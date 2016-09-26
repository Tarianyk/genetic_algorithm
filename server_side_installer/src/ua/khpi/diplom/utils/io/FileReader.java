package ua.khpi.diplom.utils.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ua.khpi.diplom.utils.constant.Constants;

/**
 * The class used for processing with different files.
 * 
 * @author Anton
 *
 */
public class FileReader {

	/**
	 * Read from file.
	 * 
	 * @return data
	 */
	public static List<String> getInput(String path) {
		List<String> text = new ArrayList<>();
		try {
			Scanner scanner = new Scanner(new File(path), Constants.ENCODING_UTF8);
			while (scanner.hasNextLine()) {
				text.add(scanner.nextLine() + System.lineSeparator());
			}
			return text;
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
		return text;
	}

	/**
	 * Writes data to file.
	 * 
	 * @param text
	 * @param pathToFile
	 */
	public static void writeToFile(List<String> text, String pathToFile) {
		try {
			OutputStream f = new FileOutputStream(pathToFile, false);
			OutputStreamWriter writer = new OutputStreamWriter(f, Constants.ENCODING_UTF8);
			BufferedWriter out = new BufferedWriter(writer);
			for (int i = 0; i < text.size(); i++) {
				out.write(text.get(i));
				out.flush();
			}
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}

	/**
	 * Reads results from file.
	 * 
	 * @return
	 * @throws IOException
	 */
	public static Double getResult() throws IOException {
		List<String> text = getInput(Constants.PATH_TO_RESULT);
		return Double.parseDouble(text.get(0)) / 100000;
	}

	public static void deleteDirectory(String path) {
		for (File file : new File(path).listFiles())
			if (file.isFile())
				file.delete();
	}
}
