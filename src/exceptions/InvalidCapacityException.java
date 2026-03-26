package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when a capacity value is zero, negative, or exceeds system limits.
 */
public class InvalidCapacityException extends Exception {
    public InvalidCapacityException() {
        super(ExceptionMessages.INV_CAPACITY.getMessage());
    }
}
