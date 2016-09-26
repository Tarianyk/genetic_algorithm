package ua.khpi.diplom.utils.serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.List;

import ua.khpi.model.TransferData;

/**
 * Contains methods for serialization and deserialization objects.
 *  
 * @author Anton
 *
 */
public class Serializable {

	/**
	 * Used for serializing <tt>List</tt> object.
	 * 
	 * @param backet
	 *            input object that will be serialization
	 * @return serialized object
	 */
	public static String serializationList(List<String> population) {
		String str = "";
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream so = new ObjectOutputStream(bo);
			so.writeObject(population);
			so.close();
			str = new String(Base64.getEncoder().encodeToString(bo.toByteArray()));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return str;
	}

	/**
	 * Used for serializing <tt>List</tt> object.
	 * 
	 * @param backet
	 *            input object that will be serialization
	 * @return serialized object
	 */
	public static String serialization(List<TransferData> transferData) {
		String str = "";
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream so = new ObjectOutputStream(bo);
			so.writeObject(transferData);
			so.close();
			str = new String(Base64.getEncoder().encodeToString(bo.toByteArray()));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return str;
	}

	/**
	 * Read the object from Base64 string.
	 * 
	 * @param s
	 *            non-serializable object
	 * @return deserialized object
	 * @throws IOException
	 *             input/output problem
	 * @throws ClassNotFoundException
	 *             class does not exists
	 */
	public static Object deserialization(String s) throws IOException, ClassNotFoundException {
		byte[] data = Base64.getDecoder().decode(s);
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
		Object o = ois.readObject();
		ois.close();
		return o;
	}

}
