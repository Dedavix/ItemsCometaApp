package it.objectmethod.webapp.dao.implemention;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import it.objectmethod.webapp.dao.LottiDaoInterface;
import it.objectmethod.webapp.dati.Lotto;
import it.objectmethod.webapp.dati.mapper.LottiMapper;

public class LottiDao implements LottiDaoInterface {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(this.dataSource);
	}

	@Override
	public List<Lotto> getLotti(String item) {
		List<Lotto> lista = new ArrayList<Lotto>();
		String sql;
		sql = "SELECT COALESCE(lotti.id, 0)as id, COALESCE(lotti.codice_lotto, 'NA')as codice_lotto ,COALESCE(lotti.id_articolo,0) as id_articolo, COALESCE(lotti.quantita,0) as quantita\r\n"
				+ "FROM cometa_easy.lotti\r\n" + "where lotti.id_articolo=?";
		lista = this.jdbcTemplateObject.query(sql, new Object[] { item }, new LottiMapper());
		return lista;
	}

}
