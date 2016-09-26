package ua.khpi.diplom.utils.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ua.khpi.model.TransferData;

/**
 * The util class for sorting collection.
 * 
 * @author Anton
 *
 */
public class Sorter {

	private static final Comparator<TransferData> SORT = new Comparator<TransferData>() {

		@Override
		public int compare(TransferData objOne, TransferData objTwo) {
			return objOne.getResults().compareTo(objTwo.getResults());
		}
	};

	public static final List<TransferData> searchBest(List<TransferData> list) {
		List<TransferData> temp = list;

		Collections.sort(temp, SORT);

		return temp;
	}

}
