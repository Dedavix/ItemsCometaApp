package it.objectmethod.webapp.dati;

public class Articolo {
	
	private int id;
	private String codice;
	private String descrizione;
	private int quantita_tot;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public int getQuantita_tot() {
		return quantita_tot;
	}
	public void setQuantita_tot(int quantita_tot) {
		this.quantita_tot = quantita_tot;
	}
}
