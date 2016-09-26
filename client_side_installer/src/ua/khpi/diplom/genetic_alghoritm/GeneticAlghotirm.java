package ua.khpi.diplom.genetic_alghoritm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import ua.khpi.diplom.ansys.AnsysProcess;
import ua.khpi.diplom.layer.Layers;
import ua.khpi.diplom.utils.constant.Constants;
import ua.khpi.diplom.utils.io.FileReader;
import ua.khpi.model.TransferData;

public class GeneticAlghotirm {

	/**
	 * Creates initial population.
	 * 
	 * @param populationSize
	 *            number of populations
	 * @param numberChromosomes
	 *            number of chromosomes
	 * @return list that contains initial population
	 */
	public List<Integer[]> initialPopulation(int populationSize, int numberChromosomes) {
		List<Integer[]> population = new ArrayList<>(populationSize);
		Random random = new Random();
		for (int i = 0; i < populationSize; i++) {
			Integer[] chromos = new Integer[numberChromosomes];
			for (int j = 0; j < numberChromosomes; j++) {
				chromos[j] = random.nextInt(2);
			}
			population.add(chromos);
		}
		return population;
	}

	/**
	 * Calculates fitness for initial population.
	 * 
	 * @param generatedLayers
	 *            generated layers
	 * @param layers
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public List<TransferData> calculateFitness(List<TransferData> transferData, Layers layers)
			throws IOException, InterruptedException {
		AnsysProcess process = new AnsysProcess();

		// adds to file generated layers and calculates
		for (int i = 0; i < transferData.size(); i++) {

			// adds layer to file
			layers.addLayerToFile(transferData.get(i).getGeneratedLayers());

			// creates process
			System.err.println(Constants.PATH_TO_ANSYS + Constants.PATH_WITH_PARAMETERS);
			process.createProcess(Constants.PATH_TO_ANSYS + Constants.PATH_WITH_PARAMETERS);

			// deletes layer from file
			layers.deleteLayerFromFile();

			transferData.get(i).setResults(FileReader.getResult());

			FileReader.deleteDirectory(Constants.PATH_TO_FOLDER);
		}

		return transferData;
	}

	/**
	 * Prints specified list.
	 * 
	 * @param list
	 * @param name
	 */
	private <E> void printListArray(List<E[]> list, String name) {
		Iterator<E[]> iterator = list.iterator();

		System.err.println(name);
		while (iterator.hasNext()) {
			System.out.println(Arrays.asList(iterator.next()));
		}
	}

	/**
	 * Prints specified list.
	 * 
	 * @param list
	 * @param name
	 */
	public <E> void printListString(List<String> str, String name) {
		System.err.println(name);
		for (String i : str) {
			System.out.println(i);
		}
	}

}
