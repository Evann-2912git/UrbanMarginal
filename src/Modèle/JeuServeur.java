package Modèle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import javax.swing.JLabel;

import Controleur.Controle;
import Controleur.Global;
import Outils.connexion.Connection;

/**
 * Gestion du jeu côté serveur
 *
 */
public class JeuServeur extends Jeu implements Global{

	/**
	 * Collection de murs
	 */
	private ArrayList<Mur> lesMurs = new ArrayList<Mur>() ;
	/**
	 * Collection de joueurs
	 */
	private Hashtable<Connection,Joueur> lesJoueurs=new Hashtable<Connection,Joueur>();
	
	/**
	 * Constructeur
	 */
	public JeuServeur(Controle controle) {
		super.controle=controle;
	}
	
	public Collection getLesJoueurs() {
		return lesJoueurs.values();
	}
	
	@Override
	public void connexion(Connection connection) {
		this.lesJoueurs.put(connection, new Joueur(this));
	}

	@Override
	public void reception(Connection connection,Object info) {
		String[] message=((String)info).split(STRINGSEPARE);
		switch(message[0]){
		case PSEUDO:
			controle.evenementJeuServeur("ajout panel murs",connection);
			this.lesJoueurs.get(connection).initPerso(message[1],Integer.parseInt(message[2]),lesJoueurs.values(),lesMurs);
			controle.evenementJeuServeur("ajout phrase", "***"+this.lesJoueurs.get(connection).getPseudo()+" vient de se connecter***");
			break;
		case "tchat":
			controle.evenementJeuServeur("ajout phrase",this.lesJoueurs.get(connection).getPseudo()+" > "+message[1]);
			break;
		case "action":
			this.lesJoueurs.get(connection).action(Integer.parseInt(message[1]),lesJoueurs.values(),lesMurs);
			break;
		}
	}
	
	@Override
	public void deconnexion(Connection connection) {
		this.lesJoueurs.get(connection).departJoueur();
		this.lesJoueurs.remove(connection);
	}

	/**
	 * Envoi d'une information vers tous les clients
	 * fais appel plusieurs fois à l'envoi de la classe Jeu
	 */
	public void envoi(Object info) {
		for(Connection uneConnection:lesJoueurs.keySet()) {
			super.envoi(uneConnection, info);
		}
	}

	/**
	 * Génération des murs
	 */
	public void constructionMurs() {
		for(int i=0;i<20;i++) {
			lesMurs.add(new Mur());
			this.controle.evenementJeuServeur("ajout mur", lesMurs.get(lesMurs.size()-1).getjLabel());
		}
	}
	
	public void ajoutJLabelJeuArene(JLabel labelAjout) {
		controle.evenementJeuServeur("ajout jlabel jeu", labelAjout);;
	}
	
	public void envoiJeuATous() {
		for(Connection uneConnection:lesJoueurs.keySet()) {
			controle.evenementJeuServeur("ajout panel jeu",uneConnection);
		}
	}
	
}
