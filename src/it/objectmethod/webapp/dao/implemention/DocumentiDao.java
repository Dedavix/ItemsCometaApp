package it.objectmethod.webapp.dao.implemention;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import it.objectmethod.webapp.dao.DocumentiDaoInterface;
import it.objectmethod.webapp.dati.Documento;

public class DocumentiDao extends NamedParameterJdbcDaoSupport implements DocumentiDaoInterface {

	@Override
	public int getLastProgressivo(String anno, String profilo) {
		List<Integer> num = null;
		int progressivo = 0;
		String sql = "Select max(cometa_easy.documenti.progressivo)"
				+ "from cometa_easy.documenti join cometa_easy.profilo_documento on documenti.id_profilo_documento=profilo_documento.id"
				+ "where year(documenti.data)=? and profilo_documento.codice = ?"
				+ "group by profilo_documento.codice, year(documenti.data)";
		num = getJdbcTemplate().queryForList(sql, new Object[] { anno, profilo }, Integer.class);
		if (!num.isEmpty()) {
			progressivo = num.get(0);
		}
		return progressivo;
	}

	@Override
	public int inserisciDocumento(int idProfilo, Date data, int progressivo) {
		int rs = 0;
		java.sql.Date dateDB = new java.sql.Date(data.getTime());
		String sql = "INSERT INTO documenti(progressivo,id_profilo_documento,data) VALUES (:progressivo,:idProfilo,:dataSql)";
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("dataSql", dateDB);
		param.addValue("progressivo", progressivo);
		param.addValue("idProfilo", idProfilo);
		rs = getNamedParameterJdbcTemplate().update(sql, param);
		return rs;
	}

	@Override
	public int getIdDocumento(int progressivo, int idProfilo, Date data) {
		String sql = "Select documenti.id from cometa_easy.documenti where progressivo = ? and id_profilo_documento = ? and data = ?";
		int id = getJdbcTemplate().queryForObject(sql,
				new Object[] { progressivo, idProfilo, new java.sql.Date(data.getTime()) }, Integer.class);
		return id;
	}

	@Deprecated
	@Override
	public List<Documento> getDocumenti() {
		List<Documento> lista = null;
		String sql = "SELECT documenti.id as id,progressivo,codice as profilo,data from cometa_easy.documenti join cometa_easy.profilo_documento on documenti.id_profilo_documento=profilo_documento.id";
		BeanPropertyRowMapper<Documento> rm = new BeanPropertyRowMapper<Documento>(Documento.class);
		lista = getNamedParameterJdbcTemplate().query(sql, rm);
		return lista;
	}

	@Override
	public List<Documento> getFilteredDocuments(int idProfilo, Date data1, Date data2) {
		java.sql.Date date1 = new java.sql.Date(data1.getTime());
		java.sql.Date date2 = new java.sql.Date(data2.getTime());
		String sql = "SELECT " + "d.id AS id, " + "d.progressivo AS progressivo, " + "pd.codice AS profilo, "
				+ "d.data AS data " + "FROM " + "documenti d " + "JOIN " + "profilo_documento pd "
				+ "ON d.id_profilo_documento = pd.id " + "WHERE " + "(:idProfilo = 0 or pd.id = :idProfilo) "
				+ "AND data BETWEEN :date1 AND :date2 ";
		BeanPropertyRowMapper<Documento> rm = new BeanPropertyRowMapper<Documento>(Documento.class);
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("idProfilo", idProfilo);
		param.addValue("date1", date1);
		param.addValue("date2", date2);
		List<Documento> lista = getNamedParameterJdbcTemplate().query(sql, param, rm);
		return lista;
	}

}
