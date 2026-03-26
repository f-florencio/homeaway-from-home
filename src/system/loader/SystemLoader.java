package system.loader;

import exceptions.BoundsExistException;
import system.application.SystemInterface;

import java.io.IOException;
/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public interface SystemLoader {

    /**
     * Loads system from file.
     *
     * @param name File name to load from
     * @return Loaded system interface
     * @throws IOException If file reading fails
     * @throws ClassNotFoundException If serialized class not found
     */
    SystemInterface load(String name) throws IOException, ClassNotFoundException;

    /**
     * Saves system to file.
     *
     * @param systemInterface System to save
     * @throws IOException If file writing fails
     */
    void save(SystemInterface systemInterface) throws IOException;

    /**
     * Checks if file exists at location.
     *
     * @param location File path
     * @return true if file exists
     */
    boolean hasFile(String location);

    /**
     * Checks if city bounds already exist at location.
     *
     * @param location City location name
     * @param systemInterface System to check
     * @throws BoundsExistException If bounds already exist for location
     */
    void existsPlaceCheck(String location, SystemInterface systemInterface) throws BoundsExistException;
}
