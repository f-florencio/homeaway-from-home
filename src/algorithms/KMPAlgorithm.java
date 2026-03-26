package algorithms;

/**
 * @author Francisco Florencio (70881) f.florencio@campus.fct.unl.pt
 **/

public class KMPAlgorithm implements StringMatcher {

    /**
     * Builds the LPS array for KMP pattern matching.
     *
     * @param pattern Character array to preprocess
     * @return LPS array where lps[i] = longest prefix length that is also suffix for pattern
     */
    public int[] buildLPS(char[] pattern) {
        int m = pattern.length;
        int[] lps = new int[m];
        int len = 0;
        int i = 1;

        while (i < m) {
            if (pattern[i] == pattern[len]) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) len = lps[len - 1];
                else { lps[i] = 0; i++; }
            }
        }

        return lps;
    }

    /**
     * Checks if character is a word boundary.
     *
     * @param c Character to check
     * @return true if character is not letter or digit
     */
    private boolean isWordBoundary(char c) {
        return !( (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') );
    }

    /**
     * Searches for pattern in text using KMP algorithm with whole-word matching.
     *
     * @param patternArr Pattern to search for
     * @param textArr Text to search within
     * @return true if pattern found as complete word
     */
    @Override
    public boolean matches(Character[] patternArr, Character[] textArr) {
        if (patternArr.length == 0) return false;
        char[] pattern = new char[patternArr.length];
        char[] text = new char[textArr.length];
        for (int i = 0; i < patternArr.length; i++) pattern[i] = patternArr[i];
        for (int i = 0; i < textArr.length; i++) text[i] = textArr[i];
        int[] lps = buildLPS(pattern);
        int i = 0, j = 0;
        while (i < text.length) {
            if (pattern[j] == text[i]) {
                i++;
                j++;
            }
            if (j == pattern.length) {
                int start = i - j;
                boolean startOk = (start == 0) || isWordBoundary(text[start - 1]);
                boolean endOk = (i == text.length) || isWordBoundary(text[i]);
                if (startOk && endOk) return true;
                j = lps[j - 1];
            }
            else if (i < text.length && pattern[j] != text[i]) {
                if (j != 0) j = lps[j - 1];
                else i++;
            }
        }
        return false;
    }
}