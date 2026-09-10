package Modèle;

import java.net.URL;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Controleur.Global;

/**
 * Gestion de la boule
 *
 */
public class Boule extends Objet implements Global,Runnable{

	/**
	 * instance de JeuServeur pour la communication
	 */
	private JeuServeur jeuServeur ;
	
	private Joueur attaquant;
	
	private Collection lesMurs;
	
	/**
	 * Constructeur
	 */
	public Boule(JeuServeur jeuServeur) {
		this.jeuServeur=jeuServeur;
		super.jLabel=new JLabel();
		super.jLabel.setVisible(false);
		String chemin="images/boules/boule.gif";
		URL ressource=getClass().getClassLoader().getResource(chemin);
		super.jLabel.setIcon(new ImageIcon(ressource));
		super.jLabel.setBounds(0,0,LARGEURBOULE,HAUTEURBOULE);
	}
	
	/**
	 * Tire d'une boule
	 */
	public void tireBoule(Joueur attaquant,Collection lesMurs) {
		this.attaquant=attaquant;
		this.lesMurs=lesMurs;
		this.posY=attaquant.getPosY()+HAUTEURPERSO/2;
		if(attaquant.getOrientation()==0) {
			this.posX=attaquant.getPosX()-LARGEURBOULE-1;
		}
		else {
			this.posX=attaquant.getPosX()+LARGEURPERSO+1;
		}
		new Thread(this).start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		jeuServeur.envoi(0);
		this.attaquant.setNbBoule(this.attaquant.getNbBoule()-1);
		attaquant.affiche("marche",1);
		super.jLabel.setVisible(true);
		Joueur victime=null;
		Collection lesJoueurs;
		int lePas;
		if(attaquant.getOrientation()==1) {
			lePas=DEPLACEMENT;
		}
		else {
			lePas=-DEPLACEMENT;
		}
		do {
			this.posX+=lePas;
			this.jLabel.setBounds(this.posX,this.posY,LARGEURBOULE,HAUTEURBOULE);
			this.jeuServeur.envoiJeuATous();
			this.jeuServeur.getLesJoueurs();
			lesJoueurs=this.jeuServeur.getLesJoueurs();
			victime=(Joueur) super.toucheCollectionObjet(lesJoueurs);
		}while(posX>=0 && posX<=LARGEURARENE && this.toucheCollectionObjet(lesMurs)==null && victime==null);
		if(victime!=null && !victime.estMort()) {
			this.attaquant.setNbBoule(this.attaquant.getNbBoule()+2);
			victime.perteVie();
			attaquant.gainVie();
			for(int i=1;i<=2;i++) {
				victime.affiche("touche", i);
				pause(80,0);
			}
			if(victime.estMort()) {
				jeuServeur.envoi(2);
				this.attaquant.setNbBoule(this.attaquant.getNbBoule()+2);
				this.attaquant.affiche("marche", 1);
				for(int i=1;i<=2;i++) {
					victime.affiche("mort", i);
					pause(80,0);
				}
			}
			else {
				jeuServeur.envoi(1);
				victime.affiche("marche",1);
			}
		}
		this.jLabel.setVisible(false);
		this.jeuServeur.envoiJeuATous();
	}
	
	public void pause(long milliSeconde,int nanoSeconde) {
		try {
			Thread.sleep(milliSeconde,nanoSeconde);
		} 
		catch (InterruptedException e) {
			System.out.println("erreur pause");
		}
	}
	
}
