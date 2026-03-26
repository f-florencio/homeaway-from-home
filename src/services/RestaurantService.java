package services;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public class RestaurantService extends AbstractEssentialServices implements Restaurant {
    public RestaurantService(long latitude, long longitude, int price, int capacity, String name, ServiceType type, int order) {
        super(latitude, longitude, name, price, capacity, type, order);
    }
}
