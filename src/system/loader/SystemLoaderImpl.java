package system.loader;

import exceptions.BoundsExistException;
import system.application.*;

import java.io.*;
/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public class SystemLoaderImpl implements SystemLoader {

    @Override
    public SystemInterface load(String location) throws IOException, ClassNotFoundException {
        String normalizedLocation = normalizeLocation(location);
        FileInputStream fileIn = new FileInputStream(fileCreator(normalizedLocation));
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        SystemInterface systemInterface = (SystemInterface) objectIn.readObject();
        objectIn.close();
        fileIn.close();
        return systemInterface;
    }

    @Override
    public void save(SystemInterface systemInterface) throws IOException {
        String cityName = normalizeLocation(systemInterface.getCityName());
        FileOutputStream fileOut = new FileOutputStream(fileCreator(cityName));
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(systemInterface);
        out.close();
        fileOut.close();
    }

    @Override
    public boolean hasFile(String location) {
        String normalizedLocation = normalizeLocation(location);
        File file = fileCreator(normalizedLocation);
        return file.exists();
    }

    @Override
    public void existsPlaceCheck(String location, SystemInterface systemInterface) throws BoundsExistException {
        String normalizedLocation = normalizeLocation(location);
        boolean fileExists = hasFile(normalizedLocation);
        String cityNameNormalized = systemInterface != null ? normalizeLocation(systemInterface.getCityName()) : null;
        boolean isSameCity = systemInterface != null && cityNameNormalized.equals(normalizedLocation);
        if (isSameCity || fileExists)
            throw new BoundsExistException();
    }

    /**
     * Normalizes location string to file-friendly format.
     *
     * @param location Location name to normalize
     * @return Lowercase string with spaces replaced by underscores
     */
    private String normalizeLocation(String location) {
        return location.toLowerCase().replaceAll(" ", "_");
    }

    /**
     * Creates File object for given location.
     *
     * @param location File path string
     * @return File object for the location
     */
    private File fileCreator(String location) {
        return new File(location);
    }
}
