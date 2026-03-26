package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when a move or transition is not allowed based on current state.
 */
public class NotAcceptedMoveException extends Exception {
    public NotAcceptedMoveException(String name) {
        super(String.format(ExceptionMessages.MOVE_NOT_ACCEPTED.getMessage(), name));
    }
}
