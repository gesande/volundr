package net.sf.völundr.statistics;

import java.util.List;

public class MedianCalculator {

	public static int calculateFrom(final List<Integer> list) {
		Integer result = 0;
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

}
