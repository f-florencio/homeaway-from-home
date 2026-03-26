package messages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public enum ExceptionMessages {
    THRIFTY_STUDENT("%s is thrifty!\n"),
    NO_SERVICE_AVERAGE("No %s services with average!\n"),
    INV_EVALUATION("Invalid evaluation!\n"),
    INV_STARS("Invalid stars!\n"),
    NO_TYPE_SERVICES("No %s services!\n"),
    NOT_VISITED("%s has not visited any locations!\n"),
    INV_TYPE("Invalid %s type!\n"),
    BOUNDS_EXISTS("Bounds already exists. Please load it!\n"),
    INV_BOUNDS("Invalid bounds.\n"),
    INV_LOCATION("Invalid location!\n"),
    INV_PRICE("Invalid %s price!\n"),
    INV_CAPACITY("Invalid capacity!\n"),
    INV_DISCOUNT("Invalid discount price!\n"),
    ALR_EXISTS("%s already exists!\n"),
    THING_NOT_EXISTS("%s does not exist!\n"),
    PLACE_NOT_EXISTS("%s %s does not exist!\n"),
    UNKNOWN_LOC("Unknown %s!\n"),
    NOT_VALID_SERVICE("%s is not a valid service!\n"),
    INVALID_SERVICE_TYPE("Invalid service type!\n"),
    ALR_THERE("Already there!\n"),
    DISTRACTED_STUDENT("%s is now at %s. %s is distracted!\n"),
    SAME_HOME("That is %s's home!\n"),
    MOVE_NOT_ACCEPTED("Move is not acceptable for %s!\n"),
    ORDER_NOT_EXIST("This order does not exists!\n"),
    NOT_CONTROLLED("%s does not control student entry and exit!\n"),
    PLACE_FULL("%s %s is full!\n"),
    ;


    private final String message;

    ExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
