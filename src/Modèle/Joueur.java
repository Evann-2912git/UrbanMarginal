package Modèle;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Controleur.Global;

/**
 * Gestion des joueurs
 *
 */
public class Joueur extends Objet implements Global {
	
	/**
	 * pseudo saisi
	 */
	private String pseudo ;
	/**
	 * n° correspondant au personnage (avatar) pour le fichier correspondant
	 */
	private int numPerso ; 
	/**
	 * instance de JeuServeur pour communiquer avec lui
	 */
	private JeuServeur jeuServeur ;
	/**
	 * numéro d'�tape dans l'animation (de la marche, touché ou mort)
	 */
	private int etape ;
	/**
	 * la boule du joueur
	 */
	private Boule boule ;
	
	private int nbBoule;
	
	/**
	* vie restante du joueur
	*/
	private int vie ;
	
	/**
	* tourné vers la gauche (0) ou vers la droite (1)
	*/
	private int orientation ;
	
	private JLabel jLabelMessage;
	
	/**
	 * Constructeur
	 */
	public Joueur(JeuServeur leJeu) {
		this.jeuServeur=leJeu;
		this.vie=MAXVIE;
		this.orientation=1;
		this.etape=1;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	
	public int getOrientation() {
		return orientation;
	}
	
	public int getNbBoule() {
		return nbBoule;
	}

	public void setNbBoule(int nbBoule) {
		this.nbBoule=nbBoule;
	}
	
	/**
	 * Initialisation d'un joueur (pseudo et numéro, calcul de la 1ère position, affichage, création de la boule)
	 */
	public void initPerso(String pseudo,int numPerso,Collection lesJoueurs,Collection lesMurs) {
		this.pseudo=pseudo;
		this.numPerso=numPerso;
		System.out.println("joueur "+pseudo+" - num perso "+numPerso+" créé");
		this.nbBoule=10;
		super.jLabel=new JLabel();
		this.jLabelMessage=new JLabel();
		this.jLabelMessage.setHorizontalAlignment(SwingConstants.CENTER);
		this.jLabelMessage.setFont(new Font("Dialog",Font.PLAIN,8));
		this.boule=new Boule(this.jeuServeur);
		premierePosition(lesJoueurs,lesMurs);
		jeuServeur.ajoutJLabelJeuArene(this.jLabel);
		jeuServeur.ajoutJLabelJeuArene(jLabelMessage);
		jeuServeur.ajoutJLabelJeuArene(boule.getjLabel());
		this.affiche("marche", this.etape);
	}

	/**
	 * Calcul de la première position aléatoire du joueur (sans chevaucher un autre joueur ou un mur)
	 */
	private void premierePosition(Collection lesJoueurs,Collection lesMurs) {
		jLabel.setBounds(0, 0, LARGEURPERSO, HAUTEURPERSO);
		do {
			posX = (int) Math.round(Math.random() * (LARGEURARENE - LARGEURPERSO)) ;
			posY = (int) Math.round(Math.random() * (HAUTEURARENE - HAUTEURPERSO - 8)) ;
		}while(toucheCollectionObjet(lesJoueurs)!=null || toucheCollectionObjet(lesMurs)!=null);
	}
	
	/**
	 * Affiche le personnage et son message
	 */
	public void affiche(String etat,int etape) {
		super.jLabel.setBounds(this.posX,this.posY,LARGEURPERSO,HAUTEURPERSO);
		String chemin="images/personnages/perso"+this.numPerso+etat+etape+"d"+this.orientation+".gif";
		URL resource=getClass().getClassLoader().getResource(chemin);
		super.jLabel.setIcon(new ImageIcon(resource));
		jLabelMessage.setText(this.pseudo+" : "+this.vie+" boules : "+this.nbBoule);
		jLabelMessage.setBounds(this.posX-10,this.posY+HAUTEURPERSO,LARGEURPERSO+30,8);
		jeuServeur.envoiJeuATous();
	}

	/**
	 * Gère une action reçue et qu'il faut afficher (déplacement, tire de boule...)
	 */
	public void action(Integer action,Collection lesJoueurs,Collection lesMurs) {
		if(!this.estMort()) {
			switch (action) {
			case KeyEvent.VK_UP:
				posY=this.deplace(posY,action,-DEPLACEMENT,lesJoueurs,lesMurs);
				break;
			case KeyEvent.VK_LEFT:
				posX=this.deplace(posX,action,-DEPLACEMENT,lesJoueurs,lesMurs);
				this.orientation=0;
				break;
			case KeyEvent.VK_RIGHT:
				posX=this.deplace(posX,action,DEPLACEMENT,lesJoueurs,lesMurs);
				this.orientation=1;
				break;
			case KeyEvent.VK_DOWN:
				posY=this.deplace(posY,action,DEPLACEMENT,lesJoueurs,lesMurs);
				break;
			case KeyEvent.VK_SPACE:
				if(!this.boule.getjLabel().isVisible()&& nbBoule>0) {
					this.boule.tireBoule(this,lesMurs);
				}
				break;
			}	
			this.affiche("marche",this.etape);
		}
	}

	/**
	 * Gère le déplacement du personnage
	 */
	private  int deplace(Integer position,Integer action,Integer nbPas,Collection lesJoueurs,Collection lesMurs) { 
		int ancpos = position ;
		position += nbPas ;
		if (action==KeyEvent.VK_LEFT || action==KeyEvent.VK_RIGHT) {
			posX = position ;
			if(posX<=0) {
				posX=LARGEURARENE-1;
				position=posX;
			}
			if(posX>=LARGEURARENE) {
				posX=0+1;
				position=posX;
			}
		}
		else{
			posY = position ;
			if(posY<=0) {
				posY=HAUTEURARENE-1;
				position=posY;
			}
			if(posY>=HAUTEURARENE) {
				posY=0+1;
				position=posY;
			}
		}
		// controle s'il y a collision, dans ce cas, le personnage reste sur place
		if (toucheCollectionObjet(lesJoueurs)!=null || toucheCollectionObjet(lesMurs)!=null) {
			position = ancpos ;
		}
		// passe à l'étape suivante de l'animation de la marche
		etape = (etape % 4) + 1 ;
		return position ;
	}
	
	/**
	 * Gain de points de vie après avoir touché un joueur
	 */
	public void gainVie() {
		this.vie+=GAIN;
		if (this.vie>MAXVIE) {
			this.vie=MAXVIE;
		}
		this.affiche("marche", 1);
	}
	
	/**
	 * Perte de points de vie après avoir été touché 
	 */
	public void perteVie() {
		this.vie = Math.max(0, this.vie - PERTE);
	}

	/**
	 * vrai si la vie est à 0
	 * @return true si vie = 0
	 */
	public Boolean estMort() {
		return this.vie==0;
	}
	
	/**
	 * Le joueur se déconnecte et disparait
	 */
	public void departJoueur() {
		if(super.jLabel != null) {
			super.jLabel.setVisible(false);
			this.jLabelMessage.setVisible(false);
			this.boule.getjLabel().setVisible(false);
			this.jeuServeur.envoiJeuATous();
		}
	}
	
}
