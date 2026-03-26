package services;

import algorithms.StringMatcher;
import services.reviews.Review;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public abstract class AbstractService implements Service, Serializable {

    private final List<Review> reviews;
    private final ServiceType type;
    private final long latitude;
    private final long longitude;
    private final int price;
    private final int order;
    private final String name;

    private int numRatings;
    private int totalStars;
    private int lastTimeStarChanged;
    private float averageRating;
    private boolean roundedStarChanged;


    /**
     * Creates a new service with initial parameters.
     *
     * @param latitude  Service latitude coordinate
     * @param longitude Service longitude coordinate
     * @param name      Service name
     * @param price     Service price
     * @param type      Type of service
     * @param order     Creation order number
     */
    AbstractService(long latitude, long longitude, String name, int price,
                    ServiceType type, int order) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.type = type;
        this.price = price;
        this.reviews = new LinkedList<>();
        this.lastTimeStarChanged = 0;
        this.numRatings = 1;               // Start with 1 rating of 4 stars
        this.totalStars = 4;
        this.averageRating = 4;
        this.order = order;
        this.roundedStarChanged = false;
    }

    /**
     * Adds a review to the service.
     */
    private void addTags(Review review) {
        reviews.addLast(review);
    }

    /**
     * Checks if the rounded star rating changed since last check.
     *
     * @param changedCounter Current change counter
     * @return true if rating changed
     */
    @Override
    public boolean didStarChanged(int changedCounter) {
        if (roundedStarChanged) {
            roundedStarChanged = false;
            lastTimeStarChanged = changedCounter;
            return true;
        }
        return false;
    }

    /**
     * Evaluates the service with a new review and rating.
     *
     * @param review Review object
     * @param stars  Star rating
     */
    @Override
    public void evaluate(Review review, int stars) {
        int beforeRounded = getEvaluationAverageRounded();
        addTags(review);
        numRatings++;
        totalStars += stars;
        averageRating = (float) totalStars / numRatings;
        if (beforeRounded != getEvaluationAverageRounded())
            roundedStarChanged = true;
    }

    /**
     * Checks if service contains a specific tag.
     *
     * @param tag    Tag to search for
     * @param matcher String matcher algorithm
     * @return true if tag found in any review
     */
    @Override
    public boolean containsTag(Character[] tag, StringMatcher matcher) {
        for (Review r : reviews) {
            if (matcher.matches(tag, r.tags()))
                return true;
        }
        return false;
    }

    /**
     * Returns service latitude.
     */
    @Override
    public long getLatitude() {
        return latitude;
    }

    /**
     * Returns service longitude.
     */
    @Override
    public long getLongitude() {
        return longitude;
    }

    /**
     * Returns rounded average rating.
     */
    @Override
    public int getEvaluationAverageRounded() {
        return Math.round(averageRating);
    }

    /**
     * Returns exact average rating.
     */
    @Override
    public float getAverageRating() {
        return averageRating;
    }

    /**
     * Returns service name.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns service type.
     */
    @Override
    public ServiceType getType() {
        return type;
    }

    /**
     * Returns service price.
     */
    @Override
    public int getPrice() {
        return price;
    }

    /**
     * Returns last time rating changed.
     */
    @Override
    public int getLastTimeStarChanged() {
        return lastTimeStarChanged;
    }

    /**
     * Returns creation order.
     */
    @Override
    public int getOrderOfCreation() {
        return order;
    }
}

