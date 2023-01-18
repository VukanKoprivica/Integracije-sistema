package server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import util.ChatListener;
import util.ChatServer;
import util.RemoteObservable;
import xml.DOMPARSER;

public class Server extends UnicastRemoteObject implements ChatServer{
	
	private RemoteObservable remoteObservable;
	private DOMPARSER xml;
	
	protected Server() throws RemoteException {
		super();
		this.remoteObservable=new RemoteObservable();
		this.xml=new DOMPARSER();
	}

	@Override
	public void sendMessage(String name, String message) throws RemoteException {
		// TODO Auto-generated method stub
		if(message.startsWith("/+/")) {
			message = message.substring(3);
			if(this.xml.addRec(name, message)) {
			findListener(name,"Rec:"+message+" uspesno dodata");
			}else {
				findListener(name,"Rec:"+message+" vec postoji");
			}
		}else if(message.startsWith("/-/")) {
			message=message.substring(3);
			if(this.xml.deleteRec(message)) {
				findListener(name,"Rec:"+message+" uspesno obrisana");
			}else {findListener(name,"Rec:"+message+" ne postoji");}
		}else {
		
			String[] token  =message.split(" ");
			StringBuilder sb = new StringBuilder();
			for(String s:token) {
				if(this.xml.getPsovke().contains(s)) {
					s="*****";
				}
				sb.append(s+" ");
				
			}
		message  =sb.toString();
		this.remoteObservable.notifyObservers(name, message);
		System.out.println(name+":"+message);
		}
	}

	@Override
	public void addListener(ChatListener listener) throws RemoteException {
		this.remoteObservable.addListener(listener);
		
	}
	@Override
	public void findListener(String name,String message) throws RemoteException{
		this.remoteObservable.find(name,message);
	}

	@Override
	public void removeListener(ChatListener listener) throws RemoteException {
		// TODO Auto-generated method stub
		this.remoteObservable.removeListener(listener);
		
	}
	
	public static void main(String args[]) {
		try {
			LocateRegistry.createRegistry(1099);
			String url = "//localhost:1099/chat";
			Server server = new Server();
			System.out.println("Server : " +url);
			Naming.rebind(url, server);
		} catch (Exception e) {
			// TODO: handle exception\
			e.printStackTrace();
		}
		
		
		
	}

}
