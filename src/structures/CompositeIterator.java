package structures;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Composite Iterator that iterates over an array of Maps in reverse order
 * (from maximum index to minimum index).
 * Automatically skips empty maps.
 *
 */

public class CompositeIterator<E> implements Iterator<E> {

    /**
     * Array of maps to iterate over
     */
    private final Map<?, E>[] maps;

    /**
     * Current index in the array (starts at max, goes to 0)
     */
    private int currentIndex;

    /**
     * Current iterator from the map at currentIndex
     */
    private Iterator<E> currentIterator;

    /**
     * Maximum index to start from (typically array.length - 1)
     */
    private int maxIndex;

    /**
     * Constructor for CompositeIterator
     *
     * @param maps Array of maps to iterate over
     */
    public CompositeIterator(Map<?, E>[] maps) {
        this.maps = maps;
        this.maxIndex = maps.length - 1;
        currentIndex = maxIndex;
        currentIterator = null;
        advanceToNextNonEmptyIndex();
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     *
     * @return true iff the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        while (currentIterator == null || !currentIterator.hasNext()) {
            if (currentIndex < 0) {
                currentIterator = null;
                return false;
            }
            if (currentIterator != null && !currentIterator.hasNext()) {
                currentIndex--;
            }
            advanceToNextNonEmptyIndex();
            if (currentIterator == null) {
                return false;
            }
        }
        return currentIterator != null && currentIterator.hasNext();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    @Override
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return currentIterator.next();
    }

    /**
     * Advances to the next non-empty index in the array.
     * Starts from currentIndex and goes down to 0.
     */

    @SuppressWarnings("unchecked")
    private void advanceToNextNonEmptyIndex() {
        while (currentIndex >= 0) {
            Map<?, E> currentMap = maps[currentIndex];
            if (currentMap != null && !currentMap.isEmpty()) {
                currentIterator = currentMap.values().iterator();
                if (currentIterator.hasNext()) {
                    return;
                }
            }
            currentIndex--;
        }
        currentIterator = null;
    }
}

