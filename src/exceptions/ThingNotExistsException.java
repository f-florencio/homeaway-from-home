package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when a specified object or item does not exist in the system.
 */
public class ThingNotExistsException extends Exception {
    public ThingNotExistsException(String name) {
        super(String.format(ExceptionMessages.THING_NOT_EXISTS.getMessage(), name));
    }
}
