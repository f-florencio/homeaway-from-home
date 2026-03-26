package system.repositories;

import services.reviews.Review;
import services.Service;
import services.ServiceType;
import structures.CompositeIterator;

import java.util.*;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public class ServiceRepositoryImpl implements ServiceRepository {

    private final int TOTAL_SERVICE_TYPES = ServiceType.values().length;
    private final int TOTAL_POSSIBLE_STARS = 5;
    private final int EXPECTED_NUM_SERVICES = 2500; //indicado no enunciado
    private final int COMBINATION_TYPE_STARS = TOTAL_POSSIBLE_STARS * TOTAL_SERVICE_TYPES;

    private Map<String, Service> services;
    private Map<String, SortedMap<Float, SortedSet<Service>>> servicesByTypeAndAverage; //usado no find de não thrifthy
    private Map<String,Service>[] servicesByStar; //usado no
    private Map<String, SortedSet<Service>> servicesByTypeAndStar;
    private Service[] cheapestServices; //usado no find do thrifty
    private Comparator<Service> comparatorForFind;

    public ServiceRepositoryImpl() {
        initDataStructures();
    }

    @SuppressWarnings("unchecked")
    /**
     * Initializes all data structures for service storage and organization.
     */
    private void initDataStructures() {
        services = new LinkedHashMap<>(EXPECTED_NUM_SERVICES);
        servicesByTypeAndAverage = new HashMap<>(TOTAL_SERVICE_TYPES);
        servicesByStar = new LinkedHashMap[TOTAL_POSSIBLE_STARS];
        servicesByTypeAndStar = new HashMap<>(COMBINATION_TYPE_STARS);
        cheapestServices = new Service[TOTAL_SERVICE_TYPES];
        comparatorForFind = new ServiceComparator();
        fillDataStructures();
    }

    /**
     * Fills data structures with initial empty containers.
     */
    private void fillDataStructures() {
        for (ServiceType serviceType : ServiceType.values()) {
            servicesByTypeAndAverage.put(serviceType.getName(), new TreeMap<>());
        }
        for(int i = 0; i < TOTAL_POSSIBLE_STARS; i++) {
            servicesByStar[i] = new LinkedHashMap<>();
        }
    }

    /**
     * Checks and updates cheapest service for a given service type.
     *
     * @param service Service to check against current cheapest
     */
    private void checkCheapest(Service service) {
        int indexType = service.getType().getIndex();
        Service cheapestServiceFound = cheapestServices[indexType];
        if (cheapestServiceFound == null) {
            cheapestServices[indexType] = service;
            return;
        }
        float servicePrice = service.getPrice();
        float cheapestPrice = cheapestServiceFound.getPrice();
        if (servicePrice < cheapestPrice) {
            cheapestServices[indexType] = service;
        }
    }

    @Override
    public Iterator<Service> getAllServicesIterator() {
        return services.values().iterator();
    }


    @Override
    public Iterator<Service> servicesOrderedByStarIterator() {
        return new CompositeIterator<>(servicesByStar);
    }

    @Override
    public Iterator<Service> getTypeAndStarIterator(String type, int star) {
        type = ServiceType.valueOf(type.toUpperCase()).getName();
        String key = concatenateKey(type, star);
        SortedSet<Service> services = servicesByTypeAndStar.get(key);
        return services.iterator();
    }

    @Override
    public Service getService(String name) {
        return services.get(name.toLowerCase());
    }

    private String concatenateKey(String type, int star) {
        return type + "_" + star;
    }

    @Override
    public void addService(Service service) {
        String serviceName = service.getName().toLowerCase();
        String typeName = service.getType().getName();
        float average = service.getAverageRating();
        int rounded = service.getEvaluationAverageRounded();
        int roundedIdx = rounded - 1;
        String key = concatenateKey(typeName, rounded);
        servicesByTypeAndAverage.get(typeName).computeIfAbsent(average, k -> new TreeSet<>(comparatorForFind));
        servicesByTypeAndStar.computeIfAbsent(key, k -> new TreeSet<>(comparatorForFind));
        services.put(serviceName, service);
        servicesByTypeAndAverage.get(typeName).get(average).add(service);
        servicesByTypeAndStar.get(key).add(service);
        servicesByStar[roundedIdx].put(serviceName, service);
        checkCheapest(service);
    }

    /**
     * Updates star-based service grouping when rating changes.
     *
     * @param service Service whose rating changed
     * @param oldRounded Previous rounded star rating (1-5)
     * @param newRounded New rounded star rating (1-5)
     */
    private void updateServicesByStar(Service service, int oldRounded, int newRounded) {
        if (oldRounded != newRounded) {
            String serviceName = service.getName().toLowerCase();
            int idxOld = oldRounded - 1;
            int idxNew = newRounded - 1;
            servicesByStar[idxOld].remove(serviceName);
            servicesByStar[idxNew].put(serviceName, service);
        }
    }

    /**
     * Updates type and average rating grouping when rating changes.
     *
     * @param service Service whose rating changed
     * @param typeName Service type name
     * @param oldFloat Previous exact average rating
     * @param newFloat New exact average rating
     */
    private void updateServicesByTypeAndAverage(Service service, String typeName, float oldFloat, float newFloat) {
        SortedMap<Float, SortedSet<Service>> typeMap = servicesByTypeAndAverage.get(typeName);
        SortedSet<Service> oldAvgList = typeMap.get(oldFloat);
        oldAvgList.remove(service);
        if (oldAvgList.isEmpty())
            typeMap.remove(oldFloat);
        typeMap.computeIfAbsent(newFloat, k -> new TreeSet<>(comparatorForFind));
        typeMap.get(newFloat).add(service);
    }

    /**
     * Updates type and star rating grouping when rating changes.
     *
     * @param service Service whose rating changed
     * @param typeName Service type name
     * @param oldRounded Previous rounded star rating
     * @param newRounded New rounded star rating
     * */
    private void updateServicesByTypeAndStar(Service service, String typeName, int oldRounded, int newRounded) {
        if (oldRounded != newRounded) {
            String oldKey = concatenateKey(typeName, oldRounded);
            String newKey = concatenateKey(typeName, newRounded);
            SortedSet<Service> oldMap = servicesByTypeAndStar.get(oldKey);
            if (oldMap != null) {
                oldMap.remove(service);
                if (oldMap.isEmpty())
                    servicesByTypeAndStar.remove(oldKey);
            }
            servicesByTypeAndStar.computeIfAbsent(newKey, k -> new TreeSet<>(comparatorForFind));
            servicesByTypeAndStar.get(newKey).add(service);
        }
    }

    @Override
    public void rateService(Service service, int oldRounded, int newRounded, float oldFloat, Review review) {
        String typeName = service.getType().getName();
        float newFloat = service.getAverageRating();
        updateServicesByStar(service, oldRounded, newRounded);
        updateServicesByTypeAndAverage(service, typeName, oldFloat, newFloat);
        updateServicesByTypeAndStar(service, typeName, oldRounded, newRounded);
    }

    @Override
    public Service getBestRatedService(String type) {
        SortedMap<Float, SortedSet<Service>> typeMap = servicesByTypeAndAverage.get(type);
        Map.Entry<Float, SortedSet<Service>> maxAverageEntry = typeMap.lastEntry();
        SortedSet<Service> servicesWithMaxAverage = maxAverageEntry.getValue();
        return servicesWithMaxAverage.getFirst();
    }

    @Override
    public Service getCheapestService(String type) {
        ServiceType serviceType = ServiceType.valueOf(type.toUpperCase());
        return cheapestServices[serviceType.getIndex()];
    }

    @Override
    public boolean hasServices() {
        return !services.isEmpty();
    }

    @Override
    public boolean isServiceTypeEmpty(String type) {
        return getCheapestService(type) == null;
    }

    @Override
    public boolean isThereServiceWithStar(String type, int star) {
        type = ServiceType.valueOf(type.toUpperCase()).getName();
        String key = concatenateKey(type, star);
        SortedSet<Service> serviceMap = servicesByTypeAndStar.get(key);
        return serviceMap != null && !serviceMap.isEmpty();
    }
}
