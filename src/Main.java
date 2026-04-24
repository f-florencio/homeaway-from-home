import exceptions.*;
import messages.Commands;
import messages.Outputs;
import messages.PairOutput;
import services.Service;
import services.ServiceReadOnly;
import students.Student;
import students.StudentReadOnly;
import system.application.SystemClass;
import system.application.SystemInterface;
import system.loader.SystemLoader;
import system.loader.SystemLoaderImpl;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import static messages.Outputs.*;


/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/


public class Main {

    private static final String ALL = "all";
    private static boolean boundsSet;
    private static SystemInterface systemInterface;


    private static void findService(Scanner in) {
        if (!checkBounds(2, in)) return;
        String name = in.nextLine().trim();
        String type = in.nextLine().trim();
        try {
            String serviceName = systemInterface.findServiceForStudent(name, type);
            outputWriter(DISPLAY_NAME, serviceName);
        } catch (InvalidServiceTypeException | ThingNotExistsException | NoTypeServiceException e) {
            outputWriter(ERROR, e.getMessage());
        }
    }

    private static void listTaggedServices(Scanner in) {
        if (!checkBounds(2, in)) return;
        String tag = in.nextLine().trim().toLowerCase();
        boolean anyListed = false;
        Iterator<Service> it = systemInterface.getServicesTaggedWith(tag);
        while (it.hasNext()) {
            anyListed = true;
            ServiceReadOnly s = it.next();
            outputWriter(DISPLAY_TAGGED, s.getType().getName(), s.getName());
        }
        if (!anyListed)
            outputWriter(NO_SERVICE_TAGGED);

    }

    private static void listPlacesDistance(Scanner in) {
        if (!checkBounds(1, in)) return;
        String type = in.next().trim();
        int star = in.nextInt();
        String name = in.nextLine().trim();
        try {
            Iterator<Service> it = systemInterface.getServicesByTypeAndStar(type, star, name);
            outputWriter(SERVICE_CLOSE_STAR, type, String.valueOf(star));
            while (it.hasNext()) {
                ServiceReadOnly s = it.next();
                outputWriter(DISPLAY_NAME, s.getName());
            }
        } catch (InvalidStarsException | ThingNotExistsException | InvalidTypeException |
                 NoTypeServiceException | NoTypeServiceWithAverageException e) {
            outputWriter(ERROR, e.getMessage());
        }
    }

    private static void rankPlacesByStar() {
        if (!boundsSet) {
            outputWriter(BOUNDS_NOT_DEFINED);
            return;
        }
        if (!systemInterface.hasServices()) {
            outputWriter(NO_SERVICE_IN_SYSTEM);
            return;
        }
        Iterator<Service> it = systemInterface.getServicesOrdered();
        outputWriter(SERVICES_SORTED);
        while (it.hasNext()) {
            ServiceReadOnly s = it.next();
            outputWriter(SERVICE_DISPLAY_STAR, s.getName(), String.valueOf(s.getEvaluationAverageRounded()));
        }
    }

    private static void ratePlace(Scanner in) {
        if (!checkBounds(2, in)) return;
        int star = in.nextInt();
        String serviceName = in.nextLine().trim();
        String description = in.nextLine().trim().toLowerCase();
        try {
            systemInterface.rateService(star, serviceName, description);
            outputWriter(EVALUATION_REG);
        } catch (InvalidEvaluationException | ThingNotExistsException e) {
            outputWriter(ERROR, e.getMessage());
        }
    }

    private static void listVisitedPlaces(Scanner in) {
        if (!checkBounds(1, in)) return;
        String name = in.nextLine().trim();
        try {
            Iterator<Service> it = systemInterface.getServicesVisitedByStudent(name);
            while (it.hasNext()) {
                ServiceReadOnly s = it.next();
                outputWriter(DISPLAY_NAME, s.getName());
            }
        } catch (ThingNotExistsException | ThriftyStudentException | NotVisitedPlacesException e) {
            outputWriter(ERROR, e.getMessage());
        }
    }

    private static void locateStudent(Scanner in) {
        if (!checkBounds(1, in)) return;
        String name = in.nextLine().trim();
        try {
            PairOutput output = systemInterface.getStudentLocation(name);
            String studentName = output.student().getName();
            String serviceName = output.service().getName();
            String type = output.service().getType().getName();
            String lat = String.valueOf(output.service().getLatitude());
            String lon = String.valueOf(output.service().getLongitude());
            outputWriter(WHERE_STUDENT, studentName, serviceName, type, lat, lon);
        } catch (ThingNotExistsException e) {
            outputWriter(ERROR, e.getMessage());
        }
    }

