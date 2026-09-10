package Modèle;

import javax.swing.JPanel;

import Controleur.Controle;
import Outils.connexion.Connection;

/**
 * Gestion du jeu côté client
 *
 */
public class JeuClient extends Jeu {
	
	private Connection connection;
	
	private Boolean mursOk=false;
	
	/**
	 * Controleur
	 */
	public JeuClient(Controle controle) {
		super.controle=controle;
	}
	
	@Override
	public void connexion(Connection connection) {
		this.connection=connection;
	}
	
	@Override
	public void deconnexion(Connection connection) {
		System.exit(0);
	}
	@Override
	public void reception(Connection connection,Object info) {
		if(info instanceof JPanel) {
			if(!mursOk) {
				controle.evenementJeuClient("ajout panel murs",info);
				mursOk=true;
			}
			else{
				controle.evenementJeuClient("ajout panel jeu",info);
			}
		}
		if(info instanceof String) {
			this.controle.evenementJeuClient("modif tchat", info);
		}
		if(info instanceof Integer) {
			controle.evenementJeuClient("jouer son", info);
		}
	}
	
	/**
	 * Envoi d'une information vers le serveur
	 * fais appel une fois à l'envoi dans la classe Jeu
	 */
	public void envoi(String info) {
		super.envoi(this.connection,info);
	}

}
