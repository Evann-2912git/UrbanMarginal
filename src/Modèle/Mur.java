package Modèle;

import java.net.URL;


import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Controleur.Global;

/**
 * Gestion des murs
 *
 */
public class Mur extends Objet implements Global{

	/**
	 * Constructeur
	 */
	public Mur() {
		posX = (int) Math.round(Math.random() * (LARGEURARENE - LARGEURMUR)) ;
		posY = (int) Math.round(Math.random() * (HAUTEURARENE - HAUTEURMUR)) ;
		jLabel=new JLabel();
		jLabel.setBounds(posX,posY,LARGEURMUR,HAUTEURMUR);
		String chemin="images/murs/mur.gif";
		URL ressource=getClass().getClassLoader().getResource(chemin);
		jLabel.setIcon(new ImageIcon(ressource));
	}

}
