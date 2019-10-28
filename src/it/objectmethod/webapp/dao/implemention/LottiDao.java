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
	

	public Lotto codiceLottoInArticolo(String codiceLotto, String codiceArticolo) {

		Lotto lotto = null;
		String sql;
		List<Lotto> lotti = null; 
		sql = "Select lotti.id as id,codice_lotto,id_articolo,quantita from cometa_easy.lotti join cometa_easy.articoli on lotti.id_articolo=articoli.id\n" + 
				"where codice_lotto = ? and codice= ? ";
		lotti = this.jdbcTemplateObject.query(sql, new Object[] { codiceLotto, codiceArticolo },new LottiMapper());
		if(!lotti.isEmpty()) {
			lotto= lotti.get(0);
		}
		return lotto;
	}

	@Override
	public Lotto verificaQuantità(String codiceLotto, int quantita) {
		Lotto lotto = null;
		List<Lotto> lotti = null;	
		String sql= "SELECT * from cometa_easy.lotti where codice_lotto = ? and quantita >= ?";
		lotti = this.jdbcTemplateObject.query(sql, new Object[] { codiceLotto, quantita }, new LottiMapper());
		if(!lotti.isEmpty()) {
			lotto= lotti.get(0);
		}
		return lotto;
	}

	@Override
	public int sottraiQuantita(String codiceLotto, int quantita) {
		int result = 0;
		String sql;
		sql = "UPDATE cometa_easy.lotti SET quantita= quantita - ? WHERE codice_lotto = ? ";
		result = this.jdbcTemplateObject.update(sql, new Object[] { quantita, codiceLotto });
		return result;
	}

	@Override
	public int aggiungiQuantita(String codiceLotto, int quantita) {
		int result = 0;
		String sql;
		sql = "UPDATE cometa_easy.lotti SET quantita= quantita + ? WHERE codice_lotto = ? ";
		result = this.jdbcTemplateObject.update(sql, new Object[] { quantita, codiceLotto });
		return result;
	}

	@Override
	public int getIdByCode(String codice, int idArticolo) {
		String sql = "Select lotti.id from cometa_easy.lotti where codice_lotto = ? and id_articolo= ?";
		int id = this.jdbcTemplateObject.queryForObject(sql, new Object[] { codice, idArticolo }, Integer.class);
		return id;
	}

}
