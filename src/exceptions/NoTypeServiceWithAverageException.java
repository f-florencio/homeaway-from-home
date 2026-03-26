package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when no services of a type exist that have an average rating.
 */
public class NoTypeServiceWithAverageException extends Exception {
    public NoTypeServiceWithAverageException(String type) {
        super(String.format(ExceptionMessages.NO_SERVICE_AVERAGE.getMessage(), type));
    }
}