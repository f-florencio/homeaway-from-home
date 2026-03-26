package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when a student cannot afford a product or service due to budget constraints.
 */
public class ThriftyStudentException extends Exception {
    public ThriftyStudentException(String name) {
        super(String.format(ExceptionMessages.THRIFTY_STUDENT.getMessage(), name));
    }
}