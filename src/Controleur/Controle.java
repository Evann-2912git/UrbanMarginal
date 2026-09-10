package Controleur;

import Vue.Arene;

import Vue.ChoixJoueur;
import Vue.EntreeJeu;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Modèle.Jeu;
import Modèle.JeuClient;
import Modèle.JeuServeur;
import Outils.connexion.AsyncResponse;
import Outils.connexion.ClientSocket;
import Outils.connexion.Connection;
import Outils.connexion.ServeurSocket; 

public class Controle implements AsyncResponse,Global{

	private EntreeJeu frmEntreeJeu;
	private Arene frmArene;
	private ChoixJoueur frmChoixJoueur;
	private Jeu leJeu;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Controle();
	}
	
	public Controle() {
		this.frmEntreeJeu=new EntreeJeu(this);
		this.frmEntreeJeu.setVisible(true);
	}
	
	public void envoi(Connection connection,Object info) {
		connection.envoi(info);
	}
	
	public void evenementChoixJoueur(String pseudo,int numPerso) {
		this.frmChoixJoueur.dispose();
		this.frmArene.setVisible(true);
		((JeuClient)this.leJeu).envoi(PSEUDO+STRINGSEPARE+pseudo+STRINGSEPARE+numPerso);
	}
	
	public void evenementEntreeJeu(String info) {
		if(info.equals("serveur")) {
			new ServeurSocket(this,PORT);
			this.leJeu=new JeuServeur(this);
			this.frmEntreeJeu.dispose();
			this.frmArene=new Arene(this,"serveur");
			((JeuServeur) this.leJeu).constructionMurs();
			this.frmArene.setVisible(true);
		}
		else {
			new ClientSocket(this,info,PORT);
		}
	}

	@Override
	public void reception(Connection connection, String ordre, Object info) {
		// TODO Auto-generated method stub
		switch(ordre){
		case "connexion":
			if(!(this.leJeu instanceof JeuServeur)) {
				this.leJeu=new JeuClient(this);
				this.leJeu.connexion(connection);
				this.frmEntreeJeu.dispose();
				this.frmArene=new Arene(this,"client");
				this.frmChoixJoueur=new ChoixJoueur(this);
				this.frmChoixJoueur.setVisible(true);
			}
			else {
				this.leJeu.connexion(connection);
			}
			break;
		case "reception":
			this.leJeu.reception(connection,info);
			break;
		case "deconnexion":
			this.leJeu.deconnexion(connection);
			break;
		}
	}
	
	public void evenementJeuServeur(String ordre,Object info) {
		switch(ordre) {
		case "ajout mur":
			frmArene.ajoutMurs(info);
			break;
		case "ajout panel murs":
			leJeu.envoi((Connection)info,frmArene.getJpnMurs());
			break;
		case "ajout panel jeu":
			leJeu.envoi((Connection)info,frmArene.getJpnJeu());
			break;
		case "ajout jlabel jeu":
			frmArene.ajoutJLabelJeu((JLabel)info);
			break;
		case "ajout phrase":
			frmArene.ajoutTchat((String)info);
			((JeuServeur)this.leJeu).envoi(this.frmArene.gettxtTchat());
			break;
		}
	}
	
	public void evenementJeuClient(String ordre,Object info) {
		switch(ordre) {
		case "ajout panel murs":
			frmArene.setJpnMurs((JPanel)info);
			break;
		case "ajout panel jeu":
			frmArene.setJpnJeu((JPanel)info);
			break;
		case "modif tchat":
			frmArene.settxtTchat((String)info);
			break;
		case "jouer son":
			frmArene.joueSon((Integer) info);
		}
	}
	
	public void evenementArene(Object info) {
		if(info instanceof String) {
			((JeuClient)leJeu).envoi("tchat"+STRINGSEPARE+info);
		}
		if(info instanceof Integer) {
			((JeuClient)leJeu).envoi("action"+STRINGSEPARE+info);
		}
	}

}
