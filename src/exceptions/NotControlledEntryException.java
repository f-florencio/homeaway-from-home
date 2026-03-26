package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when an operation is attempted on an entry not under user control.
 */
public class NotControlledEntryException extends Exception {
    public NotControlledEntryException(String serviceName) {
        super(String.format(ExceptionMessages.NOT_CONTROLLED.getMessage(), serviceName));
    }
}