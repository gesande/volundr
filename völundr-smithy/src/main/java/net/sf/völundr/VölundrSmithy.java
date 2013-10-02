package net.sf.völundr;

import java.io.OutputStream;
import java.nio.charset.Charset;

import net.sf.völundr.bag.StronglyTypedSortedBag;
import net.sf.völundr.io.AsynchronousStreamReader;
import net.sf.völundr.io.InputStreamToString;
import net.sf.völundr.io.StreamReadFailedNotifier;
import net.sf.völundr.io.StreamReader;
import net.sf.völundr.io.VisitingInputStreams;
import net.sf.völundr.io.VisitingInputStreamsHandler;

public final class VölundrSmithy {

    private final Charset charset;

    public VölundrSmithy(final Charset charset) {
        this.charset = charset;
    }

    public LineReader lineReader() {
        return new LineReader(charset());
    }

    public StreamReader streamReader(final LineVisitor visitor) {
        return new StreamReader(visitor, charset());
    }

    public AsynchronousStreamReader asynchronousStreamReader(
            final LineVisitor visitor,
            final StreamReadFailedNotifier failNotifier) {
        return new AsynchronousStreamReader(visitor, charset(), failNotifier);
    }

    public StringToOutputStream stringToOutputStream(
            final OutputStream streamToWrite) {
        return StringToOutputStream.forCharset(streamToWrite, charset());
    }

    public InputStreamToString inputStreamToString() {
        return InputStreamToString.forCharset(charset());
    }

    public VisitingInputStreams visitinInputStreams(
            final VisitingInputStreamsHandler handler,
            final StreamReadFailedNotifier readFailedNotifier) {
        return new VisitingInputStreams(handler, charset(), readFailedNotifier);
    }

    @SuppressWarnings("static-method")
    public <TYPE> StronglyTypedSortedBag<TYPE> synchronizedTreeBag() {
        return StronglyTypedSortedBag.synchronizedTreeBag();
    }

    @SuppressWarnings("static-method")
    public <TYPE> StronglyTypedSortedBag<TYPE> treeBag() {
        return StronglyTypedSortedBag.treeBag();

    }

    private Charset charset() {
        return this.charset;
    }
}
