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
			sql = "SELECT articoli.id, articoli.codice , articoli.descrizione, coalesce(sum(lotti.quantita),0) as quantita_tot "
					+ "FROM cometa_easy.articoli left join cometa_easy.lotti on articoli.id = lotti.id_articolo "
					+ "group by(articoli.id) " + "having articoli.codice like ? or articoli.descrizione like ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + filtro + "%");
			stmt.setString(2, "%" + filtro + "%");
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

	@Override
	public Articolo searchById(String id) {
		Articolo articolo = new Articolo();
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			conn = ConnectionFactory.getConnection();
			String sql;
			sql = "SELECT * FROM articoli WHERE articoli.id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
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
			stmt.setString(1, codice);
			stmt.setString(2, descrizione);
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
		Articolo articolo = null;
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			conn = ConnectionFactory.getConnection();
			String sql;
			sql = "SELECT * FROM articoli WHERE articoli.codice=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, codice);
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
			stmt.setString(1, codiceArticolo);
			stmt.setString(2, descrizioneArticolo);
			stmt.setString(3, idArticolo);
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
