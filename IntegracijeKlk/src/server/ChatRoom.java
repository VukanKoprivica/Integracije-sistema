package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.List;

import util.ChatListener;
import util.RObservable;
import xml.XMLDOM;

public class ChatRoom extends UnicastRemoteObject implements ChatServer {

	private RObservable remoteObservable;
	private XMLDOM cenzura; 
	
	
	

	public ChatRoom() throws RemoteException {
		super();
		this.remoteObservable = new RObservable();
		this.cenzura=new XMLDOM();
		
	}
	
	

	@Override
	public synchronized void sendMessage(String name, String message) throws RemoteException {
		
		if(message.startsWith("/+/")) {
			message = message.substring(3);
			this.cenzura.addRec(name, message);
			findListener(name, "rec:" + message+" uspesno dodata");
		}else if(message.startsWith("/-/")) {
			message = message.substring(3);
			boolean obrisana=this.cenzura.deleteRec( message);
			if(obrisana) {
				findListener(name, "rec:"+message +" uspesno obrisana");
			}else {
				findListener(name,"rec:"+message+ " ne postoji");
			}
		}else {
			
			List<String> zabranjene = cenzura.getListu();
			String[] tokens = message.split(" ");
			StringBuilder sb = new StringBuilder();
			for(String s:tokens) {
				if(zabranjene.contains(s.toLowerCase())) {
					s="*****";
				}
				
				sb.append(s +" ");
			}
			
			message = sb.toString();
			this.remoteObservable.notifyRemoteObservers(name, message);
			System.out.println(name +":" +message);
		}
	}

	private void findListener(String name,String message) throws RemoteException {
		this.remoteObservable.find(name, message);
	}
	@Override
	public void addListener(ChatListener listener) throws RemoteException {
		
		this.remoteObservable.addRemoteObservers(listener);
		
	}

	@Override
	public void removeListener(ChatListener listener) throws RemoteException {
		this.remoteObservable.deleteRemoteObservers(listener);
		
	}
	
	
	
	
	
	public static void main(String[] args) throws RemoteException {
		
		/*
		 * if(System.getSecurityManager()==null) { System.setSecurityManager(new
		 * RMISecurityManager()); }
		 */
		LocateRegistry.createRegistry(1099);
		String name ="//127.0.0.1:1099/Chat";
		System.out.println("Server : " +name);
		try {
			ChatServer chatRoom = new ChatRoom();
			Naming.rebind(name, chatRoom);
		} catch (RemoteException e) {
			// TODO: handle exception
			System.out.println("Servis :" +e.getMessage());
			e.printStackTrace();
		}catch (MalformedURLException e) {
			// TODO: handle exception
			System.out.println("Url: " +e.getMessage());
			e.printStackTrace();
		}
		
	}

}
