package students;

import exceptions.StudentDistractedException;
import services.EssentialServices;
import services.Restaurant;
import services.Service;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public class ThriftyStudent extends AbstractStudent implements Thrifty {

    private Service cheapestRestaurantFound;

    public ThriftyStudent(StudentType type, String name, String country, Service residence) {
        super(type, name, country, residence);
        cheapestRestaurantFound = null;
    }

    @Override
    public void changeLocation(Service service) throws StudentDistractedException {
        setLocation(service);
        if (service instanceof Restaurant) {
            ((EssentialServices) service).addStudentToService(this);
            checkRestaurantCheaper(service);
        }
    }

    /**
     * Compares restaurant prices and tracks cheapest found.
     *
     * @param service New restaurant service
     * @throws StudentDistractedException If cheaper restaurant already found
     */
    private void checkRestaurantCheaper(Service service) throws StudentDistractedException {
        if (cheapestRestaurantFound == null) {
            cheapestRestaurantFound = service;
            return;
        }
        int cheapestPrice = cheapestRestaurantFound.getPrice();
        int servicePrice = service.getPrice();
        if (cheapestPrice < servicePrice)
            throw new StudentDistractedException(super.getName(), service.getName());
        if (cheapestPrice > servicePrice)
            cheapestRestaurantFound = service;
    }
}
