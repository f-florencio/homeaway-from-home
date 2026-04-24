package system.application;

import java.util.Iterator;
import exceptions.*;
import services.Service;
import students.Student;
import messages.PairOutput;

import java.io.Serializable;
/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public interface SystemInterface extends Serializable {
    /**
     * Adds a new student to the system.
     *
     * @param type    Student type
     * @param name    Student name
     * @param country Student home country
     * @param lodge   Initial lodging service name
     * @throws InvalidTypeException    If student type is invalid
     * @throws PlaceNotExistsException If lodging doesn't exist
     * @throws PlaceFullException      If lodging is at capacity
     * @throws AlreadyExistsException  If student already exists
     */
    void addStudent(String type, String name, String country, String lodge)
            throws InvalidTypeException, PlaceNotExistsException, PlaceFullException, AlreadyExistsException;

    /**
     * Removes a student from the system.
     *
     * @param name Student name to remove
     * @return Student's home country
     * @throws ThingNotExistsException If student doesn't exist
     */
    String removeStudent(String name) throws ThingNotExistsException;

    /**
     * Adds a new service place to the system.
     *
     * @param latitude  Service latitude coordinate
     * @param longitude Service longitude coordinate
     * @param price     Service price
     * @param value     Additional value (discount/capacity)
     * @param name      Service name
     * @param type      Service type
     * @throws InvalidTypeException           If service type is invalid
     * @throws InvalidLocationException       If location is invalid
     * @throws InvalidPriceException          If price is invalid
     * @throws InvalidDiscountPriceException  If discount price is invalid
     * @throws InvalidCapacityException       If capacity is invalid
     * @throws AlreadyExistsException         If service already exists
     */
    void addPlace(long latitude, long longitude, int price, int value, String name, String type)
            throws InvalidTypeException, InvalidLocationException, InvalidPriceException,
            InvalidDiscountPriceException, InvalidCapacityException, AlreadyExistsException;

    /**
     * Changes a student's current location.
     *
     * @param name     Student name
     * @param location New location service name
     * @return Pair of previous and new location names
     * @throws UnknownLocationException    If location doesn't exist
     * @throws ThingNotExistsException     If student doesn't exist
     * @throws NotValidServiceException    If service type is invalid for movement
     * @throws AlreadyThereException       If student is already at location
     * @throws PlaceFullException          If location is at capacity
     * @throws StudentDistractedException  If student behavior prevents move
     */
    PairOutput changeStudentLocation(String name, String location)
            throws UnknownLocationException, ThingNotExistsException, NotValidServiceException,
            AlreadyThereException, PlaceFullException, StudentDistractedException;

    /**
     * Changes a student's permanent lodging.
     *
     * @param name     Student name
     * @param location New lodging service name
     * @return Pair of previous and new lodging names
     * @throws PlaceNotExistsException  If lodging doesn't exist
     * @throws ThingNotExistsException  If student doesn't exist
     * @throws SameHomeException        If student already lives there
     * @throws PlaceFullException       If lodging is at capacity
     * @throws NotAcceptedMoveException If move is not allowed
     */
    PairOutput changeStudentLodge(String name, String location)
            throws PlaceNotExistsException, ThingNotExistsException, SameHomeException,
            PlaceFullException, NotAcceptedMoveException;

    /**
     * Rates and reviews a service.
     *
     * @param star        Star rating
     * @param name        Service name
     * @param description Review description/tags
     * @throws InvalidEvaluationException If rating is invalid
     * @throws ThingNotExistsException    If service doesn't exist
     */
    void rateService(int star, String name, String description)
            throws InvalidEvaluationException, ThingNotExistsException;

    /**
     * Finds suitable service for a student based on type.
     *
     * @param name Student name
     * @param type Service type to find
     * @return Recommended service name
     * @throws InvalidServiceTypeException If service type is invalid
     * @throws ThingNotExistsException     If student doesn't exist
     * @throws NoTypeServiceException     If no services of requested type exist
     */
    String findServiceForStudent(String name, String type)
            throws InvalidServiceTypeException, ThingNotExistsException, NoTypeServiceException;

    /**
     * Gets a student's current location.
     *
     * @param name Student name
     * @return Pair of student name and current location
     * @throws ThingNotExistsException If student doesn't exist
     */
    PairOutput getStudentLocation(String name) throws ThingNotExistsException;

    /**
     * Gets correct service name.
     *
     * @param serviceName Input service name
     * @return Correct service name from system
     */
    String getCorrectServiceName(String serviceName);

    /**
     * Gets the city name.
     *
     * @return City name
     */
    String getCityName();

    /**
     * Gets services visited by a student.
     *
     * @param name Student name
     * @return Iterator of visited services
     * @throws ThingNotExistsException   If student doesn't exist
     * @throws ThriftyStudentException   If student is thrifty (doesn't track visits)
     * @throws NotVisitedPlacesException If student hasn't visited any places
     */
    Iterator<Service> getServicesVisitedByStudent(String name)
            throws ThingNotExistsException, ThriftyStudentException, NotVisitedPlacesException;

    /**
     * Gets all services in the system.
     *
     * @return Iterator of all services
     */
    Iterator<Service> getAllServices();

    /**
     * Gets services in creation order.
     *
     * @return Iterator of ordered services
     */
    Iterator<Service> getServicesOrdered();

    /**
     * Gets services containing a specific tag.
     *
     * @param tag Tag to search for
     * @return Iterator of tagged services
     */
    Iterator<Service> getServicesTaggedWith(String tag);

    /**
     * Gets services filtered by type and minimum star rating.
     *
     * @param type Service type filter
     * @param star Minimum star rating
     * @param name Student name for preference
     * @return Iterator of filtered services
     * @throws InvalidStarsException               If star rating is invalid
     * @throws ThingNotExistsException            If student doesn't exist
     * @throws InvalidTypeException               If service type is invalid
     * @throws NoTypeServiceException            If no services of requested type exist
     * @throws NoTypeServiceWithAverageException If no services meet star requirement
     */
    Iterator<Service> getServicesByTypeAndStar(String type, int star, String name)
            throws InvalidStarsException, ThingNotExistsException, InvalidTypeException,
            NoTypeServiceException, NoTypeServiceWithAverageException;

    /**
     * Gets students at a specific location.
     *
     * @param order       Sort order
     * @param serviceName Service location name
     * @return Two-way iterator of students
     * @throws OrderNotExistException      If sort order is invalid
     * @throws ThingNotExistsException    If service doesn't exist
     * @throws NotControlledEntryException If service doesn't track students
     */
    Iterator<Student> getStudentsAtLocation(String order, String serviceName)
            throws OrderNotExistException, ThingNotExistsException, NotControlledEntryException;

    /**
     * Gets students with optional restriction.
     *
     * @param restriction Filter restriction
     * @return Iterator of filtered students
     */
    Iterator<Student> getStudents(String restriction);

    /**
     * Checks if any student is from a specific country/type.
     *
     * @param restriction Country name or student type
     * @return true if matching student exists
     */
    boolean hasStudentFrom(String restriction);

    /**
     * Checks if system has any services.
     *
     * @return true if services exist
     */
    boolean hasServices();
}
