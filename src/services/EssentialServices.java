package services;

import java.util.List;

import students.Student;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public interface EssentialServices {
    /**
     * Returns list for students currently inside the service.
     *
     * @return Two-way iterator of students
     */
    List<Student> getStudentsInside();

    /**
     * Adds a student to the service.
     *
     * @param student Student to add
     */
    void addStudentToService(Student student);

    /**
     * Removes a student from a non-lodging service.
     *
     * @param student Student to remove
     */
    void removeStudentInService(Student student);

    /**
     * Removes a student from a lodging service (home).
     *
     * @param student Student to remove
     */
    void removeStudentFromHome(Student student);

    /**
     * Checks if the service is at full capacity.
     *
     * @return true if service is full
     */
    boolean isFull();
}
