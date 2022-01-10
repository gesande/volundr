package org.fluentjava.volundr.statistics;

import java.util.List;

public final class MedianResolver {

    /**
     * Resolve median value
     * <p>
     * http://en.wikipedia.org/wiki/Median
     *
     * @param list
     *            list of values
     * @return the middle value if number of entries is not even in the list
     *         otherwise the middle two values and an average of them
     */
    @SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
    public static int resolveFrom(final List<Integer> list) {
        Integer result;
        final int size = list.size();
        // If the number of entries in the list is not even.
        if (size % 2 == 1) {
            result = list.get(size / 2); // Get the middle
                                         // value.
        } else { // If the number of entries in the list are even.
            final Integer lowerMiddle = list.get(size / 2);
            final Integer upperMiddle = list.get(size / 2 - 1);
            // Get the middle two values and average them.
            result = (lowerMiddle + upperMiddle) / 2;
        }
        return result;
    }

    @SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
    public static long resolveFromLong(final List<Long> list) {
        Long result;
        final int size = list.size();
        // If the number of entries in the list is not even.
        if (size % 2 == 1) {
            result = list.get(size / 2); // Get the middle
                                         // value.
        } else { // If the number of entries in the list are even.
            final Long lowerMiddle = list.get(size / 2);
            final Long upperMiddle = list.get(size / 2 - 1);
            // Get the middle two values and average them.
            result = (lowerMiddle + upperMiddle) / 2;
        }
        return result;
    }

}
