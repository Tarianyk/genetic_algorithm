package ua.khpi.diplom.ansys;

import java.io.IOException;

/**
 * The class used for creating process of ansys.
 * 
 * @author Anton
 *
 */
public class AnsysProcess {

	/**
	 * Creates process.
	 * 
	 * @param path_with_parameters
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void createProcess(String path_with_parameters) throws IOException, InterruptedException {
		ProcessBuilder procBuilder = new ProcessBuilder(path_with_parameters);

		procBuilder.redirectErrorStream(true);

		Process process = procBuilder.start();

		int exitVal = 0;
		try {
			exitVal = process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(exitVal);
	}

}
