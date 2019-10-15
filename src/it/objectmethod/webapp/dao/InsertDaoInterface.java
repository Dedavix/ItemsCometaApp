package it.objectmethod.webapp.dao;

import it.objectmethod.webapp.dati.Articolo;

public interface InsertDaoInterface {
	
	public Integer insert(String codice, String descrizione);
	
	public Articolo searchByCode(String codice);

}