    private static void listStudentsAtService(Scanner in) {
        if (!checkBounds(1, in)) return;
        String orderType = in.next();
        String serviceName = in.nextLine().trim();
        try {
            Iterator<Student> it = systemInterface.getStudentsAtLocation(orderType, serviceName);
            boolean hasAny = false;
            while (it.hasNext()) {
                hasAny = true;
                StudentReadOnly s = it.next();
                outputWriter(USER_DISPLAY, s.getName(), s.getType().getName());
            }
            if (!hasAny) {
                outputWriter(NO_STUDENTS_IN_SERVICE, systemInterface.getCorrectServiceName(serviceName));
            }
        } catch (OrderNotExistException | ThingNotExistsException | NotControlledEntryException e) {
            outputWriter(ERROR, e.getMessage());
        }
    }

    private static void changeLodgeStudent(Scanner in) {
        if (!checkBounds(1, in)) return;
        String name = in.nextLine().trim();
        String location = in.nextLine().trim();
        try {
            PairOutput output = systemInterface.changeStudentLodge(name, location);
            String studentName = output.student().getName();
            String serviceName = output.service().getName();
            outputWriter(STUDENT_NEW_HOME, serviceName, studentName, studentName);
        } catch (PlaceNotExistsException | ThingNotExistsException | SameHomeException | PlaceFullException |
                 NotAcceptedMoveException e) {
            outputWriter(ERROR, e.getMessage());
        }
    }

    private static void changeLocationStudent(Scanner in) {
        if (!checkBounds(2, in)) return;
        String name = in.nextLine().trim();
        String location = in.nextLine().trim();
        try {
            PairOutput output = systemInterface.changeStudentLocation(name, location);
            String studentName = output.student().getName();
            String serviceName = output.service().getName();
            outputWriter(STUDENT_NEW_LOC, studentName, serviceName);

        } catch (UnknownLocationException | ThingNotExistsException | NotValidServiceException |
                 AlreadyThereException | StudentDistractedException | PlaceFullException e) {
            outputWriter(ERROR, e.getMessage());
        }
    }

    private static void listStudents(Scanner in) {
        if (!checkBounds(1, in)) return;
        String restriction = in.nextLine().trim();
        if (!systemInterface.hasStudentFrom(restriction)) {
            if (restriction.equalsIgnoreCase(ALL))
                outputWriter(NO_STUDENTS);
            else
                outputWriter(NO_STUDENTS_FROM, restriction);
            return;
        }
        Iterator<Student> it = systemInterface.getStudents(restriction);
        while (it.hasNext()) {
            StudentReadOnly s = it.next();
            outputWriter(STUDENT_DISPLAY, s.getName(), s.getType().getName(), s.getLocationName());
        }
    }

    private static void removeStudent(Scanner in) {
        if (!checkBounds(1, in)) return;
        String name = in.nextLine().trim();
        try {
            name = systemInterface.removeStudent(name);
            outputWriter(STUDENT_REMOVED, name);
        } catch (ThingNotExistsException e) {
            outputWriter(ERROR, e.getMessage());
        }
    }

    private static void addStudent(Scanner in) {
        if (!checkBounds(4, in)) return;
        String type = in.nextLine().trim();
        String name = in.nextLine().trim();
        String country = in.nextLine().trim();
        String residency = in.nextLine().trim();
        try {
            systemInterface.addStudent(type, name, country, residency);
            outputWriter(STUDENT_ADDED, name);
        } catch (InvalidTypeException | PlaceNotExistsException | PlaceFullException | AlreadyExistsException e) {
            outputWriter(ERROR, e.getMessage());
        }
    }

    private static void listServices() {
        if (!boundsSet) {
            outputWriter(BOUNDS_NOT_DEFINED);
            return;
        }
        boolean hasAny = false;
        Iterator<Service> it = systemInterface.getAllServices();
        String name, type, lat, lon;
        while (it.hasNext()) {
            hasAny = true;
            ServiceReadOnly s = it.next();
            name = s.getName();
            type = s.getType().getName();
            lat = String.valueOf(s.getLatitude());
            lon = String.valueOf(s.getLongitude());
            outputWriter(SERVICE_DISPLAY, name, type, lat, lon);
        }
        if (!hasAny) {
            outputWriter(NO_SERVICE);
        }
    }

    private static void addService(Scanner in) {
        try {
            if (!checkBounds(1, in)) return;
            String type = in.next().toLowerCase();
            long latitude = in.nextLong();
            long longitude = in.nextLong();
            int price = in.nextInt();
            int value = in.nextInt();
            String name = in.nextLine().trim();
            systemInterface.addPlace(latitude, longitude, price, value, name, type);
            outputWriter(SERVICE_ADDED, type, name);
        } catch (InvalidTypeException | InvalidPriceException | InvalidLocationException |
                 InvalidDiscountPriceException | InvalidCapacityException | AlreadyExistsException e) {
            outputWriter(ERROR, e.getMessage());
        }
    }

