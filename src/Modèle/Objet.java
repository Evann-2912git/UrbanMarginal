package Modèle;

import java.util.Collection;

import javax.swing.JLabel;

import Controleur.Global;

/**
 * Informations communes � tous les objets (joueurs, murs, boules)
 * permet de m�moriser la position de l'objet et de g�rer les  collisions
 *
 */
public abstract class Objet implements Global{

	/**
	 * position X de l'objet
	 */
	protected Integer posX ;
	/**
	 * position Y de l'objet
	 */
	protected Integer posY ;
	
	protected JLabel jLabel;
	
	public JLabel getjLabel() {
		return jLabel;
	}
	
	public Integer getPosX() {
		return posX;
	}
	
	public Integer getPosY() {
		return posY;
	}
	
	public void setPos(int posX,int posY) {
		this.posX=posX;
		this.posY=posY;
	}
	
	/**
	 * contr�le si l'objet actuel touche l'objet pass� en param�tre
	 * @param objet contient l'objet � contr�ler
	 * @return true si les 2 objets se touchent
	 */
	public Boolean toucheObjet (Objet objet) {
		if (objet.jLabel==null || objet.jLabel==null) {
			return false ;
		}
		else{
			return(this.posX+this.jLabel.getWidth()>objet.posX &&
					this.posX<objet.posX+objet.jLabel.getWidth() && 
					this.posY+this.jLabel.getHeight()>objet.posY &&
					this.posY<objet.posY+objet.jLabel.getHeight()) ;
		}
	}
	
	public Objet toucheCollectionObjet(Collection<Objet> lesObjets) {
		for(Objet unObjet : lesObjets) {
			if (unObjet!=this && toucheObjet(unObjet)) {
				return unObjet;
			}
		}
		return null;
	}
	
}
