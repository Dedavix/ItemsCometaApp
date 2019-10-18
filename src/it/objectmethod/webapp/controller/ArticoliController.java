package it.objectmethod.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.objectmethod.webapp.dao.ArticoliDaoInterface;
import it.objectmethod.webapp.dati.Articolo;
import it.objectmethod.webapp.dati.Filtro;

@Controller
public class ArticoliController {

	@Autowired
	private Filtro filtro;
	
	@Autowired
	private ArticoliDaoInterface articoliDao;

	private List<Articolo> creaTabella() {
		List<Articolo> listaArticoli;
		filtro.setFiltro((filtro.getFiltro() != null) ? filtro.getFiltro().toUpperCase() : "");
		listaArticoli = articoliDao.getItems(filtro.getFiltro());
		return listaArticoli;
	}

	@RequestMapping("/index")
	public String mostraArticoli(@RequestParam(value = "filtro", required = false) String filtroPassato,
			ModelMap model) {
		if (filtroPassato != null) {
			filtro.setFiltro(filtroPassato);
		}
		model.addAttribute("filtro", filtro.getFiltro());
		model.addAttribute("items", creaTabella());
		return "ShowItems";
	}


	@RequestMapping("/modifica")
	public String mostraForm(@RequestParam(value = "idArticolo", required = false) String id, ModelMap model) {
		Articolo articolo = new Articolo();
		if (id != null) {
			articolo = articoliDao.searchById(id);
		} else {
			articolo.setId(0);
		}
		model.addAttribute("articolo", articolo);
		return "Update";
	}

	@PostMapping("/effettuaModifica")
	public String effettuaModifica(@RequestParam(value = "idArticolo") String idArticolo,
			@RequestParam("codiceArticolo") String codiceArticolo,
			@RequestParam("descrizioneArticolo") String descrizioneArticolo, ModelMap model) {
		String outputPage = "forward:/index";
		String outputMsg = "OPERAZIONE ESEGUITA CON SUCCESSO";
		int valid = 0;
		if (!idArticolo.equals("0")) {
			valid = articoliDao.update(idArticolo, codiceArticolo, descrizioneArticolo);
		} else {
			Articolo art = articoliDao.searchByCode(codiceArticolo);
			if (art == null) {
				valid = articoliDao.insert(codiceArticolo, descrizioneArticolo);
			}
		}
		if (valid > 0) {
			filtro.setFiltro("");
		} else {
			outputMsg = "OPERAZIONE FALLITA";
			outputPage = "forward:/modifica";
		}
		model.addAttribute("msg", outputMsg);
		return outputPage;
	}

}
