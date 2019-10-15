package it.objectmethod.webapp.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.objectmethod.webapp.dao.ArticoliDaoInterface;
import it.objectmethod.webapp.dao.implemention.ArticoliDao;
import it.objectmethod.webapp.dati.Articolo;

public class ArticoliServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1078730073374281757L;

	private void processRequest(HttpServletRequest richiesta, HttpServletResponse risposta)
			throws ServletException, IOException {
		List<Articolo> listaArticoli = creaTabella(richiesta, risposta);
		richiesta.setAttribute("items", listaArticoli);
		richiesta.getRequestDispatcher("/pages/ShowItems.jsp").forward(richiesta, risposta);
	}

	private List<Articolo> creaTabella(HttpServletRequest richiesta, HttpServletResponse risposta) {
		ArticoliDaoInterface dao = new ArticoliDao();
		List<Articolo> listaArticoli;

		String filtro = richiesta.getParameter("filtro");
		if (filtro == null) {
			filtro = (String) richiesta.getSession().getAttribute("filtroSes");
		}
		filtro = (filtro != null) ? filtro.toUpperCase() : "";

		listaArticoli = dao.getItems(filtro);
		richiesta.getSession().setAttribute("filtroSes", filtro);
		return listaArticoli;
	}

	protected void doGet(HttpServletRequest richiesta, HttpServletResponse risposta)
			throws ServletException, IOException {
		processRequest(richiesta, risposta);
	}

	protected void doPost(HttpServletRequest richiesta, HttpServletResponse risposta)
			throws ServletException, IOException {
		processRequest(richiesta, risposta);
	}

}
