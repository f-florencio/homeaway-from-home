package services;


import java.util.*;
import students.Student;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public abstract class AbstractEssentialServices extends AbstractService implements EssentialServices {
    private final Map<String, Student> studentsInside;
    private final int capacity;
    private int numStudentsInside;

    /**
     * Creates an essential service.
     *
     * @param latitude Service latitude
     * @param longitude Service longitude
     * @param name Service name
     * @param price Service price
     * @param capacity Maximum student capacity
     * @param type Service type
     * @param order Display order
     */
    AbstractEssentialServices(long latitude, long longitude, String name, int price,
                              int capacity, ServiceType type, int order) {
        super(latitude, longitude, name, price, type, order);
        this.capacity = capacity;
        this.numStudentsInside = 0;
        this.studentsInside = new LinkedHashMap<>(capacity);
    }

    /**
     * Returns iterator for students inside.
     */
    @Override
    public List<Student> getStudentsInside() {
        Iterator<Student> it = studentsInside.values().iterator();
        List<Student> list = new LinkedList<>();
        while (it.hasNext()) {
            Student s = it.next();
            list.addLast(s);
        }
        return list;
    }

    /**
     * Adds student to service.
     *
     * @param student Student to add
     */
    @Override
    public void addStudentToService(Student student) {
        String name = student.getName().toLowerCase();
        studentsInside.put(name, student);
        numStudentsInside++;
    }

    /**
     * Removes student from service.
     *
     * @param student Student to remove
     */
    private void studentRemoval(Student student) {
        String name = student.getName().toLowerCase();
        studentsInside.remove(name);
        numStudentsInside--;
    }

    /**
     * Removes student from non-lodging service.
     *
     * @param student Student to remove
     */
    @Override
    public void removeStudentInService(Student student) {
        if (super.getType() != ServiceType.LODGING)
            studentRemoval(student);
    }

    /**
     * Removes student from home (lodging).
     *
     * @param student Student to remove
     */
    @Override
    public void removeStudentFromHome(Student student) {
        studentRemoval(student);
    }

    /**
     * Checks if service is full.
     *
     * @return true if at capacity
     */
    @Override
    public boolean isFull() {
        return numStudentsInside == capacity;
    }
}
