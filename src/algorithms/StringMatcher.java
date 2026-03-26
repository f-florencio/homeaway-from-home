package algorithms;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public interface StringMatcher {

    /**
     * Builds the LPS array for a pattern.
     *
     * @param pattern The search pattern
     * @return LPS array for the pattern
     */
    int[] buildLPS(char[] pattern);

    /**
     * Checks if pattern appears as a complete word in text.
     *
     * @param patternArr Pattern to search for
     * @param textArr Text to search in
     * @return true if pattern found as complete word
     */
    boolean matches(Character[] patternArr, Character[] textArr);
}


