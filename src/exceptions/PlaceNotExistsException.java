package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when a specified place does not exist in the system for a given type.
 */
public class PlaceNotExistsException extends Exception {
    public PlaceNotExistsException(String type, String name) {
        super(String.format(ExceptionMessages.PLACE_NOT_EXISTS.getMessage(), type, name));
    }
}
