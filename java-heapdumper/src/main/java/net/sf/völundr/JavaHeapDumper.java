package net.sf.völundr;

import java.lang.management.ManagementFactory;

import com.sun.management.HotSpotDiagnosticMXBean;

/**
 * Heap dump
 * <p>
 * Original idea got from https://blogs.oracle.com/sundararajan/entry/
 * programmatically_dumping_heap_from_java
 * <p>
 */
public final class JavaHeapDumper {
	private final static String HOTSPOT_DIAGNOSTIC_BEAN_NAME = "com.sun.management:type=HotSpotDiagnostic";
	private static volatile HotSpotDiagnosticMXBean HOTSPOT_MBEAN;

	/*
	 * Call this method from your application whenever you want to dump the heap
	 * snapshot into a file.
	 * 
	 * @param fileName name of the heap dump file
	 * 
	 * @param live flag that tells whether to dump only the live objects
	 */
	public static void dumpHeap(final String fileName, final boolean live) {
		initHotspotMBean();
		try {
			HOTSPOT_MBEAN.dumpHeap(fileName, live);
		} catch (final Exception exp) {
			throw new RuntimeException(exp);
		}
	}

	private static void initHotspotMBean() {
		if (HOTSPOT_MBEAN == null) {
			synchronized (JavaHeapDumper.class) {
				if (HOTSPOT_MBEAN == null) {
					HOTSPOT_MBEAN = getHotspotMBean();
				}
			}
		}
	}

	private static HotSpotDiagnosticMXBean getHotspotMBean() {
		try {
			return ManagementFactory
					.newPlatformMXBeanProxy(
							ManagementFactory.getPlatformMBeanServer(),
							HOTSPOT_DIAGNOSTIC_BEAN_NAME,
							HotSpotDiagnosticMXBean.class);
		} catch (Exception exp) {
			throw new RuntimeException(exp);
		}
	}
}