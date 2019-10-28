package it.objectmethod.webapp.dao;

import java.util.List;

import it.objectmethod.webapp.dati.RigaDocumento;

public interface RigheDocumentoDao {
	
	public List<RigaDocumento> getRighe(int idDocumento);
	
	public int inserisciRiga(int idDocumento, int idArticolo, int idLotto, int quantita);

}
