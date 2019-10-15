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
	
	private List<Articolo> CreaTabella(HttpServletRequest richiesta,HttpServletResponse risposta) {
		ArticoliDaoInterface dao = new ArticoliDao();
		List<Articolo> listaArticoli;

		if (richiesta.getParameter("filtro") == null) {
			if (richiesta.getSession().getAttribute("filtroSes") == null) {
				richiesta.getSession().setAttribute("filtroSes", "");
			}
			listaArticoli = dao.getItems((String) richiesta.getSession().getAttribute("filtroSes"));
		} else {
			richiesta.getSession().setAttribute("filtroSes", richiesta.getParameter("filtro"));
			listaArticoli = dao.getItems(((String) richiesta.getSession().getAttribute("filtroSes")).toUpperCase());
		}
		
		return listaArticoli;
		
	}

	protected void doGet(HttpServletRequest richiesta, HttpServletResponse risposta)
			throws ServletException, IOException {
		
		richiesta.setAttribute("items", CreaTabella(richiesta, risposta));
		richiesta.getRequestDispatcher("/pages/ShowItems.jsp").forward(richiesta, risposta);

		
	}
	
	protected void doPost(HttpServletRequest richiesta, HttpServletResponse risposta)
			throws ServletException, IOException {
		
		richiesta.setAttribute("items", CreaTabella(richiesta, risposta));
		richiesta.getRequestDispatcher("/pages/ShowItems.jsp").forward(richiesta, risposta);

	}


}
