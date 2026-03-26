package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when a star rating is outside the allowed range (not 1-5).
 */
public class InvalidStarsException extends Exception {
    public InvalidStarsException() {
        super(ExceptionMessages.INV_STARS.getMessage());
    }
}
