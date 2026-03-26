package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when boundaries or limits are already defined and cannot be redefined.
 */
public class BoundsExistException extends Exception {
    public BoundsExistException() {
        super(ExceptionMessages.BOUNDS_EXISTS.getMessage());
    }
}
