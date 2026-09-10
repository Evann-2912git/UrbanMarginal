package TestUnitaire;

import static org.junit.jupiter.api.Assertions.*;
import Modèle.Objet;
import Modèle.Mur;

import org.junit.jupiter.api.Test;

class ObjetTest {
	Mur obj1=new Mur();
	Mur obj2=new Mur();
	@Test
	void testToucheObjet() {
		obj1.setPos(100,100);
		obj2.setPos(80, 100);
		assertEquals(true,obj1.toucheObjet(obj2));
		obj2.setPos(120, 100);
		assertEquals(true,obj1.toucheObjet(obj2));
		obj2.setPos(100, 80);
		assertEquals(true,obj1.toucheObjet(obj2));
		obj2.setPos(100, 120);
		assertEquals(true,obj1.toucheObjet(obj2));
		obj2.setPos(50, 100);
		assertEquals(false,obj1.toucheObjet(obj2));
		obj2.setPos(150, 100);
		assertEquals(false,obj1.toucheObjet(obj2));
		obj2.setPos(100, 50);
		assertEquals(false,obj1.toucheObjet(obj2));
		obj2.setPos(100, 150);
		assertEquals(false,obj1.toucheObjet(obj2));
	}

}
