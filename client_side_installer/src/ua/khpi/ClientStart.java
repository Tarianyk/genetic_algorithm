package ua.khpi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ua.khpi.diplom.socket.Client;
import ua.khpi.diplom.utils.constant.Constants;
import ua.khpi.diplom.utils.io.Find;

/**
 * The class used for starting client side.
 * 
 * @author Anton
 *
 */
public class ClientStart {

	/**
	 * Main point for entrance.
	 * 
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) throws ClassNotFoundException, InterruptedException, IOException {
		String[] commands = getInputFromConsole();

		String disk = commands[0];
		String versionAnsys = commands[1];
		Find.search(new String[] { disk, "-name", "ANSYS" + versionAnsys + ".exe" });
		Constants.setPATH_TO_ANSYS(Find.getFound().toString());
		new Client("192.168.0.8", 1200);
		// System.out.println(commands[2] + " " + Integer.valueOf(commands[3]));
		// new Client(commands[2].trim(), Integer.valueOf(commands[3]));
	}

	/**
	 * Gets input parameters from console.
	 * 
	 * @return parameters
	 */
	private static String[] getInputFromConsole() {
		BufferedReader br = null;

		String[] commands = new String[4];
		commands[0] = Constants.DISK;
		commands[1] = Constants.VERSION_OF_ANSYS;
		commands[2] = Constants.IP;
		commands[3] = Constants.PORT;

		String[] ret = new String[4];
		int counter = 0;
		try {

			br = new BufferedReader(new InputStreamReader(System.in));

			while (true) {

				System.out.print(commands[counter++]);
				String input = br.readLine();
				ret[counter - 1] = input;

				if (4 == counter) {
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
}
