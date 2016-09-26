package ua.khpi.diplom.utils.socket;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

public class SocketUtils {

	/**
	 * Splits list on specified count of parts.
	 * 
	 * @param initialList
	 * @param countPart
	 * @return
	 */
	public static List<List<String>> splitList(List<String> initialList, int countPart) {
		List<List<String>> endList = new ArrayList<>();

		for (List<String> part : Lists.partition(initialList, initialList.size() / countPart)) {
			endList.add(part);
		}
		return endList;
	}
}
