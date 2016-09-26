package ua.khpi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ua.khpi.diplom.genetic_alghoritm.GeneticAlghotirm;
import ua.khpi.diplom.layer.Layers;
import ua.khpi.diplom.socket.SendMessageFromServer;
import ua.khpi.diplom.socket.Server;
import ua.khpi.diplom.utils.constant.Constants;
import ua.khpi.diplom.utils.io.Find;
import ua.khpi.diplom.utils.socket.SocketUtils;
import ua.khpi.model.Parents;
import ua.khpi.model.TransferData;

/**
 * The class used for starting server and start up GA.
 * 
 * @author Anton
 *
 */
public class StartUpServer {

	/**
	 * Monitor for threads.
	 */
	public static String MONITOR = new String("MONITOR");

	/**
	 * Boolean variables for controlling another clusters.
	 */
	public static boolean START = false, FINISH = true, WAIT_CLIENTS = true;

	/**
	 * Contains results of calculate.
	 */
	public static List<TransferData> FITNESS_RESULTS = new ArrayList<>();

	/**
	 * Main point for entrance.
	 * 
	 * @param args
	 * @throws InterruptedException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InterruptedException, ClassNotFoundException, IOException {

		String[] commands = getInputFromConsole();
		String disk = commands[0];
		String versionAnsys = commands[1];
		Find.search(new String[] { disk, "-name", "ANSYS" + versionAnsys + ".exe" });
		Constants.setPATH_TO_ANSYS(Find.getFound().toString());
		int numberOfClients = Integer.valueOf(commands[2]);
		int numberOfIteration = Integer.valueOf(commands[3]);
		int numberOfInitialPopulation = Integer.valueOf(commands[4]);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		synchronized (MONITOR) {
			// second param need add from console
			new Server(1200, numberOfClients);

			MONITOR.wait();
		}
		START = true;

		Layers layers = new Layers();
		GeneticAlghotirm ga = new GeneticAlghotirm();

		List<Integer[]> initialPopulation = null;
		for (int i = 0; i < numberOfIteration; i++) {

			if (i == 0) {
				// initialized the initial population
				initialPopulation = ga.initialPopulation(numberOfInitialPopulation, 10);
			}

			// generates layers
			List<String> generatedLayers = layers.generateLayer(initialPopulation);

			// splits list on parts
			List<List<String>> endList = new ArrayList<>(
					SocketUtils.splitList(generatedLayers, numberOfClients + 1));

			List<String> serverList = new ArrayList<>(endList.get(0));
			List<TransferData> transferData = convertToTransferData(serverList);

			endList.remove(0);

			List<List<String>> newList = newList(endList); //////// start here

			SendMessageFromServer.setUserInput(newList);

			List<TransferData> serverResult = ga.calculateFitness(transferData, layers);

			while (FINISH) {
				Thread.sleep(100);
			}
			;
			FINISH = true;
			SendMessageFromServer.CHECK = false;

			while (WAIT_CLIENTS) {
				Thread.sleep(100);
			}
			WAIT_CLIENTS = true;

			FITNESS_RESULTS.addAll(serverResult);

			initialPopulation = new ArrayList<>();
			List<TransferData> bestIndivid = ga.findFitIndivid(FITNESS_RESULTS, 2);

			FITNESS_RESULTS = FITNESS_RESULTS.subList(2, FITNESS_RESULTS.size());

			Parents parents = ga.generateOtherPopulation(FITNESS_RESULTS);

			FITNESS_RESULTS = new ArrayList<>();

			List<TransferData> otherPop = ga.generateOtherPopulation(parents);

			for (int j = 0; j < bestIndivid.size(); j++) {
				initialPopulation.add(layers.parse(bestIndivid.get(j).getGeneratedLayers()));
			}

			for (int j = 0; j < otherPop.size(); j++) {
				initialPopulation.add(layers.parse(otherPop.get(j).getGeneratedLayers()));
			}

			System.out.println("--->" + initialPopulation);

		}
	}

	/**
	 * Converts list data to bean class.
	 * 
	 * @param serverList
	 * @return
	 */
	private static List<TransferData> convertToTransferData(List<String> serverList) {
		List<TransferData> list = new ArrayList<>();

		for (int i = 0; i < serverList.size(); i++) {
			TransferData transferData = new TransferData();
			transferData.setGeneratedLayers(serverList.get(i));
			list.add(transferData);
		}

		return list;
	}

	/**
	 * Gets input parameters from console.
	 * 
	 * @return parameters
	 */
	private static String[] getInputFromConsole() {
		BufferedReader br = null;

		String[] commands = new String[5];
		commands[0] = Constants.DISK;
		commands[1] = Constants.VERSION_OF_ANSYS;
		commands[2] = Constants.NUMBER_OF_CLIENTS;
		commands[3] = Constants.NUMBER_OF_ITERATIONS;
		commands[4] = Constants.SIZE_OF_INITIAL_POPULATION;

		String[] ret = new String[5];
		int counter = 0;
		try {

			br = new BufferedReader(new InputStreamReader(System.in));

			while (true) {

				System.out.print(commands[counter++]);
				String input = br.readLine();
				ret[counter - 1] = input;

				if (5 == counter) {
					break;
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return ret;
	}

	/**
	 * Converts old list to new for serialize.
	 * 
	 * @param oldList
	 * @return new list
	 */
	private static List<List<String>> newList(List<List<String>> oldList) {
		List<List<String>> newList = new ArrayList<>();

		for (int j = 0; j < oldList.size(); j++) {
			newList.add(new ArrayList<>(oldList.get(j)));
		}

		return newList;
	}

}
