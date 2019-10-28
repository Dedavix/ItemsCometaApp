package it.objectmethod.webapp.dao;

import java.util.List;

import it.objectmethod.webapp.dati.ProfiloDocumento;

public interface ProfiloDocumentoInterface {
	
	public List <ProfiloDocumento> getProfiles();
	
	public String getCodeById(int id);

}
