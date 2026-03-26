package messages;

import services.ServiceReadOnly;
import students.StudentReadOnly;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public record PairOutput(StudentReadOnly student, ServiceReadOnly service) {
}
