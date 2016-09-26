package ua.khpi.diplom.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import ua.khpi.StartUpServer;
import ua.khpi.diplom.ansys.AnsysProcess;

public class Server extends Thread {

	/**
	 * Logger.
	 */
	private final static Logger LOG = Logger.getLogger(Server.class);

	public static int counterClient = 0;
	private ServerSocket server;
	private int count = 0;

	protected List<ClientHandler> clients;

	private SendMessageFromServer messageFromServer = null;

	public Server(int port, int count) {
		this.count = count;

		try {
			this.server = new ServerSocket(port);
			System.out.println("New server initialized! Port: " + port);

			clients = Collections.synchronizedList(new ArrayList<ClientHandler>());

			this.start();
		} catch (Exception e) {
			LOG.log(Level.TRACE, "Exception has occured in constructor.", e);
		}
	}

	public synchronized void run() {

		while (true) {
			try {
				System.err.println("Waiting for client...");
				Socket client = server.accept();
				System.err.println("Client is logged.");

				ClientHandler newClient = new ClientHandler(client);
				clients.add(newClient);

				synchronized (StartUpServer.MONITOR) {
					if (clients.size() == count) {
						StartUpServer.MONITOR.notifyAll();
					}
				}

				Thread.sleep(1000);
				if (StartUpServer.START == true) {
					messageFromServer = new SendMessageFromServer(clients, client, count);
					messageFromServer.run();
				}

			} catch (Exception e) {
				LOG.log(Level.TRACE, "Exception has occured in run method.", e);
			}
		}
	}

}