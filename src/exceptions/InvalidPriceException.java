package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when a price is negative or invalid for a specific product or service.
 */
public class InvalidPriceException extends Exception {
    public InvalidPriceException(String product) {
        super(String.format(ExceptionMessages.INV_PRICE.getMessage(), product));
    }
}
