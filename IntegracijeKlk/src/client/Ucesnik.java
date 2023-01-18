package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.Scanner;

import server.ChatServer;

public class Ucesnik  {
	
	private ChatServer chatRoom ;
	private String name;
	private RUcesnik ucesnik;
	
	

	public Ucesnik(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	public void start() {
		/*
		 * if(System.getSecurityManager()==null) { System.setSecurityManager(new
		 * RMISecurityManager()); }
		 */
		try {
			this.chatRoom = (ChatServer) Naming.lookup("//127.0.0.1:1099/Chat");
			this.ucesnik=new RUcesnik(chatRoom, this);
			
			send();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
	
	public void primljena(String name,String message) {
		System.out.println(name +":" +message);
	}
	
	public void send() throws IOException {
		String message = null;
		//Scanner in = new Scanner(System.in);
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
				message = in.readLine();
				if(message.equals(""))break;
				this.chatRoom.sendMessage(name, message);
		}
		
		exit();
		
	}

	private void exit() throws RemoteException {
		this.chatRoom.removeListener(this.ucesnik);
		System.out.println("Goodbye!");
		System.exit(0);
	}
	

	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		/*
		 * if(System.getSecurityManager()==null) { System.setSecurityManager(new
		 * RMISecurityManager()); }
		 */
		
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Unesi nadimak");
			String nadimak = in.readLine();
			Ucesnik ucesnik = new Ucesnik(nadimak);
			
			ucesnik.start();
			
			in.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

}
