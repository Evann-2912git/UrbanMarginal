package Vue;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controleur.Controle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EntreeJeu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel FenetreEntreeJeu;
	private Controle controle;
	private JTextField txtAdresseIP;
	
	private void btnStart_Click() {
		controle.evenementEntreeJeu("serveur");
	}

	private void btnConnect_Click() {
		controle.evenementEntreeJeu(txtAdresseIP.getText());
	}

	private void btnExit_Click() {
		System.exit(0);
	}
	
	/**
	 * Create the frame.
	 */
	public EntreeJeu(Controle controle) {
		setTitle("Urban Marginal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		FenetreEntreeJeu = new JPanel();
		FenetreEntreeJeu.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(FenetreEntreeJeu);
		FenetreEntreeJeu.setLayout(null);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStart_Click();
			}
		});
		
		btnStart.setBounds(292, 28, 89, 23);
		FenetreEntreeJeu.add(btnStart);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnConnect_Click();
			}
		});
		btnConnect.setBounds(292, 66, 89, 23);
		FenetreEntreeJeu.add(btnConnect);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnExit_Click();
			}
		});
		btnExit.setBounds(292, 100, 89, 23);
		FenetreEntreeJeu.add(btnExit);
		
		JLabel lbl1 = new JLabel("Start a server :");
		lbl1.setBounds(60, 32, 98, 14);
		FenetreEntreeJeu.add(lbl1);
		
		JLabel lbl2 = new JLabel("Connecting an existing server :");
		lbl2.setBounds(60, 70, 185, 14);
		FenetreEntreeJeu.add(lbl2);
		
		JLabel lblAdresseIP = new JLabel("IP server :");
		lblAdresseIP.setBounds(60, 104, 58, 14);
		FenetreEntreeJeu.add(lblAdresseIP);
		
		txtAdresseIP = new JTextField();
		txtAdresseIP.setText("127.0.0.1");
		txtAdresseIP.setBounds(119, 101, 86, 20);
		FenetreEntreeJeu.add(txtAdresseIP);
		txtAdresseIP.setColumns(10);
		
		this.controle=controle;
	}
}
