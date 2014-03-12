package org.jboss.pressgang.ccms.model;

import java.io.Serializable;

public enum ProcessStatus implements Serializable {
    /**
     * The job is currently in the submission queue (on the client side).
     */
    PENDING,
    /**
     * The job is currently in the submission queue (on the server side).
     */
    QUEUED,
    /**
     * The job is being executed.
     */
    EXECUTING,
    /**
     * The job execution is complete.
     */
    COMPLETED,
    /**
     * The job execution has failed.
     */
    FAILED,
    /**
     * The job was cancelled.
     */
    CANCELLED
}
