package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when a user attempts to set their home location to where they already reside.
 */
public class SameHomeException extends Exception {
    public SameHomeException(String name) {
        super(String.format(ExceptionMessages.SAME_HOME.getMessage(), name));
    }
}