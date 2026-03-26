package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when a service type is not recognized or supported by the system.
 */
public class InvalidServiceTypeException extends Exception {
    public InvalidServiceTypeException() {
        super(ExceptionMessages.INVALID_SERVICE_TYPE.getMessage());
    }
}
