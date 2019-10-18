package it.objectmethod.webapp.dati;

public class Articolo {
	
	private int id;
	private String codice;
	private String descrizione;
	private int quantitaTot;
	
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
	public int getQuantitaTot() {
		return quantitaTot;
	}
	public void setQuantitaTot(int quantita_tot) {
		this.quantitaTot = quantita_tot;
	}
}
