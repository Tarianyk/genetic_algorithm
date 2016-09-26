package ua.khpi.diplom.utils.constant;

/**
 * The class used for containing constants.
 * 
 * @author Anton
 *
 */
public final class Constants {

	private Constants() {
		throw new UnsupportedOperationException("Non instance Constants.");
	}

	public static final String LINE_SEPARATOR = System.lineSeparator();
	public static final String LAYER = "val(1)=";
	public static final String DISK = "Select the drive in which will be searched (format must be: C:\\, D:\\ and etc.): \n";
	public static final String VERSION_OF_ANSYS = "Enter version of Ansys (format must be: 140, 145 and etc.): \n";
	public static final String MARK_LAYER = "*dim,val,array,10,1,1,,,";
	public static final String PATH_TO_FILE = "ansys\\macro_work.txt";
	public static final String PATH_TO_RESULT = "ansys\\temp_folder\\NAPES3.txt";
	public static final String PATH_TO_FOLDER = "ansys\\temp_folder\\";
	public static final String ENCODING_UTF8 = "UTF-8";
	public static final String IP = "Enter ip-address to which will be connected:";
	public static final String PORT = "Enter port number:";
	public static String PATH_TO_ANSYS = "";
//	public static final String PATH_WITH_PARAMETERS = "C:\\Program Files\\ANSYS Inc\\v145\\ANSYS\\bin\\winx64\\ansys145.exe" + "\" " + "-p struct -np 4 -m 5000 -dir \"c:\\Java\\workspace\\repo_git\\client_side_installer\\ansys\\temp_folder\" -j \"ansys_temp\" -s read -l en-us -b -i \"c:\\Java\\workspace\\repo_git\\client_side_installer\\ansys\\macro_work.txt\" -o \"c:\\Java\\workspace\\repo_git\\client_side_installer\\ansys\\temp_folder\\diplom.dat";
	public static String PATH_WITH_PARAMETERS = "\" " + "-p struct -np 4 -m 5000 -dir \"c:\\Java\\workspace\\repo_git\\client_side_installer\\ansys\\temp_folder\" -j \"ansys_temp\" -s read -l en-us -b -i \"c:\\Java\\workspace\\repo_git\\client_side_installer\\ansys\\macro_work.txt\" -o \"c:\\Java\\workspace\\repo_git\\client_side_installer\\ansys\\temp_folder\\diplom.dat";
	public static void setPATH_TO_ANSYS(String pATH_TO_ANSYS) {
		PATH_TO_ANSYS = pATH_TO_ANSYS;
	}
	
	
	
	

}
