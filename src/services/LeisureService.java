package services;


/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public class LeisureService extends AbstractService implements Leisure {

    private final int studentDiscount;

    /**
     * Creates a leisure service with student discount.
     *
     * @param latitude        Service latitude
     * @param longitude       Service longitude
     * @param price           Base price
     * @param studentDiscount Discount percentage (0-100)
     * @param name            Service name
     * @param type            Service type
     * @param order           Creation order
     */
    public LeisureService(long latitude, long longitude, int price, int studentDiscount,
                          String name, ServiceType type, int order) {
        super(latitude, longitude, name, price, type, order);
        this.studentDiscount = studentDiscount;
    }

    /**
     * Returns the discounted price for students.
     *
     * @return Price with student discount applied
     */
    @Override
    public int getPrice() {
        return super.getPrice() * (1 - studentDiscount);
    }
}
