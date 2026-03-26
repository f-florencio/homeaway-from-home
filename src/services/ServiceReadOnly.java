package services;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public interface ServiceReadOnly {

    /**
     * Returns service latitude coordinate.
     *
     * @return Latitude value
     */
    long getLatitude();

    /**
     * Returns service longitude coordinate.
     *
     * @return Longitude value
     */
    long getLongitude();

    /**
     * Returns service name.
     *
     * @return Name string
     */
    String getName();

    /**
     * Returns service type.
     *
     * @return ServiceType enum
     */
    ServiceType getType();

    /**
     * Returns rounded average rating.
     *
     * @return Rating rounded to nearest integer
     */
    int getEvaluationAverageRounded();

}
