package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when an order ID does not correspond to any existing order.
 */
public class OrderNotExistException extends Exception {
    public OrderNotExistException() {
        super(ExceptionMessages.ORDER_NOT_EXIST.getMessage());
    }
}
