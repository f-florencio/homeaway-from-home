package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when a location is malformed, out of bounds, or unrecognized.
 */
public class InvalidLocationException extends Exception {
    public InvalidLocationException() {
        super(ExceptionMessages.INV_LOCATION.getMessage());
    }
}
