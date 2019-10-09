package it.objectmethod.webapp.dao.implemention;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.objectmethod.webapp.config.ConnectionFactory;
import it.objectmethod.webapp.dao.ArticoliDaoInterface;
import it.objectmethod.webapp.dati.Articolo;

public class ArticoliDao implements ArticoliDaoInterface {

	@Override
	public List<Articolo> getItems(String filtro) {

		List<Articolo> lista = new ArrayList<Articolo>();
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			conn = ConnectionFactory.getConnection();
			String sql;
			sql = "SELECT articoli.id, articoli.codice , articoli.descrizione, coalesce(sum(lotti.quantita),0) as quantita_tot\r\n" + 
					"FROM cometa_easy.articoli left join cometa_easy.lotti on articoli.id = lotti.id_articolo\r\n" + 
					"group by(articoli.id)\r\n" + 
					"having articoli.codice like ? or articoli.descrizione like ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,"%"+filtro+"%");
			stmt.setString(2,"%"+filtro+"%");
			ResultSet rs = stmt.executeQuery();

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				Articolo item = new Articolo();
				item.setId(rs.getInt("id"));
				item.setCodice(rs.getString("codice"));
				item.setDescrizione(rs.getString("descrizione"));
				item.setQuantita_tot(rs.getInt("quantita_tot"));
				lista.add(item);

			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		}

		finally {

			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return lista;
	}
}
