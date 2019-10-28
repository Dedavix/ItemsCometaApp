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

	public boolean validateInsert(Documento doc) {
		Lotto lotto = null;
		lotto = lottiDao.codiceLottoInArticolo(doc.getRighe().get(0).codiceLotto, doc.getRighe().get(0).codiceArticolo);
		Articolo articolo = articoliDao.searchByCode(doc.getRighe().get(0).codiceArticolo);
		boolean value = false;
		if (articolo != null) {
			if (lotto != null) {
				if (doc.getProfilo().equals("SCARICO") || doc.getProfilo().equals("DDT")) {
					lotto = lottiDao.verificaQuantità(doc.getRighe().get(0).codiceLotto,
							doc.getRighe().get(0).quantita);
					if (lotto != null) {
						value = true;
					}
				} else {
					value = true;
				}
			}
		}
		return value;
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
				lottiDao.getIdByCode(doc.getRighe().get(0).getCodiceLotto(), articolo.getId()), quantita);
		if (inserisciRiga > 0 && inserisciDocumento > 0) {
			value = true;
		}
		return value;
	}

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
		Documento doc = new Documento();
		RigaDocumento riga = new RigaDocumento();
		List<RigaDocumento> listRighe = new ArrayList<RigaDocumento>();
		riga.setCodiceArticolo(codiceArticolo);
		riga.setCodiceLotto(codiceLotto);
		riga.setQuantita(quantita);
		listRighe.add(riga);
		doc.setRighe(listRighe);
		int update = 1;
		String outputPage = "forward:/Inserisci";
		doc.setProfilo(profiliDao.getCodeById(profilo));
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(data);
		doc.setData(date);
		if (validateInsert(doc)) {
			if (doc.getProfilo().equals("CARICO")) {
				update = lottiDao.aggiungiQuantita(codiceLotto, quantita);
			}
			if (doc.getProfilo().equals("SCARICO") || doc.getProfilo().equals("DDT")) {
				update = lottiDao.sottraiQuantita(codiceLotto, quantita);
			}
			if (update > 0) {
				if (inserisciDocumento(doc, profilo, quantita)) {
					outputPage = "forward:/MostraDocumenti";
				}
			}

		}
		return outputPage;
	}

	@RequestMapping("/MostraDocumenti")
	public String mostraDocumenti(ModelMap model, @RequestParam(value = "profilo", required = false) Integer idProfilo,
			@RequestParam(value = "data1", required = false) String data1,
			@RequestParam(value = "data2", required = false) String data2) throws ParseException {
		List<Documento> documenti = documentiDao.getDocumenti();
		List<ProfiloDocumento> profiles = profiliDao.getProfiles();
		if (data1 != null && data2 != null) {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(data1);
			Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(data2);
			documenti = documentiDao.getFilteredDocuments(idProfilo, date1, date2);
		}
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
