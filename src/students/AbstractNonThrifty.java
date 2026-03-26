package students;


import services.EssentialServices;
import services.Leisure;
import services.Service;

import java.util.*;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public abstract class AbstractNonThrifty extends AbstractStudent implements NonThrifty {

    /**
     * since there is no expected number of locations per visit, 100 locations
     * allows a reasonable capacity for most cases, avoiding additional memory waste
     */

    private final Map<String, Service> visitedPlaces;

    /**
     * Creates a non-thrifty student with visited places tracking.
     *
     * @param type      Student type
     * @param name      Student name
     * @param country   Home country
     * @param residence Initial residence service
     */
    public AbstractNonThrifty(StudentType type, String name, String country, Service residence) {
        super(type, name, country, residence);
        visitedPlaces = new LinkedHashMap<>(100);
        initVisited(type, residence);
    }

    /**
     * Initializes visited places based on student type.
     *
     * @param type      Student type
     * @param residence Initial residence
     */
    private void initVisited(StudentType type, Service residence) {
        if (type == StudentType.OUTGOING) {
            visitedPlaces.put(residence.getName(), residence);
        }
    }

    /**
     * Adds a service to visited places if not already visited.
     *
     * @param service Service to add
     */
    @Override
    public void addToVisited(Service service) {
        visitedPlaces.putIfAbsent(service.getName(), service);
    }

    /**
     * Changes student location and tracks visited places.
     *
     * @param service New location service
     */
    @Override
    public void changeLocation(Service service) {
        if (this instanceof Bookish) {
            if (service instanceof Leisure)
                addToVisited(service);
        } else
            addToVisited(service);
        setLocation(service);
        if (service instanceof EssentialServices)
            ((EssentialServices) service).addStudentToService(this);
    }

    /**
     * Checks if student has visited any places.
     *
     * @return true if has visited places
     */
    @Override
    public boolean hasVisitedPlaces() {
        return !visitedPlaces.isEmpty();
    }

    /**
     * Returns iterator for visited places.
     *
     * @return Iterator of visited services
     */
    @Override
    public Iterator<Service> placesVisitedIterator() {
        return visitedPlaces.values().iterator();
    }
}
