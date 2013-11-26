package net.sf.völundr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import net.sf.völundr.bag.StronglyTypedSortedBag;
import net.sf.völundr.concurrent.ThreadEngineApi;

import org.junit.Before;
import org.junit.Test;

public class VölundrSmithyTest {

	private VölundrSmithy völundrSmithy;

	@Before
	public void before() {
		this.völundrSmithy = new VölundrSmithy(Charset.forName("UTF-8"));
	}

	@Test
	public void stringToBytes() {
		assertTrue(Arrays.equals(new byte[] { 118, -61, -74, 108, 117, 110,
				100, 114 }, smithy().stringToBytes().convert("völundr")));
	}

	@Test
	public void bytesToString() {
		assertEquals(
				"völundr",
				smithy().bytesToString().convert(
						new byte[] { 118, -61, -74, 108, 117, 110, 100, 114 }));
	}

	@Test
	public void asyncTreeBag() {
		final StronglyTypedSortedBag<String> synchronizedTreeBag = smithy()
				.synchronizedTreeBag();
		final ThreadEngineApi<Runnable> api = new ThreadEngineApi<Runnable>()
				.threadNamePrefix("asynctreebag");
		api.runnables(new Runnable() {

			@Override
			public void run() {
				synchronizedTreeBag.add("100");
			}
		}, new Runnable() {

			@Override
			public void run() {
				synchronizedTreeBag.add("101");
			}
		});
		api.run();
		assertFalse(synchronizedTreeBag.isEmpty());
		assertEquals(2, synchronizedTreeBag.size());
		assertEquals(1, synchronizedTreeBag.count("100"));
		assertEquals(1, synchronizedTreeBag.count("101"));
	}

	@Test
	public void treebag() {
		final StronglyTypedSortedBag<String> treebag = smithy().treeBag();
		treebag.add("100");
		treebag.add("101");
		assertEquals(2, treebag.size());
		assertEquals(1, treebag.count("100"));
		assertEquals(1, treebag.count("101"));
	}

	@Test
	public void lineReader() throws IOException {
		final StringBuilder result = new StringBuilder();
		smithy().lineReader()
				.read(VölundrSmithyTest.class
						.getResourceAsStream("/file-with-lines"),
						new LineVisitor() {

							@Override
							public void visit(final String line) {
								result.append(line).append("\n");
							}

							@Override
							public void emptyLine() {
								throw new RuntimeException(
										"No empty lines should have been there!");
							}
						});

		assertEquals("line1\n" + "line2\n" + "line3\n" + "", result.toString());
	}

	private VölundrSmithy smithy() {
		return this.völundrSmithy;
	}
}
