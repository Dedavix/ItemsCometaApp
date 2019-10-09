package it.objectmethod.webapp.dao.implemention;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.objectmethod.webapp.config.ConnectionFactory;
import it.objectmethod.webapp.dao.LottiDaoInterface;
import it.objectmethod.webapp.dati.Articolo;
import it.objectmethod.webapp.dati.Lotto;

public class LottiDao implements LottiDaoInterface {

	@Override
	public List<Lotto> getLotti(String item) {
		List<Lotto> lista = new ArrayList<Lotto>();
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			conn = ConnectionFactory.getConnection();
			String sql;
			sql = "SELECT COALESCE(lotti.id, 0)as id, COALESCE(lotti.codice_lotto, 'NA')as codice_lotto ,COALESCE(lotti.id_articolo,0) as id_articolo, COALESCE(lotti.quantita,0) as quantita\r\n" + 
					"FROM cometa_easy.lotti\r\n" + 
					"where lotti.id_articolo=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,item);
			ResultSet rs = stmt.executeQuery();

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				Lotto lotto = new Lotto();
				lotto.setId(rs.getInt("id"));
				lotto.setCodice(rs.getString("codice_lotto"));
				lotto.setId_articolo(rs.getInt("id_articolo"));
				lotto.setQuantita(rs.getInt("quantita"));
				lista.add(lotto);

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
