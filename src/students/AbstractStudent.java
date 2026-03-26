package students;

import services.Service;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public abstract class AbstractStudent implements Student {

    private final String name;
    private final String country;
    private final StudentType type;
    private Service currentLocation;
    private Service home;

    /**
     * Creates a new student with initial residence.
     *
     * @param type      Student type
     * @param name      Student name
     * @param country   Home country
     * @param residence Initial residence service
     */
    AbstractStudent(StudentType type, String name, String country, Service residence) {
        this.type = type;
        this.name = name;
        this.country = country;
        this.currentLocation = residence;
        this.home = residence;
    }

    /**
     * Changes student's lodging (home) location.
     *
     * @param service New lodging service
     */
    @Override
    public void changeLodge(Service service) {
        this.currentLocation = service;
        this.home = service;
    }

    /**
     * Sets student's current location.
     *
     * @param service New location service
     */
    @Override
    public void setLocation(Service service) {
        this.currentLocation = service;
    }

    /**
     * Returns student name.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns student's home country.
     */
    @Override
    public String getCountry() {
        return country;
    }

    /**
     * Returns student's current location service.
     */
    @Override
    public Service getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Returns student's home (lodging) service.
     */
    @Override
    public Service getHome() {
        return home;
    }

    /**
     * Returns name of current location.
     */
    @Override
    public String getLocationName() {
        return currentLocation.getName();
    }

    /**
     * Returns student type.
     */
    @Override
    public StudentType getType() {
        return type;
    }
}
