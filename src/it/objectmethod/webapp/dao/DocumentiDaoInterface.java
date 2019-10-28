package it.objectmethod.webapp.dao;

import java.util.Date;
import java.util.List;

import it.objectmethod.webapp.dati.Documento;
import it.objectmethod.webapp.dati.RigaDocumento;

public interface DocumentiDaoInterface {
	
	public int inserisciDocumento(int idProfilo, Date data, int progressivo);
	
	public int getLastProgressivo(String anno,String profilo);
	
	public int getIdDocumento(int progressivo, int idProfilo, Date data);
	
	public List<Documento> getDocumenti();
	
	public List<Documento> getFilteredDocuments(int idProfilo, Date data1, Date data2);
	
	

}
