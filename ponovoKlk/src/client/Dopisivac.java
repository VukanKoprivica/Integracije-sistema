package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import util.ChatListener;
import util.ChatServer;

public class Dopisivac extends UnicastRemoteObject implements ChatListener {

	private String name;
	private ChatServer server;
	
	protected Dopisivac(String name) throws RemoteException {
		super();
		this.name=name;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void receiveMessage(String name, String message) throws RemoteException {
		System.out.println(name +":"+message);
		
	}
	
	public void start() {
		
		try {
			this.server = (ChatServer) Naming.lookup("//localhost:1099/chat");
			this.server.addListener(this);
			send();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void send() throws IOException {
		String message="";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			
			message =br.readLine();
			if(message.equals(""))break;
			this.server.sendMessage(this.name, message);
			
		}
		br.close();
		this.server.removeListener(this);
		System.out.println("Cao");
		this.server.sendMessage(this.name," napustio cet");
		System.exit(0);
		
	}
	
	public static void main(String args[]) {
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Unesi nadimak da bi se registrovao:");
			String ime = br.readLine();
			Dopisivac dopisivac = new Dopisivac(ime);
			dopisivac.start();
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

	@Override
	public boolean getByName(String name) throws RemoteException {
		
		if(this.name.equalsIgnoreCase(name))return true;
		return false;
	}

}
