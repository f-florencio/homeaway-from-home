package students;

import services.Service;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public class OutgoingStudent extends AbstractNonThrifty implements Outgoing {
    public OutgoingStudent(StudentType type, String name, String country, Service residence) {
        super(type, name, country, residence);
    }
}
