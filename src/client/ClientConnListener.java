package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import server.Server;

public class ClientConnListener extends Thread {
	Client PARENT;
	public Socket socket;
	public DataInputStream in;
	public DataOutputStream out;
	public int port;

	public ClientConnListener(Client p, Socket s) {
		PARENT = p;
		this.socket = s;
		try {
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());

			start();
		} catch (Exception ex) {
			System.out.println("Error with socket creation!");
		}
	}

	/*
	 * this block will keep listening messages sent from server
	 */
	public void run() {
		try {
			port = socket.getPort();
			PARENT.clientApp.addServertoDisplay(Integer.toString(port));
			System.out.println("Your name: " + PARENT.clientApp.getUsername());
			sendMessage(PARENT.clientApp.getUsername());

			while (true) {
				String msg = in.readUTF();
				PARENT.clientApp.addToMessageBox(port + ": " + msg);
			}
		} catch (Exception e) {
			// This may happen because of the fact that client
			// application is closed.
			System.out.println(e.getMessage());
		}
		PARENT.clientApp.addToMessageBox("\"" + port + "\" 已關閉");
		System.out.println("Socket closed!");
		PARENT.removeServer(this);
	}

	public void sendMessage(String msg) {
		try {
			System.out.println("Send to servers: " + msg);
			out.writeUTF(msg);
		} catch (Exception ex) {
			System.out.println("Error sending message");
		}
	}
}
