package it.objectmethod.webapp.dao.implemention;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.objectmethod.webapp.config.ConnectionFactory;
import it.objectmethod.webapp.dao.UpdateDaoInterface;

public class UpdateDao implements UpdateDaoInterface {

	@Override
	public Integer update(String idArticolo, String codiceArticolo, String descrizioneArticolo) {

			PreparedStatement stmt = null;
			Connection conn = null;
			int rs = 0;

			try {
				// STEP 4: Execute a query
				System.out.println("Creating statement...");
				conn = ConnectionFactory.getConnection();
				String sql;
				sql = "UPDATE articoli set codice = ?, descrizione = ? where id = ? ";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1,codiceArticolo);
				stmt.setString(2,descrizioneArticolo);
				stmt.setString(3,idArticolo);
				rs = stmt.executeUpdate();

				// STEP 6: Clean-up environment
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

			return rs;
		}

}
