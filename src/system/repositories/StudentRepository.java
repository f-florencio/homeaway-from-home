package system.repositories;

import java.util.*;
import students.Student;

import java.io.Serializable;
/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public interface StudentRepository extends Serializable {

    /**
     * Checks if any student matches the restriction (country or type).
     *
     * @param restriction Country name or student type
     * @return true if matching student exists
     */
    boolean hasStudentFrom(String restriction);

    /**
     * Returns iterator for all students in the repository.
     *
     * @return Iterator of all students
     */
    Iterator<Student> getAllStudents();

    /**
     * Returns iterator for students filtered by restriction.
     *
     * @param restriction Country name or student type to filter
     * @return Iterator of filtered students
     */
    Iterator<Student> getStudentsFrom(String restriction);

    /**
     * Gets student by name.
     *
     * @param name Student name to find
     * @return Student object or null if not found
     */
    Student getStudent(String name);

    /**
     * Adds new student to repository.
     *
     * @param student Student to add
     */
    void addStudent(Student student);

    /**
     * Removes student from repository.
     *
     * @param student Student to remove
     */
    void removeStudent(Student student);
}
