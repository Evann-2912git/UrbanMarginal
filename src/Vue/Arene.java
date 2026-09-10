package Vue;

import java.awt.Dimension;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Controleur.Controle;
import Controleur.Global;
import Outils.son.Son;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Arene extends JFrame implements Global{

	private static final long serialVersionUID = 1L;
	private JPanel FenetreArene;
	private JTextField txtSaisie;
	private JTextArea txtTchat;
	private JPanel jpnMurs;
	private JPanel jpnJeu;
	private Controle controle;
	private Boolean client;
	private Son[]tableauSon=new Son[SON.length];
	/**
	 * Create the frame.
	 */
	
	/**
	 * @return the jpnMurs
	 */
	public JPanel getJpnMurs() {
		return jpnMurs;
	}

	/**
	 * @param jpnMurs the jpnMurs to set
	 */
	public void setJpnMurs(JPanel jpnMurs) {
		this.jpnMurs.add(jpnMurs);
		this.jpnMurs.repaint();
	}
	
	public JPanel getJpnJeu() {
		return jpnJeu;
	}
	
	public void setJpnJeu(JPanel jpnJeu) {
		this.jpnJeu.removeAll();
		this.jpnJeu.add(jpnJeu);
		this.jpnJeu.repaint();
		FenetreArene.requestFocus();
	}
	
	public String gettxtTchat() {
		return txtTchat.getText();
	}
	
	public void settxtTchat(String texte) {
		txtTchat.setText(texte);
		this.txtTchat.setCaretPosition(this.txtTchat.getDocument().getLength());
	}
	
	public void txtSaisie_KeyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_ENTER) {
			if(!((this.txtSaisie.getText()).equals(""))) {
				this.controle.evenementArene(this.txtSaisie.getText());
				this.txtSaisie.setText("");
				FenetreArene.requestFocus();
			}
		}
	}
	
	public void FenetreArene_KeyPressed(KeyEvent e) {
		int touche = -1;
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT :
		case KeyEvent.VK_RIGHT :
		case KeyEvent.VK_UP :
		case KeyEvent.VK_DOWN :
		case KeyEvent.VK_SPACE :
			touche = e.getKeyCode();
			break;
		}
		// si touche correcte, alors envoi de sa valeur
		if(touche != -1) {
			this.controle.evenementArene(touche);
		}
	}
	
	public void txtTchat_KeyPressed(KeyEvent e) {
		FenetreArene_KeyPressed(e);
	}
	
	public Arene(Controle controle,String typeJeu) {
		this.client=typeJeu.equals("client");
		// Dimension de la frame en fonction de son contenu
		this.getContentPane().setPreferredSize(new Dimension(LARGEURARENE,HAUTEURARENE+25+140));
	    this.pack();
	    // interdiction de changer la taille
		this.setResizable(false);
																																																																																											
		
		setTitle("Arena");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FenetreArene = new JPanel();
		FenetreArene.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				FenetreArene_KeyPressed(e);
			}
		});
		setContentPane(FenetreArene);
		FenetreArene.setLayout(null);
		
		jpnJeu=new JPanel();
		jpnJeu.setBounds(0,0,LARGEURARENE,HAUTEURARENE);
		jpnJeu.setOpaque(false);
		jpnJeu.setLayout(null);
		FenetreArene.add(jpnJeu);
		
		jpnMurs=new JPanel();
		jpnMurs.setBounds(0,0,LARGEURARENE,HAUTEURARENE);
		jpnMurs.setOpaque(false);
		jpnMurs.setLayout(null);
		FenetreArene.add(jpnMurs);
		
		JLabel lblFond=new JLabel("");
		lblFond.setBounds(0,0,800,600);
		String chemin="images/fonds/fondarene.jpg";
		URL resource=getClass().getClassLoader().getResource(chemin);
		lblFond.setIcon(new ImageIcon(resource));
		FenetreArene.add(lblFond);
		
		if(this.client) {
			txtSaisie=new JTextField();
			txtSaisie.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					txtSaisie_KeyPressed(e);
				}
			});
			txtSaisie.setBounds(0,600,800,25);
			FenetreArene.add(txtSaisie);
			txtSaisie.setColumns(10);
			for(int i=0;i<SON.length;i++) {
				tableauSon[i]=new Son(getClass().getClassLoader().getResource(SON[i]));
			}
		}
		
		JScrollPane jspTchat=new JScrollPane();
		jspTchat.setBounds(0,625,800,80);
		jspTchat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		FenetreArene.add(jspTchat);

		
		txtTchat=new JTextArea();
		txtTchat.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				txtTchat_KeyPressed(e);
			}
		});
		txtTchat.setEditable(false);
		jspTchat.setViewportView(txtTchat);
		
		this.controle=controle;
		
	}
	public void ajoutMurs(Object mur) {
		jpnMurs.add((JLabel)mur);
		jpnMurs.repaint();
	}
	
	public void ajoutJLabelJeu(JLabel label) {
		jpnJeu.add(label);
		jpnJeu.repaint();
	}

	public void ajoutTchat(String phrase) {
		txtTchat.setText(txtTchat.getText()+phrase+"\r\n");
		this.txtTchat.setCaretPosition(this.txtTchat.getDocument().getLength());
	}
	
	public void joueSon(Integer numSon) {
		tableauSon[numSon].play();
	}
	
}
