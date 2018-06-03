package client;

import java.io.IOException;
import java.net.*;
import java.util.Vector;

import server.ServerConnListener;

public class Client {
	String serverHostname = "127.0.0.1";
	ClientApp clientApp;
	Vector<ClientConnListener> handles;

	public Client(ClientApp cp) {
		clientApp = cp;
		handles = new Vector<ClientConnListener>();
		connectToServer();
	}
	
    public void connectToServer() {
        try {
            Socket socket = new Socket(serverHostname, 1111);
            addServer(socket);
        } catch (Exception ex) {        	
            ex.printStackTrace();
        }
    }
    
    public void addServer(Socket socket){
    	handles.addElement(new ClientConnListener(this, socket));    	
    }
    
    public void removeServer(ClientConnListener server) {
    	try {
			server.socket.close();
			clientApp.removeServerFromDisplay(Integer.toString(server.port));
			handles.remove(server);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void broadcast(String msg) {
        
        if(handles.size() == 0)
            System.out.println("No server available");
        
        else for (int i = 0; i<handles.size(); i++) {
        	ClientConnListener cc = (ClientConnListener) handles.elementAt(i);
        	cc.sendMessage(msg);
        }
    }
}
