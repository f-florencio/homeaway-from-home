package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when a service is invalid for a given location or context.
 */
public class NotValidServiceException extends Exception {
    public NotValidServiceException(String location) {
        super(String.format(ExceptionMessages.NOT_VALID_SERVICE.getMessage(), location));
    }
}
