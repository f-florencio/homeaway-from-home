package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when attempting to create an entity that already exists in the system.
 */
public class AlreadyExistsException extends Exception {
    public AlreadyExistsException(String name) {
        super(String.format(ExceptionMessages.ALR_EXISTS.getMessage(), name));
    }
}