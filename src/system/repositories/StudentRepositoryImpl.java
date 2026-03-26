package system.repositories;

import java.util.*;
import students.Student;
/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public class StudentRepositoryImpl implements StudentRepository {

    private final int EXPECTED_STUDENTS = 1200; //indicado no enunciado
    private final int NUM_COUNTRIES = 195;

    private final Map<String, Student> students;
    private final SortedMap<String, Student> sortedStudents;
    private final Map<String, Map<String, Student>> studentByCountry;

    public StudentRepositoryImpl() {
        students = new HashMap<>(EXPECTED_STUDENTS);
        sortedStudents = new TreeMap<>();
        studentByCountry = new HashMap<>(NUM_COUNTRIES);
    }

    @Override
    public boolean hasStudentFrom(String restriction) {
        if (restriction.equals("all"))
            return !students.isEmpty();
        Map<String, Student> map = studentByCountry.get(safeString(restriction));
        return map != null && !map.isEmpty();
    }

    @Override
    public Iterator<Student> getAllStudents() {
        return sortedStudents.values().iterator();
    }

    @Override
    public Iterator<Student> getStudentsFrom(String restriction) {
        return studentByCountry.get(safeString(restriction)).values().iterator();
    }

    @Override
    public Student getStudent(String name) {
        return students.get(safeString(name));
    }

    @Override
    public void addStudent(Student student) {
        String name = student.getName().toLowerCase();
        String country = student.getCountry().toLowerCase();
        studentByCountry.computeIfAbsent(country, k -> new LinkedHashMap<>());
        students.put(name, student);
        sortedStudents.put(name, student);
        studentByCountry.get(country).put(name, student);
    }

    /**
     * Removes student from country-based grouping.
     *
     * @param student Student to remove
     */
    private void removeStudentFromListCountry(Student student) {
        String name = student.getName().toLowerCase();
        String country = student.getCountry().toLowerCase();
        studentByCountry.get(country).remove(name);
    }

    /**
     * Converts string to lowercase for case-insensitive operations.
     *
     * @param word String to normalize
     * @return Lowercase version of input string
     */
    private String safeString(String word) {
        return word.toLowerCase();
    }

    @Override
    public void removeStudent(Student student) {
        String name = safeString(student.getName());
        students.remove(safeString(name));
        sortedStudents.remove(name);
        removeStudentFromListCountry(student);
    }
}