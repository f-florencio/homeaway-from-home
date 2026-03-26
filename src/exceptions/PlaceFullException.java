package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when a place (restaurant, accommodation) has reached its maximum capacity.
 */
public class PlaceFullException extends Exception {
    public PlaceFullException(String type, String name) {
        super(String.format(ExceptionMessages.PLACE_FULL.getMessage(), type, name));
    }
}
