package it.objectmethod.webapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.objectmethod.webapp.dao.ArticoliDaoInterface;
import it.objectmethod.webapp.dao.implemention.ArticoliDao;
import it.objectmethod.webapp.dati.Articolo;

public class UpdateServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7315999456188676024L;

	protected void doGet(HttpServletRequest richiesta, HttpServletResponse risposta)
			throws ServletException, IOException {

		String id = richiesta.getParameter("idArticolo");
		Articolo articolo = new Articolo();
		ArticoliDaoInterface dao = new ArticoliDao();

		if (id != null) {
			articolo = dao.searchById(id);
		} else {
			articolo.setId(0);
		}
		richiesta.setAttribute("articolo", articolo);
		richiesta.getRequestDispatcher("pages/Update.jsp").forward(richiesta, risposta);
	}

	protected void doPost(HttpServletRequest richiesta, HttpServletResponse risposta)
			throws ServletException, IOException {
		String outputPage = "/TabellaArticoli";
		String outputMsg = "OPERAZIONE ESEGUITA CON SUCCESSO";

		ArticoliDaoInterface dao = new ArticoliDao();

		String idArticolo = richiesta.getParameter("idArticolo");
		String codiceArticolo = richiesta.getParameter("codiceArticolo");
		String descrizioneArticolo = richiesta.getParameter("descrizioneArticolo");
		
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
			richiesta.getSession().setAttribute("filtroSes", "");
		} else {
			outputMsg = "OPERAZIONE FALLITA";
			outputPage = "pages/Update.jsp";
		}
		
		richiesta.setAttribute("msg", outputMsg);
		richiesta.getRequestDispatcher(outputPage).forward(richiesta, risposta);

	}

}
