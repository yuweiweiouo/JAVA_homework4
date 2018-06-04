package server;

import java.io.*;
import java.net.Socket;

public class ServerConnListener extends Thread{
	Server PARENT;
	public Socket socket;
	public DataInputStream in;
	public DataOutputStream out;
	
	String username;
	
	public ServerConnListener(Server p, Socket socket) {
        PARENT 	= p;
        this.socket = socket;
        try 
        {            
            in = new DataInputStream(socket.getInputStream());
            out= new DataOutputStream(socket.getOutputStream());
            
            start();
        } catch (Exception ex) {
            System.out.println("Error with socket creation!");
        }
    }
	
	/*
	 * this block will keep listening messages sent from client
	 */
    public void run() {
        try {
        	//get username and update userlist
            username = in.readUTF();
            PARENT.serverApp.addToMessageBox(username + " 已加入");            
            PARENT.serverApp.addUsertoDisplay(username);
            
            while(true) {
                String msg = in.readUTF();           
                PARENT.serverApp.addToMessageBox(username + ": " + msg);
            }
        } catch(Exception e) {
            // This may happen because of the fact that client
            // application is closed.
            System.out.println("Error reading message!");
        }
        PARENT.serverApp.addToMessageBox(username + " 已離開");
        System.out.println("Socket closed!");
        PARENT.removeUser(this);
    }
	
	public void sendMessage(String msg) {
        try {
        	System.out.println("Send to clients: " + msg);
        	out.writeUTF(msg);
        } catch (Exception ex) {
            System.out.println("Error sending message");
        }
    }
}
