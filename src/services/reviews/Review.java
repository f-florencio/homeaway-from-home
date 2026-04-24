package services.reviews;

import java.io.Serializable;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public record Review(int star, String description) implements Serializable {

}
