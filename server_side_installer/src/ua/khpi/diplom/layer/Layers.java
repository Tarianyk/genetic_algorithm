package ua.khpi.diplom.layer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ua.khpi.diplom.utils.constant.Constants;
import ua.khpi.diplom.utils.io.FileReader;

public class Layers {

	/**
	 * Generates layers for macrose.
	 * 
	 * @param initialPopulation
	 *            initial population
	 * @return generated layers
	 */
	public List<String> generateLayer(List<Integer[]> initialPopulation) {
		Iterator<Integer[]> iterator = initialPopulation.iterator();

		List<String> layers = new ArrayList<>(initialPopulation.size());
		StringBuilder sb = new StringBuilder();

		while (iterator.hasNext()) {
			Integer[] temp = iterator.next();
			for (Integer i : temp) {
				sb.append(i).append(",");
			}
			layers.add(Constants.LAYER + sb.substring(0, sb.length() - 1));
			sb.setLength(0);
		}

		return layers;
	}

	/**
	 * Adds layer to file.
	 * 
	 * @param str
	 */
	public void addLayerToFile(String str) {
		List<String> text_from_file = FileReader.getInput(Constants.PATH_TO_FILE);

		for (int i = 0; i < text_from_file.size(); i++) {
			if (text_from_file.get(i).trim().equals(Constants.MARK_LAYER)) {
				text_from_file.add(i + 1, str + Constants.LINE_SEPARATOR);
				break;
			} else
				continue;
		}
		FileReader.writeToFile(text_from_file, Constants.PATH_TO_FILE);

	}

	/**
	 * Deletes layers from file.
	 */
	public void deleteLayerFromFile() {
		List<String> text_from_file = FileReader.getInput(Constants.PATH_TO_FILE);

		for (int i = 0; i < text_from_file.size(); i++) {
			if (text_from_file.get(i).trim().contains(Constants.LAYER)) {
				text_from_file.remove(i);
				break;
			} else
				continue;
		}
		FileReader.writeToFile(text_from_file, Constants.PATH_TO_FILE);
	}

	/**
	 * Parses input data (string) to array of integer.
	 * 
	 * @param layers
	 * @return array
	 */
	public Integer[] parse(String layers) {
		layers = layers.trim().substring(7, layers.length());

		String[] lay_mas_str = layers.split(",");
		Integer[] lay_int = new Integer[10];

		for (int j = 0; j < 10; j++) {
			lay_int[j] = Integer.valueOf(lay_mas_str[j].trim());
		}

		return lay_int;
	}
}
