package org.fluentjava.volundr.io;

import java.io.IOException;

public interface VisitingInputStreamsHandler {

    VisitingInputStreamsHandler DEFAULT_HANDLER = new VisitingInputStreamsHandler() {

        @Override
        public void interruptedWhileWaitingUntilDone(InterruptedException e) {
            throw new VisitingInputStreamsHandlerException(e);
        }

        @Override
        public void closeStreamFailed(IOException e) {
            throw new VisitingInputStreamsHandlerException(e);
        }
    };

    class VisitingInputStreamsHandlerException extends RuntimeException {
        public VisitingInputStreamsHandlerException(Throwable cause) {
            super(cause);
        }
    }

    void interruptedWhileWaitingUntilDone(InterruptedException e);

    void closeStreamFailed(IOException e);

}
