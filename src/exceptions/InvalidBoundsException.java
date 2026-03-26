package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when provided boundaries are logically incorrect or out of acceptable range.
 */
public class InvalidBoundsException extends Exception {
    public InvalidBoundsException() {
        super(ExceptionMessages.INV_BOUNDS.getMessage());
    }
}
