package students;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public enum StudentType {
    BOOKISH("bookish"),
    OUTGOING("outgoing"),
    THRIFTY("thrifty");

    final String name;

    StudentType(String name) {
        this.name = name;
    }

    public static StudentType safeValueOf(String name) {
        for (StudentType type : StudentType.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

}