    private static void boundsErrorChecker(long leftLat, long leftLong, long rightLat, long rightLong) throws InvalidBoundsException {
        if (leftLat <= rightLat || rightLong <= leftLong)
            throw new InvalidBoundsException();
    }

    private static boolean checkBounds(int linesToSkip, Scanner in) {
        if (!boundsSet) {
            lineConsumer(linesToSkip, in);
            outputWriter(BOUNDS_NOT_DEFINED);
            return false;
        }
        return true;
    }

    private static void lineConsumer(int linesToSkip, Scanner in) {
        for (int i = 0; i < linesToSkip; i++) {
            in.nextLine();
        }
    }

    private static void setBounds(Scanner in) {
        try {
            SystemLoader systemLoader = new SystemLoaderImpl();
            long leftLat = in.nextLong();
            long leftLong = in.nextLong();
            long rightLat = in.nextLong();
            long rightLong = in.nextLong();
            String location = in.nextLine().trim();
            systemLoader.existsPlaceCheck(location, systemInterface);
            boundsErrorChecker(leftLat, leftLong, rightLat, rightLong);
            if (boundsSet)
                save();
            systemInterface = new SystemClass(leftLat, leftLong, rightLat, rightLong, location);
            boundsSet = true;
            outputWriter(BOUNDS_CREATED, location);
        } catch (BoundsExistException | InvalidBoundsException | IOException e) {
            outputWriter(ERROR, e.getMessage());
        }
    }

    private static void saveMap() {
        if (!boundsSet) {
            outputWriter(BOUNDS_NOT_DEFINED);
            return;
        }
        try {
            save();
            outputWriter(MAP_SAVED, systemInterface.getCityName());
        } catch (IOException e) {
            outputWriter(ERROR, e.getMessage());
        }
    }

    private static void save() throws IOException {
        SystemLoader systemLoader = new SystemLoaderImpl();
        systemLoader.save(systemInterface);
    }

    private static void loadMap(Scanner in) {
        String location = in.nextLine().trim();
        SystemLoader systemLoader = new SystemLoaderImpl();
        if (!systemLoader.hasFile(location)) {
            outputWriter(BOUNDS_NOT_EXIST, location);
            return;
        }
        try {
            if (boundsSet)
                save();
            systemInterface = systemLoader.load(location);
            boundsSet = true;
            outputWriter(BOUNDS_LOADED, systemInterface.getCityName());
        } catch (IOException | ClassNotFoundException e) {
            outputWriter(ERROR, e.getMessage());
        }
    }

    private static Commands commandGetter(String command) {
        try {
            return Commands.valueOf(command);
        } catch (IllegalArgumentException e) {
            return Commands.UNKNOWN;
        }
    }

    private static void getMethodCommand(Commands command, Scanner in) {
        switch (command) {
            case HELP -> printAllCommands();
            case BOUNDS -> setBounds(in);
            case SAVE -> saveMap();
            case LOAD -> loadMap(in);
            case SERVICE -> addService(in);
            case SERVICES -> listServices();
            case STUDENT -> addStudent(in);
            case LEAVE -> removeStudent(in);
            case STUDENTS -> listStudents(in);
            case GO -> changeLocationStudent(in);
            case MOVE -> changeLodgeStudent(in);
            case USERS -> listStudentsAtService(in);
            case WHERE -> locateStudent(in);
            case VISITED -> listVisitedPlaces(in);
            case STAR -> ratePlace(in);
            case RANKING -> rankPlacesByStar();
            case RANKED -> listPlacesDistance(in);
            case TAG -> listTaggedServices(in);
            case FIND -> findService(in);
            case UNKNOWN -> outputWriter(UNKNOWN);
        }
    }

    private static void inputReceiver(Scanner in) {
        String command;
        do {
            command = in.next().trim().toUpperCase();
            if (!command.equals(Commands.EXIT.getName().toUpperCase())) {
                getMethodCommand(commandGetter(command), in);
            }
        } while (!command.equals(Commands.EXIT.getName().toUpperCase()));
        try {
            save();
        } catch (IOException e) {
           outputWriter(ERROR, e.getMessage());
        }
        outputWriter(BYE);
    }

    private static void outputWriter(Outputs output, String...args) {
        if (args.length == 0) {
            System.out.println(output.getMessage());
        } else {
            System.out.printf(output.getMessage(), (Object[]) args);
        }
    }

    private static void printAllCommands() {
        for (Commands command : Commands.values()) {
            if (command != Commands.UNKNOWN)
                outputWriter(HELP, command.getName(), command.getDescription());
        }
    }

    public static void main(String[] args) {
        boundsSet = false;
        Scanner in = new Scanner(System.in);
        inputReceiver(in);
        in.close();
    }
}
