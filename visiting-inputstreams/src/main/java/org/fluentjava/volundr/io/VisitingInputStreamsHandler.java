package org.fluentjava.volundr.io;

import java.io.IOException;

public interface VisitingInputStreamsHandler {

    VisitingInputStreamsHandler DEFAULT_HANDLER = new VisitingInputStreamsHandler() {

        @Override
        public void interruptedWhileWaitingUntilDone(InterruptedException e) {
            throw new RuntimeException(e);
        }

        @Override
        public void closeStreamFailed(IOException e) {
            throw new RuntimeException(e);
        }
    };

    void interruptedWhileWaitingUntilDone(InterruptedException e);

    void closeStreamFailed(IOException e);

}
