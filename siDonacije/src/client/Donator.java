package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import server.IC;

public class Donator implements ActionListener {
	private JFrame frame;

	private JButton exitButton;

	private JButton donateButton;

	private JTextField donationTextField;

	private JTextField totalTextField;

	private JLabel messageLabel;

	private IC donationsCollector;

	private RDonator remoteDonor;
	
	public Donator() {
		
		this.frame = new JFrame("Donor: RMI Client");
		this.frame.setSize(400, 125);
		this.frame.setResizable(false);
		JPanel panelDonativos = new JPanel();
		JPanel panelBotones = new JPanel();
		this.donateButton = new JButton("Donate");
		this.donateButton.addActionListener(this);
		this.exitButton = new JButton("Exit");
		this.exitButton.addActionListener(this);
		this.donationTextField = new JTextField(10);
		this.totalTextField = new JTextField(10);
		this.totalTextField.setEditable(false);
		this.messageLabel = new JLabel("Wellcome to RMI donations client.");
		this.messageLabel.setOpaque(true);
		this.messageLabel.setForeground(Color.yellow);
		this.messageLabel.setBackground(Color.LIGHT_GRAY);

		panelDonativos.add(new JLabel("Donation: "));
		panelDonativos.add(this.donationTextField);
		panelDonativos.add(new JLabel("Donated total: "));
		panelDonativos.add(this.totalTextField);

		panelBotones.add(this.donateButton);
		panelBotones.add(this.exitButton);

		this.frame.getContentPane().add(panelDonativos, "North");
		this.frame.getContentPane().add(panelBotones);
		this.frame.getContentPane().add(this.messageLabel, "South");

		this.frame.setVisible(true);
		
		
	}

	

	private void start(String[] args) {
		// TODO Auto-generated method stub
		
		this.conectToConector(args);
		try {
			this.remoteDonor=new RDonator(this.donationsCollector, this);
			
		} catch (RemoteException re) {
				System.err.println("Donro e; "
						+ re.getMessage());
				re.printStackTrace();
			// TODO: handle exception
		}
		
	}
	private void conectToConector(String[] args) {
		if(System.getSecurityManager()==null)
				System.setSecurityManager(new SecurityManager());
		
		try {
			String URL="//"+args[0]+":"+args[1]+"/"+args[2];
			this.donationsCollector=(IC) Naming.lookup(URL);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Error while locating Collector: "
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	public void notifyColected(Integer arg) {
		this.messageLabel.setText("Receiving accumulated amount...");
		this.totalTextField.setText(arg.toString());
		this.messageLabel.setText("Total collected amount retrieved.");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton target = (JButton) e.getSource();
		if (target == this.exitButton) {
			try {
				this.remoteDonor.terminate();
				System.exit(0);
			} catch (Exception exc) {
				System.err.println("An error happened while un-registering the Donor: "
						+ exc.getMessage());
				System.exit(-1);
			}
		}

		if (target == this.donateButton) {
			try {
				int don = Integer.parseInt(this.donationTextField.getText());
				this.messageLabel.setText("Sending donation...");
				this.donationsCollector.collectDonation(don);
				this.messageLabel.setText("A " + don + " € donation sent.");
			} catch (Exception exc) {
				this.messageLabel.setText("An error happened during donations collection process.");
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(System.getSecurityManager()==null) {
			System.setSecurityManager(new SecurityManager());
		}
		
		try {
			Donator donator = new Donator();
			donator.start(args);
			
			
		}catch (Exception e) {
			// TODO: handle exception
			System.err.println("Donor e: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
