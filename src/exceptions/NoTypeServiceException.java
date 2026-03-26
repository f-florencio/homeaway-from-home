package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when no services of a specified type exist in the system.
 */
public class NoTypeServiceException extends Exception {
    public NoTypeServiceException(String type) {
        super(String.format(ExceptionMessages.NO_TYPE_SERVICES.getMessage(), type));
    }
}