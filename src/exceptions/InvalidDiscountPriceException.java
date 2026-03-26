package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when a discount price is negative, exceeds original price, or is invalid.
 */
public class InvalidDiscountPriceException extends Exception {
    public InvalidDiscountPriceException() {
        super(ExceptionMessages.INV_DISCOUNT.getMessage());
    }
}
