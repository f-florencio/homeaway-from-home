package messages;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public enum Outputs {
    DISPLAY_NAME("%s\n"),
    EVALUATION_REG("Your evaluation has been registered!"),
    ERROR("%s"),
    BOUNDS_CREATED("%s created.\n"),
    BOUNDS_NOT_DEFINED("System bounds not defined."),
    MAP_SAVED("%s saved.\n"),
    BOUNDS_LOADED("%s loaded.\n"),
    BOUNDS_NOT_EXIST("Bounds %s does not exists.\n"),
    BYE("Bye!"),
    HELP("%s - %s\n"),
    UNKNOWN("Unknown command. Type help to see available commands."),
    SERVICE_ADDED("%s %s added.\n"),
    NO_SERVICE("No services yet!"),
    NO_SERVICE_IN_SYSTEM("No services in the system."),
    SERVICES_SORTED("Services sorted in descending order"),
    STUDENT_ADDED("%s added.\n"),
    STUDENT_REMOVED("%s has left.\n"),
    NO_STUDENTS("No students yet!"),
    NO_STUDENTS_FROM("No students from %s!\n"),
    STUDENT_DISPLAY("%s: %s at %s.\n"),
    STUDENT_DISTRACTED("%s is distracted!\n"),
    STUDENT_NEW_LOC("%s is now at %s.\n"),
    STUDENT_NEW_HOME("lodging %s is now %s's home. %s is at home.\n"),
    SERVICE_DISPLAY("%s: %s (%s, %s).\n"),
    WHERE_STUDENT("%s is at %s %s (%s, %s).\n"),
    USER_DISPLAY("%s: %s\n"),
    SERVICE_CLOSE_STAR("%s services closer with %s average\n"),
    SERVICE_DISPLAY_STAR("%s: %s\n"),
    NO_STUDENTS_IN_SERVICE("No students on %s!\n"),
    DISPLAY_TAGGED("%s %s\n"),
    NO_SERVICE_TAGGED("There are no services with this tag!");

    private final String message;

    Outputs(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

