package org.jboss.pressgang.ccms.model.exceptions;

public class CustomConstraintViolationException extends RuntimeException {
    private static final long serialVersionUID = -6156576595811734635L;

    public CustomConstraintViolationException(final String message) {
        super(message);
    }
}
