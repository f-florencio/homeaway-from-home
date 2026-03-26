package city;

import java.io.Serializable;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

/**
 * Defines city boundaries using rectangular coordinates.
 *
 * @param topLeftLatitude Top-left corner latitude
 * @param topLeftLongitude Top-left corner longitude
 * @param bottomRightLatitude Bottom-right corner latitude
 * @param bottomRightLongitude Bottom-right corner longitude
 * @param name City name
 */
public record City(long topLeftLatitude, long topLeftLongitude, long bottomRightLatitude,
                   long bottomRightLongitude, String name) implements Serializable {

    /**
     * Checks if a point is within city boundaries.
     *
     * @param latitude Point latitude to check
     * @param longitude Point longitude to check
     * @return true if within boundaries
     */
    public boolean isInBounds(long latitude, long longitude) {
        return bottomRightLatitude <= latitude && latitude <= topLeftLatitude &&
                topLeftLongitude <= longitude && longitude <= bottomRightLongitude;
    }

}
