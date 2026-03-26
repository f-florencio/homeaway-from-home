package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when a user hasn't visited any places of a given type or category.
 */
public class NotVisitedPlacesException extends Exception {
    public NotVisitedPlacesException(String name) {
        super(String.format(ExceptionMessages.NOT_VISITED.getMessage(), name));
    }
}