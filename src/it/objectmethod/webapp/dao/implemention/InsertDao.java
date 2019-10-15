package it.objectmethod.webapp.dao.implemention;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.objectmethod.webapp.config.ConnectionFactory;
import it.objectmethod.webapp.dao.InsertDaoInterface;
import it.objectmethod.webapp.dati.Articolo;

public class InsertDao implements InsertDaoInterface {

	@Override
	public Integer insert(String codice, String descrizione) {
		
		PreparedStatement stmt = null;
		Connection conn = null;
		int rs = 0;

		try {
			System.out.println("Creating statement...");
			conn = ConnectionFactory.getConnection();
			String sql;
			sql = "INSERT INTO articoli(codice,descrizione) VALUES (?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,codice);
			stmt.setString(2,descrizione);
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

	@Override
	public Articolo searchByCode(String codice) {
		Articolo articolo = new Articolo();
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			conn = ConnectionFactory.getConnection();
			String sql;
			sql = "SELECT * FROM articoli WHERE articoli.codice=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,codice);
			ResultSet rs = stmt.executeQuery();

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				articolo = new Articolo();
				articolo.setId(rs.getInt("id"));
				articolo.setCodice(rs.getString("codice"));
				articolo.setDescrizione(rs.getString("descrizione"));
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

		return articolo;
	}

}
