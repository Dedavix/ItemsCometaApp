package it.objectmethod.webapp.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.objectmethod.webapp.dao.LottiDaoInterface;
import it.objectmethod.webapp.dao.implemention.LottiDao;
import it.objectmethod.webapp.dati.Articolo;
import it.objectmethod.webapp.dati.Lotto;

public class LottiServlet extends HttpServlet{
	
	
	protected void doGet(HttpServletRequest richiesta, HttpServletResponse risposta)
			throws ServletException, IOException {

		LottiDaoInterface dao = new LottiDao();
		String articolo= richiesta.getParameter("idArticolo");

		List<Lotto> listaLotti = dao.getLotti(articolo);

		richiesta.setAttribute("lotti", listaLotti);
		richiesta.getRequestDispatcher("/pages/ShowLotti.jsp").forward(richiesta, risposta);
	}

}
