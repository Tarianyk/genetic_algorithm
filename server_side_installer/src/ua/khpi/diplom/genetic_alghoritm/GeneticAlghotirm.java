package ua.khpi.diplom.genetic_alghoritm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ua.khpi.diplom.ansys.AnsysProcess;
import ua.khpi.diplom.layer.Layers;
import ua.khpi.diplom.utils.constant.Constants;
import ua.khpi.diplom.utils.io.FileReader;
import ua.khpi.diplom.utils.util.Sorter;
import ua.khpi.model.Parents;
import ua.khpi.model.TransferData;

/**
 * The class used for calculating GA-alghoritm.
 * 
 * @author Anton
 *
 */
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
			process.createProcess(Constants.PATH_TO_ANSYS + Constants.PATH_WITH_PARAMETERS);

			// deletes layer from file
			layers.deleteLayerFromFile();

			transferData.get(i).setResults(FileReader.getResult());
			//
			FileReader.deleteDirectory(Constants.PATH_TO_FOLDER);
		}

		return transferData;
	}

	/**
	 * Generates other population.
	 * 
	 * @param fitness
	 * @return population
	 */
	public Parents generateOtherPopulation(List<TransferData> fitness) {
		List<TransferData> fitness_results = new ArrayList<>(fitness);

		List<TransferData> father = new ArrayList<>();
		List<TransferData> mother = new ArrayList<>();

		Set<Integer> random = new HashSet<>();
		Random rd = new Random();

		for (int i = 0; i < fitness_results.size() / 2; i++) {
			random.add(rd.nextInt(fitness_results.size() - 1));

			if (i == (fitness_results.size() / 2) - 1) {
				if (random.size() != fitness_results.size() / 2) {
					int temp = (fitness_results.size() / 2) - random.size();
					i -= temp;
				}
			}
		}
		List<Integer> list = new ArrayList<>(random);
		Collections.sort(list);

		for (int i = 0; i < random.size(); i++) {
			father.add(fitness_results.get(list.get(i)));
		}

		fitness_results.removeAll(father);
		mother.addAll(fitness_results);

		Parents parents = new Parents();
		parents.setFather(father);
		parents.setMother(mother);

		return parents;
	}

	public List<TransferData> generateOtherPopulation(Parents parents) {
		List<TransferData> initial = new ArrayList<>();

		List<TransferData> father = parents.getFather();
		List<TransferData> mother = parents.getMother();
		Random randomize = new Random();

		for (int m = 0; m < 2; m++) {
			for (int i = 0; i < father.size(); i++) {
				Integer[] array1 = new Integer[10];

				TransferData fath = father.get(randomize.nextInt(father.size() - 1));
				TransferData moth = mother.get(randomize.nextInt(mother.size() - 1));

				//////////////////////////////////////////////////////////////
				String fath_str = fath.getGeneratedLayers();
				fath_str = fath_str.substring(7, fath_str.length());
				String moth_str = moth.getGeneratedLayers();
				moth_str = moth_str.substring(7, moth_str.length());

				String[] fath_mas_str = fath_str.split(",");
				String[] moth_mas_str = moth_str.split(",");

				Integer[] fath_int = new Integer[10];
				Integer[] moth_int = new Integer[10];

				for (int j = 0; j < 10; j++) {
					fath_int[j] = Integer.valueOf(fath_mas_str[j]);
					moth_int[j] = Integer.valueOf(moth_mas_str[j]);
				}
				///////////////////////////////////////////////

				int point = randomize.nextInt(9);

				for (int j = 0; j < point; j++) {
					array1[j] = fath_int[j];
				}
				for (int j = point; j < 10; j++) {
					array1[j] = moth_int[j];
				}

				String layer = "";
				for (int j = 0; j < array1.length; j++) {
					layer += array1[j] + ",";
				}

				layer = layer.substring(0, layer.length() - 1);
				TransferData transferData = new TransferData();
				transferData.setGeneratedLayers(Constants.LAYER + layer);
				initial.add(transferData);
			}
		}

		return initial;
	}

	/**
	 * Searches fit individ inside population.
	 * 
	 * @param fitness_results
	 * @param numberOfBest
	 * @return
	 */
	public List<TransferData> findFitIndivid(List<TransferData> fitness_results, int numberOfBest) {
		List<TransferData> best = new ArrayList<>();

		List<TransferData> temp = Sorter.searchBest(fitness_results);
		for (int i = 0; i < numberOfBest; i++) {
			best.add(temp.get(i));
		}

		return best;
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
