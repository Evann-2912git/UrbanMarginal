package Vue;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Controleur.Controle;
import Outils.son.Son;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Cursor;
import java.awt.Dimension;
import java.net.URL;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;


public class ChoixJoueur extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel FenetreChoixJoueur;
	private Controle controle;
	private JLabel lblPerso;
	private int numPerso;
	private static final int NBPERSO=3;
	private JTextField txtPseudo;
	private Son sonEntree;
	private Son sonPrecedent;
	private Son sonSuivant;
	private Son sonBoutonGo;
	
	public void affichePerso() {
			
			String chemin="images/personnages/perso"+numPerso+"marche1d1.gif";
			URL resource=getClass().getClassLoader().getResource(chemin);
			lblPerso.setIcon(new ImageIcon(resource));
		}
	
	private void sourisNormale() {
		FenetreChoixJoueur.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	private void sourisDoigt() {
		FenetreChoixJoueur.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	private void mouse_Clicked_Gauche() {
		numPerso--;
		if (numPerso<1) {
			numPerso=NBPERSO;
		}
		affichePerso();
		sonPrecedent.play();
	}
	
	private void mouse_Clicked_Droite() {
		numPerso++;
		if (numPerso>NBPERSO) {
			numPerso=1;
		}
		affichePerso();
		sonSuivant.play();
	}
	
	private void mouse_Clicked_Go() {
		if (txtPseudo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "La saisie du pseudo est obligatoire");
		}
		else {
			controle.evenementChoixJoueur(txtPseudo.getText(), numPerso);
			sonBoutonGo.play();
		}
	}

	/**
	 * Create the frame.
	 */
	public ChoixJoueur(Controle controle) {
		// Dimension de la frame en fonction de son contenu
		this.getContentPane().setPreferredSize(new Dimension(800, 600 + 25 + 140));
	    this.pack();
	    // interdiction de changer la taille
		this.setResizable(false);
																																																																																											
		
		setTitle("Choice");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 410, 314);
		FenetreChoixJoueur = new JPanel();
		FenetreChoixJoueur.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(FenetreChoixJoueur);
		FenetreChoixJoueur.setLayout(null);
		
		lblPerso = new JLabel();
		lblPerso.setBounds(141, 114, 122, 122);
		FenetreChoixJoueur.add(lblPerso);
		lblPerso.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtPseudo = new JTextField();
		txtPseudo.setBounds(141, 245, 122, 22);
		FenetreChoixJoueur.add(txtPseudo);
		txtPseudo.setColumns(10);
		
		JLabel lblFond=new JLabel("");
		lblFond.setBounds(0,0,400,275);
		String chemin="images/fonds/fondchoix.jpg";
		URL resource=getClass().getClassLoader().getResource(chemin);
		lblFond.setIcon(new ImageIcon(resource));
		FenetreChoixJoueur.add(lblFond);
		
		JLabel lblFlecheGauche = new JLabel("New label");
		lblFlecheGauche.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mouse_Clicked_Gauche();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale();
			}
		});
		lblFlecheGauche.setBounds(65, 146, 33, 43);
		FenetreChoixJoueur.add(lblFlecheGauche);
		
		JLabel lblFlecheDroite = new JLabel("New label");
		lblFlecheDroite.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mouse_Clicked_Droite();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale();
			}
		});
		lblFlecheDroite.setBounds(295, 146, 33, 43);
		FenetreChoixJoueur.add(lblFlecheDroite);
		
		JLabel lblGo = new JLabel("New label");
		lblGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mouse_Clicked_Go();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale();
			}
		});
		lblGo.setBounds(305, 196, 70, 65);
		FenetreChoixJoueur.add(lblGo);
		
		this.controle=controle;
		numPerso=1;
		affichePerso();
		sonEntree=new Son(getClass().getClassLoader().getResource("sons/welcome.wav"));
		sonPrecedent=new Son(getClass().getClassLoader().getResource("sons/precedent.wav"));
		sonSuivant=new Son(getClass().getClassLoader().getResource("sons/suivant.wav"));
		sonBoutonGo=new Son(getClass().getClassLoader().getResource("sons/go.wav"));
		sonEntree.play();
	}
}


