package system.application;

import city.City;
import exceptions.*;
import java.util.*;
import messages.PairOutput;
import services.*;
import services.reviews.Review;
import students.*;
import system.repositories.*;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public class SystemClass implements SystemInterface{

    private final String ALL = "all";
    private final String ASCENDING = ">";
    private final String DESCENDING = "<";
    private final String SERVICE = "service";
    private final String STUDENT = "student";


    private int counterServices;
    private int counterChangesStar;

    private final StudentRepository students;
    private final ServiceRepository services;
    private final City city;


    /**
     * Creates system with city boundaries and initializes repositories.
     *
     * @param leftLatitude Top-left latitude coordinate
     * @param leftLongitude Top-left longitude coordinate
     * @param rightLatitude Bottom-right latitude coordinate
     * @param rightLongitude Bottom-right longitude coordinate
     * @param location City name
     */
    public SystemClass(long leftLatitude, long leftLongitude, long rightLatitude,
                       long rightLongitude, String location) {
        city = new City(leftLatitude, leftLongitude, rightLatitude, rightLongitude, location);
        students = new StudentRepositoryImpl();
        services = new ServiceRepositoryImpl();
        counterServices = 1;
        counterChangesStar = 1;
    }

    /**
     * Creates student based on type and adds to residence.
     *
     * @param type Student type string
     * @param name Student name
     * @param country Student country
     * @param residence Initial residence service
     */
    private void createStudent(String type, String name, String country, Service residence) {
        Student student = null;
        StudentType studentType = StudentType.valueOf(type.toUpperCase());
        switch (studentType) {
            case BOOKISH -> student = new BookishStudent(studentType, name, country, residence);
            case OUTGOING -> student = new OutgoingStudent(studentType, name, country, residence);
            case THRIFTY -> student = new ThriftyStudent(studentType, name, country, residence);
        }
        students.addStudent(student);
        ((EssentialServices) residence).addStudentToService(student);
    }

    /**
     * Checks if essential service is at full capacity.
     *
     * @param service Service to check
     * @return true if service is full
     */
    private boolean placeIsFull(Service service) {
        return ((EssentialServices) service).isFull();
    }

    /**
     * Validates student creation conditions.
     *
     * @param name Student name
     * @param residence Residence service
     * @throws PlaceFullException If residence is full
     * @throws AlreadyExistsException If student already exists
     */
    private void checkIfCanBeStudent(String name, Service residence)
            throws PlaceFullException, AlreadyExistsException {
        String serviceType = residence.getType().getName();
        String serviceName = residence.getName();
        if (placeIsFull(residence))
            throw new PlaceFullException(serviceType, serviceName);
        Student s = students.getStudent(name);
        if (s != null)
            throw new AlreadyExistsException(s.getName());
    }

    /**
     * Adds student to system.
     *
     * @param type Student type
     * @param name Student name
     * @param country Student country
     * @param lodge Residence service name
     * @throws InvalidTypeException If student type invalid
     * @throws PlaceNotExistsException If lodge doesn't exist
     * @throws PlaceFullException If lodge is full
     * @throws AlreadyExistsException If student already exists
     */
    @Override
    public void addStudent(String type, String name, String country, String lodge)
            throws InvalidTypeException, PlaceNotExistsException,
            PlaceFullException, AlreadyExistsException {
        StudentType studentType = StudentType.safeValueOf(type);
        Service residence = services.getService(lodge);
        if (studentType == null)
            throw new InvalidTypeException(STUDENT);
        if (!(residence instanceof Lodge)) {
            throw new PlaceNotExistsException(ServiceType.LODGING.getName(), lodge);
        }
        checkIfCanBeStudent(name, residence);
        createStudent(type, name, country, residence);
    }

    /**
     * Removes student from all tracking systems.
     *
     * @param student Student to remove
     */
    private void deleteStudentExistence(Student student) {
        Service current = student.getCurrentLocation();
        Service home = student.getHome();
        if (current instanceof EssentialServices)
            ((EssentialServices) current).removeStudentInService(student);
        ((EssentialServices) home).removeStudentFromHome(student);
        students.removeStudent(student);
    }

    /**
     * Removes student from system.
     *
     * @param name Student name to remove
     * @return Student's name
     * @throws ThingNotExistsException If student doesn't exist
     */
    @Override
    public String removeStudent(String name) throws ThingNotExistsException {
        Student student = students.getStudent(name);
        if (student == null) {
            throw new ThingNotExistsException(name);
        }
        deleteStudentExistence(student);
        return student.getName();
    }

    /**
     * Validates service creation parameters.
     *
     * @param latitude Service latitude
     * @param longitude Service longitude
     * @param price Service price
     * @param value Capacity or discount value
     * @param name Service name
     * @param type Service type
     * @throws InvalidTypeException If service type invalid
     * @throws InvalidLocationException If location outside city
     * @throws InvalidPriceException If price invalid
     * @throws InvalidDiscountPriceException If discount invalid for leisure
     * @throws InvalidCapacityException If capacity invalid
     * @throws AlreadyExistsException If service already exists
     */
    private void checkIfCanBeService(long latitude, long longitude, int price,
                                     int value, String name, String type)
            throws InvalidTypeException, InvalidLocationException,
            InvalidPriceException, InvalidDiscountPriceException,
            InvalidCapacityException, AlreadyExistsException {
        ServiceType serviceType = ServiceType.safeValueOf(type);
        if (serviceType == null)
            throw new InvalidTypeException(SERVICE);
        if (!city.isInBounds(latitude, longitude))
            throw new InvalidLocationException();
        if (price <= 0)
            throw new InvalidPriceException(serviceType.getProduct());
        if (serviceType == ServiceType.LEISURE) {
            if (value < 0 || value > 100)
                throw new InvalidDiscountPriceException();
        } else {
            if (value <= 0)
                throw new InvalidCapacityException();
        }
        Service s = services.getService(name);
        if (s != null)
            throw new AlreadyExistsException(s.getName());
    }

    /**
     * Creates service based on type.
     *
     * @param latitude Service latitude
     * @param longitude Service longitude
     * @param price Service price
     * @param value Capacity or discount
     * @param name Service name
     * @param type Service type
     */
    private void createService(long latitude, long longitude, int price,
                               int value, String name, String type) {
        Service service = null;
        ServiceType serviceType = ServiceType.valueOf(type.toUpperCase());
        switch (serviceType) {
            case EATING -> service = new RestaurantService(latitude, longitude, price,
                    value, name, serviceType, counterServices);
            case LODGING -> service = new LodgeService(latitude, longitude, price,
                    value, name, serviceType, counterServices);
            case LEISURE -> service = new LeisureService(latitude, longitude, price,
                    value, name, serviceType, counterServices);
        }
        services.addService(service);
        counterServices++;
    }

    /**
     * Adds new service place to system.
     *
     * @param latitude Service latitude
     * @param longitude Service longitude
     * @param price Service price
     * @param value Capacity or discount
     * @param name Service name
     * @param type Service type
     * @throws InvalidTypeException If service type invalid
     * @throws InvalidLocationException If location outside city
     * @throws InvalidPriceException If price invalid
     * @throws InvalidDiscountPriceException If discount invalid
     * @throws InvalidCapacityException If capacity invalid
     * @throws AlreadyExistsException If service already exists
     */
    @Override
    public void addPlace(long latitude, long longitude, int price, int value,
                         String name, String type)
            throws InvalidTypeException, InvalidLocationException,
            InvalidPriceException, InvalidDiscountPriceException,
            InvalidCapacityException, AlreadyExistsException {
        checkIfCanBeService(latitude, longitude, price, value, name, type);
        createService(latitude, longitude, price, value, name, type);
    }

    /**
     * Validates location change conditions.
     *
     * @param name Student name
     * @param location New location name
     * @param service Service object
     * @param student Student object
     * @throws UnknownLocationException If service doesn't exist
     * @throws ThingNotExistsException If student doesn't exist
     * @throws NotValidServiceException If service is lodging
     * @throws AlreadyThereException If student already at location
     * @throws PlaceFullException If restaurant is full
     */
    private void checkForErrorsInLocationChange(String name, String location,
                                                Service service, Student student)
            throws UnknownLocationException, ThingNotExistsException,
            NotValidServiceException, AlreadyThereException, PlaceFullException {
        if (service == null)
            throw new UnknownLocationException(location);
        if (student == null)
            throw new ThingNotExistsException(name);
        if (service instanceof Lodge)
            throw new NotValidServiceException(location);
        if (student.getCurrentLocation() == service)
            throw new AlreadyThereException();
        if (service instanceof Restaurant && ((EssentialServices) service).isFull())
            throw new PlaceFullException(service.getType().getName(), location);
    }

    /**
     * Changes student's current location.
     *
     * @param name Student name
     * @param location New location name
     * @return Pair with student and new service
     * @throws UnknownLocationException If location doesn't exist
     * @throws ThingNotExistsException If student doesn't exist
     * @throws NotValidServiceException If service is lodging
     * @throws AlreadyThereException If student already there
     * @throws PlaceFullException If restaurant full
     * @throws StudentDistractedException If student behavior prevents move
     */
    @Override
    public PairOutput changeStudentLocation(String name, String location)
            throws UnknownLocationException, ThingNotExistsException,
            NotValidServiceException, AlreadyThereException,
            PlaceFullException, StudentDistractedException {

        Service service = services.getService(location);
        Student student = students.getStudent(name);
        checkForErrorsInLocationChange(name, location, service, student);
        Service currentLocation = student.getCurrentLocation();

        if (currentLocation instanceof EssentialServices)
            ((EssentialServices) currentLocation).removeStudentInService(student);

        student.changeLocation(service);
        return new PairOutput(student, service);
    }

    /**
     * Validates lodging change conditions.
     *
     * @param service New lodging service
     * @param student Student object
     * @param location New location name
     * @param name Student name
     * @throws PlaceNotExistsException If not a lodging service
     * @throws ThingNotExistsException If student doesn't exist
     * @throws SameHomeException If same as current home
     * @throws PlaceFullException If lodging full
     * @throws NotAcceptedMoveException If thrifty student price condition
     */
    private void checkMovingLodgeErrors(Service service, Student student,
                                        String location, String name)
            throws PlaceNotExistsException, ThingNotExistsException,
            SameHomeException, PlaceFullException, NotAcceptedMoveException {
        if (!(service instanceof Lodge))
            throw new PlaceNotExistsException(ServiceType.LODGING.getName(), location);
        if (student == null)
            throw new ThingNotExistsException(name);
        if (service == student.getHome())
            throw new SameHomeException(student.getName());
        if (((EssentialServices) service).isFull())
            throw new PlaceFullException(ServiceType.LODGING.getName(), location);
        if (student instanceof Thrifty && service.getPrice() >= student.getHome().getPrice())
            throw new NotAcceptedMoveException(name);
    }

    /**
     * Moves student to new lodging.
     *
     * @param service New lodging service
     * @param student Student object
     */
    private void moveStudent(Service service, Student student) {
        if (student instanceof Outgoing)
            ((AbstractNonThrifty) student).addToVisited(service);
        ((EssentialServices) student.getHome()).removeStudentFromHome(student);
        ((EssentialServices) service).addStudentToService(student);
        student.changeLodge(service);
    }

    /**
     * Changes student's permanent lodging.
     *
     * @param name Student name
     * @param location New lodging name
     * @return Pair with student and new service
     * @throws PlaceNotExistsException If not lodging
     * @throws ThingNotExistsException If student doesn't exist
     * @throws SameHomeException If same home
     * @throws PlaceFullException If lodging full
     * @throws NotAcceptedMoveException If thrifty price condition
     */
    @Override
    public PairOutput changeStudentLodge(String name, String location)
            throws PlaceNotExistsException, ThingNotExistsException,
            SameHomeException, PlaceFullException, NotAcceptedMoveException {
        Service service = services.getService(location);
        Student student = students.getStudent(name);
        checkMovingLodgeErrors(service, student, location, name);
        moveStudent(service, student);
        return new PairOutput(student, service);
    }

    /**
     * Validates service rating parameters.
     *
     * @param star Star rating
     * @param service Service object
     * @param name Service name
     * @throws InvalidEvaluationException If invalid star rating
     * @throws ThingNotExistsException If service doesn't exist
     */
    private void checkForErrorsRateService(int star, Service service, String name)
            throws InvalidEvaluationException, ThingNotExistsException {
        if (star > 5 || star < 1)
            throw new InvalidEvaluationException();
        if (service == null)
            throw new ThingNotExistsException(name);
    }

    /**
     * Converts description to lowercase character array.
     *
     * @param description Review description
     * @return Character array of lowercase description
     */
    private static Character[] getCharacterString(String description) {
        Character[] chars = new Character[description.length()];
        for (int i = 0; i < description.length(); i++) {
            char c = description.charAt(i);
            if (c >= 'A' && c <= 'Z') c += 32;
            chars[i] = c;
        }
        return chars;
    }

    /**
     * Rates and reviews a service.
     *
     * @param star Star rating
     * @param name Service name
     * @param description Review description
     * @throws InvalidEvaluationException If invalid star rating
     * @throws ThingNotExistsException If service doesn't exist
     */
    @Override
    public void rateService(int star, String name, String description) throws InvalidEvaluationException, ThingNotExistsException {
        Service service = services.getService(name);
        checkForErrorsRateService(star, service, name);
        Review review = new Review(star, description);
        int oldRounded = service.getEvaluationAverageRounded();
        float oldFloat = service.getAverageRating();
        service.evaluate(review, star);
        int newRounded = service.getEvaluationAverageRounded();
        services.rateService(service, oldRounded, newRounded, oldFloat, review);
        if(service.didStarChanged(counterChangesStar))
            counterChangesStar++;
    }

    /**
     * Validates service search conditions.
     *
     * @param student Student object
     * @param name Student name
     * @param type Service type
     * @throws ThingNotExistsException If student doesn't exist
     * @throws InvalidServiceTypeException If service type invalid
     * @throws NoTypeServiceException If no services of type
     */
    private void checkForErrorsCloserService(Student student, String name, String type)
            throws ThingNotExistsException, InvalidServiceTypeException,
            NoTypeServiceException {
        if(ServiceType.safeValueOf(type) == null)
            throw new InvalidServiceTypeException();
        if (student == null)
            throw new ThingNotExistsException(name);
        if(services.isServiceTypeEmpty(type))
            throw new NoTypeServiceException(type);
    }

    /**
     * Gets service based on student type preference.
     *
     * @param student Student object
     * @param type Service type
     * @return Recommended service name
     */
    private String serviceGetterByType(Student student, String type) {
        if(student.getType() == StudentType.THRIFTY)
            return services.getCheapestService(type).getName();
        else
            return services.getBestRatedService(type).getName();
    }

    /**
     * Finds recommended service for student.
     *
     * @param name Student name
     * @param type Service type
     * @return Recommended service name
     * @throws InvalidServiceTypeException If service type invalid
     * @throws ThingNotExistsException If student doesn't exist
     * @throws NoTypeServiceException If no services of type
     */
    @Override
    public String findServiceForStudent(String name, String type)
            throws InvalidServiceTypeException, ThingNotExistsException,
            NoTypeServiceException {
        Student student = students.getStudent(name);
        checkForErrorsCloserService(student, name, type);
        return serviceGetterByType(student, type);
    }

    /**
     * Gets student's current location.
     *
     * @param name Student name
     * @return Pair with student and location
     * @throws ThingNotExistsException If student doesn't exist
     */
    @Override
    public PairOutput getStudentLocation(String name) throws ThingNotExistsException {
        Student student = students.getStudent(name);
        if (student == null) {
            throw new ThingNotExistsException(name);
        }
        Service location = student.getCurrentLocation();
        return new PairOutput(student, location);
    }

    /**
     * Gets correct service name.
     *
     * @param serviceName Input service name
     * @return Correct service name from system
     */
    @Override
    public String getCorrectServiceName(String serviceName) {
        return services.getService(serviceName).getName();
    }

    /**
     * Validates visited places access.
     *
     * @param student Student object
     * @param name Student name
     * @throws ThingNotExistsException If student doesn't exist
     * @throws ThriftyStudentException If student is thrifty
     * @throws NotVisitedPlacesException If no visited places
     */
    private void checkVisitedPlacesErrors(Student student, String name)
            throws ThingNotExistsException, ThriftyStudentException,
            NotVisitedPlacesException {
        if (student == null)
            throw new ThingNotExistsException(name);
        if (student instanceof Thrifty)
            throw new ThriftyStudentException(student.getName());
        if (!((NonThrifty) student).hasVisitedPlaces())
            throw new NotVisitedPlacesException(student.getName());
    }

    /**
     * Gets services visited by student.
     *
     * @param name Student name
     * @return Iterator of visited services
     * @throws ThingNotExistsException If student doesn't exist
     * @throws ThriftyStudentException If student is thrifty
     * @throws NotVisitedPlacesException If no visited places
     */
    @Override
    public Iterator<Service> getServicesVisitedByStudent(String name)
            throws ThingNotExistsException, ThriftyStudentException,
            NotVisitedPlacesException {
        Student student = students.getStudent(name);
        checkVisitedPlacesErrors(student, name);
        return ((NonThrifty) student).placesVisitedIterator();
    }

    /**
     * Gets all services in system.
     *
     * @return Iterator of all services
     */
    @Override
    public Iterator<Service> getAllServices() {
        return services.getAllServicesIterator();
    }

    /**
     * Gets services in creation order.
     *
     * @return Iterator of ordered services
     */
    @Override
    public Iterator<Service> getServicesOrdered() {
        return services.servicesOrderedByStarIterator();
    }

    /**
     * Gets services containing specific tag.
     *
     * @param tag Tag to search
     * @return Iterator of tagged services
     */
    @Override
    public Iterator<Service> getServicesTaggedWith(String tag) {
        Iterator<Service> it = services.getAllServicesIterator();
        List<Service> tagged = new LinkedList<>();
        while (it.hasNext()) {
            Service s = it.next();
            if(s.containsTag(tag))
                tagged.addLast(s);
        }
        return tagged.iterator();
    }

    /**
     * Validates type/star filtered service access.
     *
     * @param star Star rating filter
     * @param student Student object
     * @param name Student name
     * @param type Service type
     * @throws InvalidStarsException If invalid star rating
     * @throws ThingNotExistsException If student doesn't exist
     * @throws InvalidTypeException If service type invalid
     * @throws NoTypeServiceException If no services of type
     * @throws NoTypeServiceWithAverageException If no services with star rating
     */
    private void checkForErrorsInIterationByType(int star, Student student,
                                                 String name, String type)
            throws InvalidStarsException, ThingNotExistsException,
            InvalidTypeException, NoTypeServiceException,
            NoTypeServiceWithAverageException {
        if (star > 5 || star < 1)
            throw new InvalidStarsException();
        if (student == null)
            throw new ThingNotExistsException(name);
        if (ServiceType.safeValueOf(type) == null)
            throw new InvalidTypeException(SERVICE);
        if (services.isServiceTypeEmpty(type))
            throw new NoTypeServiceException(type);
        if (!services.isThereServiceWithStar(type, star))
            throw new NoTypeServiceWithAverageException(type);
    }

    /**
     * Calculates Manhattan distance between service and student.
     *
     * @param service Service location
     * @param student Student location
     * @return Distance value
     */
    private long distanceCalc(Service service, Student student) {
        Service studentLocation = student.getCurrentLocation();
        return Math.abs(service.getLatitude() - studentLocation.getLatitude()) +
                Math.abs(service.getLongitude() - studentLocation.getLongitude());
    }

    /**
     * Gets services filtered by type and minimum star rating.
     *
     * @param type Service type
     * @param star Minimum star rating
     * @param name Student name for distance calculation
     * @return Iterator of filtered services
     * @throws InvalidStarsException If invalid star rating
     * @throws ThingNotExistsException If student doesn't exist
     * @throws InvalidTypeException If service type invalid
     * @throws NoTypeServiceException If no services of type
     * @throws NoTypeServiceWithAverageException If no services with star rating
     */
    @Override
    public Iterator<Service> getServicesByTypeAndStar(String type, int star, String name)
            throws InvalidStarsException, ThingNotExistsException,
            InvalidTypeException, NoTypeServiceException,
            NoTypeServiceWithAverageException {
        Student student = students.getStudent(name);
        checkForErrorsInIterationByType(star, student, name, type);
        return getClosestServiceIterator(type, star, student);
    }

    /**
     * Sorts services by last star change time.
     *
     * @param servicesList List of services to sort
     * @return Sorted iterator
     */
    private Iterator<Service> sortByStarIterator(List<Service> servicesList) {
        Comparator<Service> comparator = new ServiceComparator();
        Iterator<Service> it = servicesList.iterator();
        SortedSet<Service> sorted = new TreeSet<>(comparator);
        while (it.hasNext()) {
            Service s = it.next();
            sorted.add(s);
        }
        return sorted.iterator();
    }

    /**
     * Gets closest services of given type and minimum rating.
     *
     * @param type Service type
     * @param star Minimum star rating
     * @param student Student for distance calculation
     * @return Iterator of closest services
     */
    private Iterator<Service> getClosestServiceIterator(String type, int star, Student student) {
        Iterator<Service> it = services.getTypeAndStarIterator(type, star);
        List<Service> closestServices = new LinkedList<>();
        long minDistance = Long.MAX_VALUE;
        while (it.hasNext()) {
            Service s = it.next();
            long distance = distanceCalc(s, student);
            if (distance < minDistance) {
                closestServices = new LinkedList<>();
                closestServices.addLast(s);
                minDistance = distance;
            } else if (distance == minDistance)
                closestServices.addLast(s);
        }
        return sortByStarIterator(closestServices);
    }

    /**
     * Validates student location listing conditions.
     *
     * @param order Sort order
     * @param service Service object
     * @param serviceName Service name
     * @throws OrderNotExistException If invalid order
     * @throws ThingNotExistsException If service doesn't exist
     * @throws NotControlledEntryException If service doesn't track students
     */
    private void checkForErrorsInIteration(String order, Service service, String serviceName)
            throws OrderNotExistException, ThingNotExistsException,
            NotControlledEntryException {
        if (!order.equals(DESCENDING) && !order.equals(ASCENDING))
            throw new OrderNotExistException();
        if (service == null)
            throw new ThingNotExistsException(serviceName);
        if (service instanceof Leisure)
            throw new NotControlledEntryException(service.getName());
    }

    /**
     * Gets students at specific location.
     *
     * @param order       Sort order
     * @param serviceName Service name
     * @return Two-way iterator of students
     * @throws OrderNotExistException      If invalid order
     * @throws ThingNotExistsException     If service doesn't exist
     * @throws NotControlledEntryException If service doesn't track students
     */
    @Override
    public Iterator<Student> getStudentsAtLocation(String order, String serviceName)
            throws OrderNotExistException, ThingNotExistsException,
            NotControlledEntryException {
        Service service = services.getService(serviceName);
        checkForErrorsInIteration(order, service, serviceName);
        List<Student> list = ((EssentialServices) service).getStudentsInside();
        if (order.equals(DESCENDING)) {
            list = list.reversed();
        }
        return list.iterator();
    }

    /**
     * Gets students with optional restriction.
     *
     * @param restriction Filter (country or type, ALL for all)
     * @return Iterator of filtered students
     */
    @Override
    public Iterator<Student> getStudents(String restriction) {
        if (restriction.equalsIgnoreCase(ALL))
            return students.getAllStudents();
        return students.getStudentsFrom(restriction);
    }

    /**
     * Checks if any student matches restriction.
     *
     * @param restriction Country or student type
     * @return true if matching student exists
     */
    @Override
    public boolean hasStudentFrom(String restriction) {
        return students.hasStudentFrom(restriction);
    }

    /**
     * Checks if system has services.
     *
     * @return true if services exist
     */
    @Override
    public boolean hasServices() {
        return services.hasServices();
    }

    /**
     * Gets city name.
     *
     * @return City name
     */
    @Override
    public String getCityName() {
        return city.name();
    }
}
