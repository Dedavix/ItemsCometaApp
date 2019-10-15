package it.objectmethod.webapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.objectmethod.webapp.dao.ArticoliDaoInterface;
import it.objectmethod.webapp.dao.InsertDaoInterface;
import it.objectmethod.webapp.dao.UpdateDaoInterface;
import it.objectmethod.webapp.dao.implemention.ArticoliDao;
import it.objectmethod.webapp.dao.implemention.InsertDao;
import it.objectmethod.webapp.dao.implemention.UpdateDao;
import it.objectmethod.webapp.dati.Articolo;

public class UpdateServlet extends HttpServlet {

	protected void doGet(HttpServletRequest richiesta, HttpServletResponse risposta)
			throws ServletException, IOException {

		String id = richiesta.getParameter("idArticolo");
		Articolo articolo = new Articolo();
		ArticoliDaoInterface dao = new ArticoliDao();

		if (id != null) {
			articolo = dao.searchById(id);
			richiesta.setAttribute("idArticolo", articolo.getId());
			richiesta.setAttribute("codiceArticolo", articolo.getCodice());
			richiesta.setAttribute("descrizioneArticolo", articolo.getDescrizione());
			richiesta.getRequestDispatcher("pages/Update.jsp").forward(richiesta, risposta);
		} else {
			richiesta.getRequestDispatcher("pages/Update.jsp").forward(richiesta, risposta);
		}

	}

	protected void doPost(HttpServletRequest richiesta, HttpServletResponse risposta)
			throws ServletException, IOException {

		UpdateDaoInterface daoUp = new UpdateDao();
	    InsertDaoInterface daoIn = new InsertDao();
		String idArticolo = richiesta.getParameter("idArticolo");
		String codiceArticolo = richiesta.getParameter("codiceArticolo");
		String descrizioneArticolo = richiesta.getParameter("descrizioneArticolo");
		if (idArticolo != "") {
			int validUpdate = daoUp.update(idArticolo, codiceArticolo, descrizioneArticolo);
			if (validUpdate > 0) {
				richiesta.getSession().setAttribute("filtroSes", "");
				richiesta.setAttribute("msg", "OPERAZIONE ESEGUITA CON SUCCESSO");
			} else {
				richiesta.setAttribute("msg", "ERRORE");
			}
			richiesta.getRequestDispatcher("/TabellaArticoli").forward(richiesta, risposta);
		} else {
			int validInsert = 0;
			if(daoIn.searchByCode(codiceArticolo).getCodice()==null) {
				validInsert = daoIn.insert(codiceArticolo, descrizioneArticolo);
				if (validInsert>0) {
					richiesta.getSession().setAttribute("filtroSes", "");
					richiesta.setAttribute("msg", "OPERAZIONE ESEGUITA CON SUCCESSO");
					richiesta.getRequestDispatcher("/TabellaArticoli").forward(richiesta, risposta);
				}
				
			} else {
				
				richiesta.setAttribute("msg", "OPERAZIONE FALLITA");
				richiesta.getRequestDispatcher("pages/Update.jsp").forward(richiesta, risposta);			
			}
		}

	}

}
