package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when a general type/category is invalid or unsupported.
 */
public class InvalidTypeException extends Exception {
    public InvalidTypeException(String type) {
        super(String.format(ExceptionMessages.INV_TYPE.getMessage(), type));
    }
}
