package ua.khpi.diplom.socket;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ua.khpi.diplom.genetic_alghoritm.GeneticAlghotirm;
import ua.khpi.diplom.layer.Layers;
import ua.khpi.diplom.utils.serializable.Serializable;
import ua.khpi.model.TransferData;

public class Client {

	protected Socket client;
	protected BufferedReader in;
	private GeneticAlghotirm ga = null;

	@SuppressWarnings("unchecked")
	public Client(String hostName, int ip) throws ClassNotFoundException, InterruptedException {
		ga = new GeneticAlghotirm();

		// createFileOnClientSide();
		try {
			this.client = new Socket(hostName, ip);
			this.in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			String buffer = null;

			while ((buffer = in.readLine()) != null) {
				// List<String> clientList = new ArrayList<>((List<String>)
				// Serializable.deserialization(buffer));
				List<TransferData> transferData = (List<TransferData>) Serializable.deserialization(buffer);

				List<TransferData> clienResult = ga.calculateFitness(transferData, new Layers());
				// Thread.sleep(15000);
				// List<Double> clienResult = Arrays.asList(6., 7., 8., 9.,
				// 10.);
				System.err.println(clienResult);

				// Thread.sleep(30000);
				// List<Double> clienResult = Arrays.asList(23., 213., 123.);

				sendToServerData(clienResult);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set up mark for server side for end loop.
	 */
	private void sendToServerFlagOfTheEnd() {
		System.err.println("invoked serverflagmethod");
		ServerHandler serverHandler = new ServerHandler(client);
		serverHandler.out.println("exit");
		serverHandler.out.flush();
	}

	private void sendToServerData(List<TransferData> transferData) {
		System.out.println("check");
		ServerHandler serverHandler = new ServerHandler(client);
		serverHandler.out.println(Serializable.serialization(transferData));
		serverHandler.out.flush();
	}
}