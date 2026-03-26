package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when an evaluation value is outside the allowed range (e.g., ratings).
 */
public class InvalidEvaluationException extends Exception {
    public InvalidEvaluationException() {
        super(ExceptionMessages.INV_EVALUATION.getMessage());
    }
}
