package org.sid.eboutique;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.sid.eboutique.entities.Categorie;
import org.sid.eboutique.entities.Produit;
import org.sid.eboutique.metier.IAdminCategoriesMetier;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestJPA {
	ClassPathXmlApplicationContext context;
	@Before
	public void setUp() throws Exception {
		context=new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
	}

	@Test
	public void test1() {
		
		try {
			
			IAdminCategoriesMetier metier=(IAdminCategoriesMetier) context.getBean("metier");
			List<Categorie> cts1=metier.listCategories();
			metier.ajouterCategorie(new Categorie("Ordinateurs", "PC Portable", null, "image1.jpg"));
			metier.ajouterCategorie(new Categorie("Imprimantes", "imprimante laser", null, "image2.jpg"));
			List<Categorie> cts2=metier.listCategories();
			assertTrue(cts1.size()+2==cts2.size());
			} catch (Exception e) {
			assertTrue(e.getMessage(),false);
			}
	}
	
	@Test
	public void test2() {
		
		try {
			
			IAdminCategoriesMetier metier=(IAdminCategoriesMetier) context.getBean("metier");
			List<Produit> prods=metier.listProduits();
			metier.ajouterProduit(new Produit("Sony vaio", "Core i5 1To 8GO RAM", 10000, "image1.jpg", 1, true), 1L);
			metier.ajouterProduit(new Produit("Dell250", "Core i7 2To 16GO RAM", 15000, "image2.jpg", 2, true), 1L);
			metier.ajouterProduit(new Produit("Canon3600", "Cartouche couleur", 2500, "image3.jpg", 4, true), 2L);
			metier.ajouterProduit(new Produit("HP1600", "Laser Noir", 3000, "image4.jpg", 3, true), 2L);
			List<Produit> prod2=metier.listProduits();
			assertTrue(prods.size()+4==prod2.size());
			} catch (Exception e) {
			assertTrue(e.getMessage(),false);
			}
	}

}
