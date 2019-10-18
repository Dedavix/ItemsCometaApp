package it.objectmethod.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.objectmethod.webapp.dao.LottiDaoInterface;
import it.objectmethod.webapp.dati.Lotto;

@Controller
public class LottiController {
	
	@Autowired
	private LottiDaoInterface lottiDao;
	
	@GetMapping("/vediLotti")
	public String mostraLotti(@RequestParam(value = "idArticolo", required = true) String articolo, ModelMap model) {
		List<Lotto> listaLotti = lottiDao.getLotti(articolo);
		model.addAttribute("lotti", listaLotti);
		return "ShowLotti";
	}

}
