package students;

import java.util.Iterator;
import services.Service;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public interface NonThrifty {

    /**
     * Adds a service to the student's visited places.
     *
     * @param service Service to add as visited
     */
    void addToVisited(Service service);

    /**
     * Checks if student has visited any places.
     *
     * @return true if has visited at least one place
     */
    boolean hasVisitedPlaces();

    /**
     * Returns iterator for services visited by student.
     *
     * @return Iterator of visited services
     */
    Iterator<Service> placesVisitedIterator();
}
