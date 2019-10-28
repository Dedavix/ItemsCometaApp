package it.objectmethod.webapp.dao;

import java.util.List;

import it.objectmethod.webapp.dati.Lotto;

public interface LottiDaoInterface {
	
	public List<Lotto> getLotti(String item);
	public int getIdByCode(String codice, int idArticolo);
	public Lotto codiceLottoInArticolo(String codiceLotto, String codiceArticolo);
	public Lotto verificaQuantità(String codiceLotto, int quantita);
	public int sottraiQuantita(String codiceLotto,int quantita);
	public int aggiungiQuantita(String codiceLotto, int quantita);
	

}
