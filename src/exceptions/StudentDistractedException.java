package exceptions;

import messages.ExceptionMessages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Thrown when a student forgets something due to distraction.
 */
public class StudentDistractedException extends Exception {
    public StudentDistractedException(String name, String restaurant) {
        super(String.format(ExceptionMessages.DISTRACTED_STUDENT.getMessage(), name, restaurant, name));
    }
}
