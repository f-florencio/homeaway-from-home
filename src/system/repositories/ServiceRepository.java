package system.repositories;

import java.util.*;
import services.reviews.Review;
import services.Service;

import java.io.Serializable;
/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public interface ServiceRepository extends Serializable {
    /**
     * Returns iterator for all services in the repository.
     *
     * @return Iterator of all services
     */
    Iterator<Service> getAllServicesIterator();

    /**
     * Returns iterator for services sorted by star rating change time.
     *
     * @return Iterator of services ordered by last star change
     */
    Iterator<Service> servicesOrderedByStarIterator();

    /**
     * Returns iterator for services filtered by type and star rating.
     *
     * @param type Service type to filter
     * @param star star rating
     * @return Iterator of filtered services
     */
    Iterator<Service> getTypeAndStarIterator(String type, int star);

    /**
     * Gets service by name (case-insensitive).
     *
     * @param name Service name to find
     * @return Service object or null if not found
     */
    Service getService(String name);

    /**
     * Adds new service to repository.
     *
     * @param service Service to add
     */
    void addService(Service service);

    /**
     * Updates service rating information in repository.
     *
     * @param service Service that was rated
     * @param oldRounded Previous rounded rating
     * @param newRounded New rounded rating
     * @param oldFloat Previous exact rating
     * @param review New review added
     */
    void rateService(Service service, int oldRounded, int newRounded,
                     float oldFloat, Review review);

    /**
     * Gets best rated service of specific type.
     *
     * @param type Service type
     * @return Highest rated service of given type
     */
    Service getBestRatedService(String type);

    /**
     * Gets cheapest service of specific type.
     *
     * @param type Service type
     * @return Cheapest service of given type
     */
    Service getCheapestService(String type);

    /**
     * Checks if repository contains any services.
     *
     * @return true if at least one service exists
     */
    boolean hasServices();

    /**
     * Checks if repository has services of specific type.
     *
     * @param type Service type to check
     * @return true if no services of given type exist
     */
    boolean isServiceTypeEmpty(String type);

    /**
     * Checks if repository has services of type with minimum star rating.
     *
     * @param type Service type to filter
     * @param star Minimum star rating
     * @return true if services exist with given type and star rating
     */
    boolean isThereServiceWithStar(String type, int star);

}
