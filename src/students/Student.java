package students;

import exceptions.StudentDistractedException;
import services.Service;

import java.io.Serializable;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public interface Student extends StudentReadOnly,  Serializable{

    /**
     * Returns student's home country.
     *
     * @return Country name
     */
    String getCountry();

    /**
     * Sets student's current location.
     *
     * @param service New location service
     */
    void setLocation(Service service);

    /**
     * Changes student's location with behavior rules.
     *
     * @param service New location service
     * @throws StudentDistractedException If student can't change location
     */
    void changeLocation(Service service) throws StudentDistractedException;

    /**
     * Changes student's permanent lodging.
     *
     * @param service New lodging service
     */
    void changeLodge(Service service);

    /**
     * Returns student's current location service.
     *
     * @return Current service location
     */
    Service getCurrentLocation();

    /**
     * Returns student's home (lodging) service.
     *
     * @return Home service
     */
    Service getHome();

}
