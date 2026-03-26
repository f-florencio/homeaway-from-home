package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when a location is not recognized or not part of the system's known locations.
 */
public class UnknownLocationException extends Exception {
    public UnknownLocationException(String location) {
        super(String.format(ExceptionMessages.UNKNOWN_LOC.getMessage(), location));
    }
}
