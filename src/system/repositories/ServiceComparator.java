package system.repositories;

import services.Service;
import java.io.Serializable;
import java.util.Comparator;

public class ServiceComparator implements Comparator<Service>, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public int compare(Service s1, Service s2) {
        int cmp = Integer.compare(s1.getLastTimeStarChanged(), s2.getLastTimeStarChanged());
        if (cmp != 0)
            return cmp;
        int cmp2 = Integer.compare(s1.getOrderOfCreation(), s2.getOrderOfCreation());
        if (cmp2 != 0)
            return cmp2;
        return s1.getName().compareTo(s2.getName());
    }
}