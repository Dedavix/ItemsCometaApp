package it.objectmethod.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.objectmethod.webapp.dao.ArticoliDaoInterface;
import it.objectmethod.webapp.dao.LottiDaoInterface;
import it.objectmethod.webapp.dao.implemention.ArticoliDao;
import it.objectmethod.webapp.dao.implemention.LottiDao;
import it.objectmethod.webapp.dati.Articolo;
import it.objectmethod.webapp.dati.Filtro;
import it.objectmethod.webapp.dati.Lotto;


@Controller
public class AppController {
	
	@Autowired
	private Filtro filtro;
	
	private List<Articolo> creaTabella() {
		ArticoliDaoInterface dao = new ArticoliDao();
		List<Articolo> listaArticoli;
		filtro.setFiltro((filtro.getFiltro()!=null) ? filtro.getFiltro().toUpperCase() : "");
		listaArticoli = dao.getItems(filtro.getFiltro());
		return listaArticoli;
	}
	
	@GetMapping("/index")
	public String mostraArticoli(@RequestParam(value = "filtro", required= false) String filtroPassato ,ModelMap model) {
		if (filtroPassato!=null) {
			filtro.setFiltro(filtroPassato);
		}
		model.addAttribute("items", creaTabella());		
		return "ShowItems";
	}
	
	@PostMapping("/index")
	public String mostraArticoli2(ModelMap model) {
		model.addAttribute("items", creaTabella());		
		return "ShowItems";
	}
	
	@GetMapping("/vediLotti")
	public String mostraLotti(@RequestParam(value="idArticolo", required = true) String articolo,ModelMap model) {
		LottiDaoInterface dao = new LottiDao();
		List<Lotto> listaLotti = dao.getLotti(articolo);
		model.addAttribute("lotti", listaLotti);		
		return "ShowLotti";
	}
	
	@GetMapping("/modifica")
	public String mostraForm(@RequestParam(value="idArticolo", required = false) String id, ModelMap model) {
			Articolo articolo = new Articolo();
			ArticoliDaoInterface dao = new ArticoliDao();
			if (id != null) {
				articolo = dao.searchById(id);
			} else {
				articolo.setId(0);
			}
			model.addAttribute("articolo", articolo);
			return "Update";		
	}
	
	@PostMapping("/effettuaModifica")
	public String effettuaModifica(@RequestParam(value="idArticolo")String idArticolo, @RequestParam("codiceArticolo")String codiceArticolo,@RequestParam("descrizioneArticolo") String descrizioneArticolo, ModelMap model) {
		String outputPage = "forward:/index";
		String outputMsg = "OPERAZIONE ESEGUITA CON SUCCESSO";

		ArticoliDaoInterface dao = new ArticoliDao();
		int valid = 0;
		if (!idArticolo.equals("0")) {
			valid = dao.update(idArticolo, codiceArticolo, descrizioneArticolo);
		} else {
			Articolo art = dao.searchByCode(codiceArticolo);
			if (art == null) {
				valid = dao.insert(codiceArticolo, descrizioneArticolo);
			}
		}
		if (valid > 0) {
			filtro.setFiltro("");
		} else {
			outputMsg = "OPERAZIONE FALLITA";
			outputPage = "forward:/Update";
		}
		model.addAttribute("msg", outputMsg);
		return outputPage;
	}

}
