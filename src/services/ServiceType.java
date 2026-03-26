package services;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

import java.io.Serializable;

public enum ServiceType implements Serializable {
    LEISURE("leisure", 0, "ticket"),
    LODGING("lodging", 1, "room"),
    EATING("eating", 2, "menu");

    final String name;
    final int index;
    final String product;

    /**
     * Creates a ServiceType enum value.
     *
     * @param name    Display name of service type
     * @param index   Sorting/display index
     * @param product Product name associated with type
     */
    ServiceType(String name, int index, String product) {
        this.name = name;
        this.index = index;
        this.product = product;
    }

    /**
     * Safely converts string to ServiceType.
     *
     * @param name Name string to match
     * @return ServiceType or null
     */
    public static ServiceType safeValueOf(String name) {
        for (ServiceType type : ServiceType.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        return null;
    }

    /**
     * Returns display name.
     *
     * @return Name string
     */
    public String getName() {
        return name;
    }

    /**
     * Returns sorting index.
     *
     * @return Index integer
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns product name.
     *
     * @return Product string
     */
    public String getProduct() {
        return product;
    }

}
