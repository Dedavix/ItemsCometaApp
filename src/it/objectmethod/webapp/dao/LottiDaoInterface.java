package it.objectmethod.webapp.dao;

import java.util.List;

import it.objectmethod.webapp.dati.Articolo;
import it.objectmethod.webapp.dati.Lotto;

public interface LottiDaoInterface {
	
	public List<Lotto> getLotti(String item);

}
