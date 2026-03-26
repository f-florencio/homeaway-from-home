package services;

import algorithms.StringMatcher;
import services.reviews.Review;

import java.io.Serializable;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public interface Service extends ServiceReadOnly, Serializable{
    /**
     * Checks if rounded star rating changed since last check.
     *
     * @param changedCounter Current change counter value
     * @return true if rating changed
     */
    boolean didStarChanged(int changedCounter);

    /**
     * Returns the creation order of this service.
     *
     * @return Order of creation number
     */
    int getOrderOfCreation();

    /**
     * Adds a review and rating to this service.
     *
     * @param review Review object with tags
     * @param stars  Star rating
     */
    void evaluate(Review review, int stars);

    /**
     * Checks if service contains a specific tag.
     *
     * @param tag     Tag to search for
     * @param matcher String matching algorithm
     * @return true if tag found in any review
     */
    boolean containsTag(Character[] tag, StringMatcher matcher);

    /**
     * Returns exact average rating.
     *
     * @return Average rating as float
     */
    float getAverageRating();

    /**
     * Returns service price.
     *
     * @return Price value
     */
    int getPrice();

    /**
     * Returns last time star rating changed.
     *
     * @return Change counter value when rating last changed
     */
    int getLastTimeStarChanged();
}
