package server;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.IRObserver;
import util.RObservable;

public class Collector extends UnicastRemoteObject implements IC,ActionListener{

	private RObservable remoteObservable;

	private int donations = 0;

	private JFrame frame;

	private JButton terminateButton;

	private JTextField donationTextField;

	private JTextField totalTextField;

	private JLabel messageLabel;
	
	protected Collector() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		
		this.frame = new JFrame("Donacija : RMI SERVER");
		this.frame.setSize(500,150);
		this.frame.setResizable(false);
		
		JPanel panelSuperior = new JPanel();
		JPanel panelBoton = new JPanel();
		this.terminateButton = new JButton("Terminate Collection");
		this.terminateButton.addActionListener(this);
		this.donationTextField = new JTextField(10);
		this.donationTextField.setText("0");
		this.donationTextField.setEnabled(false);
		this.totalTextField = new JTextField(10);
		this.totalTextField.setText("0");
		this.totalTextField.setEnabled(false);

		this.messageLabel = new JLabel(
				"Donations Collecting RMI Server activated...");
		this.messageLabel.setOpaque(true);
		this.messageLabel.setForeground(Color.yellow);
		this.messageLabel.setBackground(Color.LIGHT_GRAY);

		panelSuperior.add(new JLabel("Last donation: "));
		panelSuperior.add(this.donationTextField);
		panelSuperior.add(new JLabel("Total collected: "));
		panelSuperior.add(this.totalTextField);

		panelBoton.add(this.terminateButton);

		this.frame.getContentPane().add(panelSuperior, "North");
		this.frame.getContentPane().add(panelBoton);
		this.frame.getContentPane().add(this.messageLabel, "South");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton target= (JButton) e.getSource();
		if(target == this.terminateButton)
				System.exit(0);
	}

	@Override
	public synchronized void collectDonation(int d)  {
		// TODO Auto-generated method stub
		this.donations  =this.donations+d;
		this.donationTextField.setText(Integer.toString(d));
		this.totalTextField.setText(Integer.toString(this.donations));
		this.notifyNew(this.donations);
	}
	
	private void notifyNew(int don) {
		this.remoteObservable.notifyRemoteObservers(new Integer(don));
		
	}
	
	@Override
	public void addRObserver(IRObserver ro) throws RemoteException {
		// TODO Auto-generated method stub
		this.remoteObservable.addRemoteObserver(ro);
		try {
			ro.update(new Integer(this.donations));
			
		} catch (RemoteException e) {
			// TODO: handle exception
			System.err.println("An error happened during notify:: "
					+ e.getMessage());
		}
	}



	@Override
	public void deleteRObserver(IRObserver ro)  {
		// TODO Auto-generated method stub

		this.remoteObservable.deleteRemoteObserver(ro);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if(System.getSecurityManager()==null) {
			System.setSecurityManager(new SecurityManager());
		}
		
		String name = "//"+args[0] + ":"+args[1]+"/"+args[2];
		System.out.println("Server : "+name);
		try {
			IC colector = new Collector();
			Naming.rebind(name, colector);
		} catch (RemoteException re) {
			// TODO: handle exception
			System.err.println("Collector RemoteException: " + re.getMessage());
			re.printStackTrace();
			System.exit(-1);
			
		}catch(MalformedURLException me){
			System.err.println("URL e : " +me.getMessage());
			me.printStackTrace();
			System.exit(-1);
		}
	}

}
