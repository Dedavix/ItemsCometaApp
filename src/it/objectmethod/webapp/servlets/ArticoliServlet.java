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
	
	protected void doGet(HttpServletRequest richiesta, HttpServletResponse risposta)
			throws ServletException, IOException {
		
		ArticoliDaoInterface dao = new ArticoliDao();

		List<Articolo> listaArticoli = dao.getItems();

		richiesta.setAttribute("items", listaArticoli);
		richiesta.getRequestDispatcher("/pages/ShowItems.jsp").forward(richiesta, risposta);
	}

}
