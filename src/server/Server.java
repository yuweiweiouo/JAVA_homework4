package server;
import java.net.*;
import java.util.Vector;
import java.io.*; 

public class Server extends Thread {
	private ServerSocket servers;
	int port = 1111; // default port 
	ServerApp serverApp;
    Vector<ServerConnListener> handles;
	
	public Server(ServerApp sp){
		boolean isServerStart = false;
        while(!isServerStart){
        	try {
        		serverApp= sp;
                servers = new ServerSocket(port);
                handles = new Vector <ServerConnListener>();
                
                start();
                isServerStart = true;
            } catch (BindException be) {
                if(port<10000){
                	port++;
                	serverApp.setPortDisplayed(port);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
	}
	
	public void run() {
        try {
            while(true) {
                System.out.println("Waiting for user ...");
                Socket socket = servers.accept();
                System.out.println("one user in!");
                addUser(socket);
                System.out.println("keep waiting...");
            }
        } catch (Exception ex) {}
    }
	
    public void addUser(Socket socket) { 
        handles.addElement(new ServerConnListener(this, socket));        
    }
    
    public void removeUser(ServerConnListener client) {
    	try {
			client.socket.close();
			serverApp.removeUserFromDisplay(client.username);
			handles.remove(client);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void broadcast(String msg) {
        System.out.println("BCAST #"+handles.size());
        
        if(handles.size() == 0)
            System.out.println("No user in room");
        
        else for (int i = 0; i<handles.size(); i++) {
        	ServerConnListener sc = (ServerConnListener) handles.elementAt(i);
        	sc.sendMessage(msg);
        }
    }
    
}
