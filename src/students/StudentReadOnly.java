package students;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public interface StudentReadOnly {
    /**
     * Returns student's name.
     *
     * @return Student name
     */
    String getName();

    /**
     * Returns student type.
     *
     * @return Student type
     */
    StudentType getType();

    /**
     * Returns name of student's current location.
     *
     * @return Location service name
     */
    String getLocationName();
}
