package ua.khpi.diplom.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import ua.khpi.StartUpServer;
import ua.khpi.diplom.utils.serializable.Serializable;
import ua.khpi.model.TransferData;

public class SendMessageFromServer {

	protected static List<List<String>> userInput;
	protected List<ClientHandler> clients;
	protected List<String> list;
	public List<Double> TEST = new ArrayList<>();

	protected Socket client;
	protected BufferedReader console;
	protected int count;
	private static boolean flag = true;
	private boolean loop = true;
	public static boolean CHECK = true;

	public SendMessageFromServer(List<ClientHandler> clients, Socket client, int count) {
		this.count = count;
		this.client = client;
		this.clients = clients;
	}

	@SuppressWarnings("unchecked")
	public void run() {
		try {
			if (clients.size() > 0) {
				this.console = new BufferedReader(new InputStreamReader(System.in));
				while (flag) {
					loop = true;
					while (null == userInput) {
						Thread.sleep(100); // was 500
					}

					for (int i = 0; i < clients.size(); i++) {
						List<TransferData> transferData = setTransfer(userInput.get(i));
						clients.get(i).out.println(Serializable.serialization(transferData));
						clients.get(i).out.flush();
					}
					userInput = null;

					while (loop) {
						for (int i = 0; i < clients.size(); i++) {
							BufferedReader in = new BufferedReader(
									new InputStreamReader(clients.get(i).client.getInputStream()));

							while (true) {
								String list = null;
								if ((list = in.readLine()) != null) {
									StartUpServer.FITNESS_RESULTS
											.addAll(((List<TransferData>) Serializable.deserialization(list)));
									break;
								}
							}
						}

						StartUpServer.WAIT_CLIENTS = false;

						while (CHECK) {
							synchronized (StartUpServer.MONITOR) {
								StartUpServer.FINISH = false;
							}
						}
						CHECK = true; // HERE
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private List<TransferData> setTransfer(List<String> serverList) {
		List<TransferData> list = new ArrayList<>();

		for (int i = 0; i < serverList.size(); i++) {
			TransferData transferData = new TransferData();
			transferData.setGeneratedLayers(serverList.get(i));
			list.add(transferData);
		}

		return list;
	}

	public static List<List<String>> getUserInput() {
		return userInput;
	}

	public static void setUserInput(List<List<String>> userInput) {
		SendMessageFromServer.userInput = userInput;
	}

	public static boolean isFlag() {
		return flag;
	}

	public static void setFlag(boolean flag) {
		SendMessageFromServer.flag = flag;
	}
}
