package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when an entity is already in the expected state or location.
 */
public class AlreadyThereException extends Exception {
    public AlreadyThereException() {
        super(ExceptionMessages.ALR_THERE.getMessage());
    }
}
