package it.objectmethod.webapp.dao;

import java.util.List;

import it.objectmethod.webapp.dati.Articolo;

public interface ArticoliDaoInterface {

	public List<Articolo> getItems(String filtro);

	public Articolo searchById(String id);

	public Integer insert(String codice, String descrizione);

	public Articolo searchByCode(String codice);

	public Integer update(String idArticolo, String codice, String descrizione);

}
