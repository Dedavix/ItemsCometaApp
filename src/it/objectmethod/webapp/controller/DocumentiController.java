package it.objectmethod.webapp.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.objectmethod.webapp.dao.ArticoliDaoInterface;
import it.objectmethod.webapp.dao.DocumentiDaoInterface;
import it.objectmethod.webapp.dao.LottiDaoInterface;
import it.objectmethod.webapp.dao.ProfiloDocumentoInterface;
import it.objectmethod.webapp.dao.RigheDocumentoDao;
import it.objectmethod.webapp.dati.Articolo;
import it.objectmethod.webapp.dati.Documento;
import it.objectmethod.webapp.dati.Lotto;
import it.objectmethod.webapp.dati.ProfiloDocumento;
import it.objectmethod.webapp.dati.RigaDocumento;

@Controller
public class DocumentiController {

	@Autowired
	private DocumentiDaoInterface documentiDao;

	@Autowired
	private ArticoliDaoInterface articoliDao;

	@Autowired
	private LottiDaoInterface lottiDao;

	@Autowired
	private RigheDocumentoDao righeDao;

	@Autowired
	private ProfiloDocumentoInterface profiliDao;

	@GetMapping("/Inserisci")
	public String mostraInserimento(ModelMap model) {
		List<ProfiloDocumento> profiles = profiliDao.getProfiles();
		model.addAttribute("profiles", profiles);
		return "insertDocument";
	}

	@GetMapping("/effettuaInserimento")
	public String inserisci(@RequestParam(value = "profilo") int profilo, @RequestParam(value = "data") String data,
			@RequestParam(value = "codiceArticolo") String codiceArticolo,
			@RequestParam(value = "codiceLotto") String codiceLotto,
			@RequestParam(value = "quantita", defaultValue = "0") int quantita, ModelMap model) throws ParseException {

		String outputPage = "forward:/Inserisci";
		int update = 1;

		Documento doc = new Documento();
		List<RigaDocumento> listRighe = new ArrayList<RigaDocumento>();

		RigaDocumento riga = new RigaDocumento();
		riga.setCodiceArticolo(codiceArticolo);
		riga.setCodiceLotto(codiceLotto);
		riga.setQuantita(quantita);

		listRighe.add(riga);
		doc.setRighe(listRighe);

		String codProf = profiliDao.getCodeById(profilo); // In questo contesto non serve, più che altro dovremmo
															// prelevare un oggetto di tipo ProfiloDocumento per la fase
															// successiva
		doc.setProfilo(codProf);
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(data); // Non chiamiamo direttamente metodi sul new
																	// SimpleDateFormat, vedi mostra documenti
		doc.setData(date);

		boolean isValid = validateInsert(doc);
		if (isValid) {
			if (doc.getProfilo().equals("CARICO")) { // La verifica andrebbe fatta su un oggetto di tipo
														// ProfiloDocumento e verificare la colonna movimenta_merce
				update = lottiDao.aggiungiQuantita(codiceLotto, quantita);
			}
			if (doc.getProfilo().equals("SCARICO") || doc.getProfilo().equals("DDT")) { // idem sopra
				update = lottiDao.sottraiQuantita(codiceLotto, quantita);
			}
			if (update > 0) {
				boolean inserito = inserisciDocumento(doc, profilo, quantita);
				if (inserito) {
					outputPage = "forward:/MostraDocumenti";
				}
			}

		} else {
			// gestione messaggi di errore...
		}
		return outputPage;
	}

	public boolean inserisciDocumento(Documento doc, int idProfilo, int quantita) {

		boolean value = false;
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		String year = df.format(doc.getData());
		Articolo articolo = articoliDao.searchByCode(doc.getRighe().get(0).codiceArticolo);

		// Procedura corretta con Calendar
		Calendar cal = Calendar.getInstance();
		cal.setTime(doc.getData());
		int y = cal.get(Calendar.YEAR);

		// Procedura corretta con LocalDate - Java 8+
		LocalDate ld = doc.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		y = ld.getYear();

		int progressivo = documentiDao.getLastProgressivo(year, doc.getProfilo()) + 1;
		int inserisciDocumento = documentiDao.inserisciDocumento(idProfilo, doc.getData(), progressivo);
		int idDocumento = documentiDao.getIdDocumento(progressivo, idProfilo, doc.getData());
		int inserisciRiga = righeDao.inserisciRiga(idDocumento, articolo.getId(),
				lottiDao.getIdByCode(doc.getRighe().get(0).getCodiceLotto(), articolo.getId()), quantita); //TERRIBILE non si capisce ne cosa fa e potrebbe creare errori tremendi
		if (inserisciRiga > 0 && inserisciDocumento > 0) {
			value = true;
		}
		return value;
	}

