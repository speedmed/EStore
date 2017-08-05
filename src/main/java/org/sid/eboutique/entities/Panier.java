package org.sid.eboutique.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Panier implements Serializable {

	private Map<Long, LigneCommande> items=new HashMap<Long, LigneCommande>();
	
	public void addItem(Produit p, int quantite){
		LigneCommande lignec=items.get(p.getIdProduit());
		
		if(lignec==null){
		
			LigneCommande lc=new LigneCommande();
		lc.setProduit(p);
		lc.setQuantite(quantite);
		lc.setPrix(p.getPrix());
		items.put(p.getIdProduit(), lc);
		} 
		else{
			lignec.setQuantite(lignec.getQuantite()+quantite);
		}
	}
	
	public Collection<LigneCommande> getItems(){
		return items.values();
		}
		
	public int getSize(){
		return items.size();
		}
		
	public double getTotal(){
		double total=0;
		for(LigneCommande lc:items.values()){
		total+=lc.getPrix()*lc.getQuantite();
		}
		return total;
		}
		
	public void deleteItem(Long idproduit){
		items.remove(idproduit);
		}
}

