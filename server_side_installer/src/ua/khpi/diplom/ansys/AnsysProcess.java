package ua.khpi.diplom.ansys;

import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * The class used for creating process of ansys.
 * 
 * @author Anton
 *
 */
public class AnsysProcess {

	/**
	 * Logger.
	 */
	private final static Logger LOG = Logger.getLogger(AnsysProcess.class);

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
			LOG.log(Level.TRACE, "Exception has occured in createProcess method.", e);
		}

		LOG.log(Level.DEBUG, "Method finished with code " + exitVal);
	}

}