	public boolean validateInsert(Documento doc) {

		Lotto lotto = null;

		lotto = lottiDao.codiceLottoInArticolo(doc.getRighe().get(0).codiceLotto // TODO ERRORE GRAVE, accesso diretto a
																					// una variabile senza getter o
																					// setter
				, doc.getRighe().get(0).codiceArticolo); // Errato comunque concatenare tante chiamate a metodi su una
															// riga, se qualcosa va in errore
															// diventa incasinato capire cosa, inoltre aumentiamo le
															// probabilità di errore
		Articolo articolo = articoliDao.searchByCode(doc.getRighe().get(0).codiceArticolo); // Stesso discorso di su

		boolean value = false;
		if (articolo != null) {
			if (lotto != null) {
				if (doc.getProfilo().equals("SCARICO") || doc.getProfilo().equals("DDT")) { // Verifica va fatta sulla
																							// colonna MOVIMENTA MERCE
																							// (in questo caso != '-')

					lotto = lottiDao.verificaQuantità(doc.getRighe().get(0).codiceLotto, // No accenti nei nomi dei
																							// metodi!!
							doc.getRighe().get(0).quantita);
					// Altro errore, il lotto lo abbiamo gia e sappiamo che e' diverso da null,
					// basta verificare la quantita
//					if(lotto.getQuantita() >= riga.getQuantita()) {
//						OK
//					}
					if (lotto != null) {
						value = true; // Gestito un messaggio di errore più che un boolean
					}
				} else {
					value = true; // Idem, messaggio errore
				}
			}
		}

		// Io di solito per codificare i tipi di errore mi creo un ENUM
		// chiamata tipo StatoInserimentoDocumentoEnum
		// (OK ("Inserimento con successo!"), ERR_LOTTO("Errore ricerca lotto, il lotto
		// indicato non esiste: "), ERR_ART("blabla")
		return value;
	}

	@RequestMapping("/MostraDocumenti")
	public String mostraDocumenti(ModelMap model, @RequestParam(value = "profilo", required = false) Integer idProfilo,
			@RequestParam(value = "data1", required = false) String dateFromReq,
			@RequestParam(value = "data2", required = false) String dateToReq) throws ParseException {

//		List<Documento> documenti = documentiDao.getDocumenti(); NO non prelevare comunque tutti, fare in modo che il metodo filtrato possa ritornare tutti i dati se usati senza filtri

		List<Documento> documenti = null;
		List<ProfiloDocumento> profiles = profiliDao.getProfiles();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar calFrom = Calendar.getInstance();
		calFrom.set(Calendar.YEAR, 1900);
		Date dateFrom = calFrom.getTime();

		if (!StringUtils.isEmpty(dateFromReq)) {
			dateFrom = sdf.parse(dateFromReq);
		}

		Calendar calTo = Calendar.getInstance();
		calTo.set(Calendar.YEAR, 2200);
		Date dateTo = calTo.getTime();

		if (!StringUtils.isEmpty(dateToReq)) {
			dateTo = sdf.parse(dateToReq);
		}

		if (idProfilo == null) {
			idProfilo = 0;
		}
		documenti = documentiDao.getFilteredDocuments(idProfilo, dateFrom, dateTo);

		model.addAttribute("profiles", profiles);
		model.addAttribute("listaDocumenti", documenti);
		return "showDocuments";
	}

	@GetMapping("/mostraRighe")
	public String mostraRighe(ModelMap model, @RequestParam(value = "idDocumento") int idDocumento) {
		List<RigaDocumento> righe = righeDao.getRighe(idDocumento);
		model.addAttribute("listaRighe", righe);
		return "showRighe";

	}

}
