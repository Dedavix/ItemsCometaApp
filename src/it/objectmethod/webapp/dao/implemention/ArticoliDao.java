package it.objectmethod.webapp.dao.implemention;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import it.objectmethod.webapp.dao.ArticoliDaoInterface;
import it.objectmethod.webapp.dati.Articolo;

public class ArticoliDao extends NamedParameterJdbcDaoSupport implements ArticoliDaoInterface {

	@Override
	public List<Articolo> getItems(String filtro) {
		List<Articolo> lista = null;
		String sql = "SELECT articoli.id, articoli.codice , articoli.descrizione, coalesce(sum(lotti.quantita),0) as quantitaTot "
				+ "FROM cometa_easy.articoli left join cometa_easy.lotti on articoli.id = lotti.id_articolo "
				+ "group by(articoli.id) " + "having articoli.codice like :filtro or articoli.descrizione like :filtro";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("filtro", "%"+filtro+"%");
		BeanPropertyRowMapper<Articolo> rm = new BeanPropertyRowMapper<Articolo>(Articolo.class);
		lista = getNamedParameterJdbcTemplate().query(sql, params, rm);
		return lista;
	}

	@Override
	public Articolo searchById(String id) {
		Articolo articolo = null;
		String sql = "SELECT * FROM articoli WHERE articoli.id=?";
		BeanPropertyRowMapper<Articolo> rm = new BeanPropertyRowMapper<Articolo>(Articolo.class);
		articolo = getJdbcTemplate().queryForObject(sql, new Object[] { id }, rm);
		return articolo;
	}

	@Override
	public Integer insert(String codice, String descrizione) {
		int rs = 0;
		String sql = "INSERT INTO articoli(codice,descrizione) VALUES (?,?)";
		rs = getJdbcTemplate().update(sql, new Object[] { codice, descrizione });
		return rs;
	}

	@Override
	public Articolo searchByCode(String codice) {
		Articolo articolo = null;
		String sql = "SELECT * FROM articoli WHERE articoli.codice=?";
		BeanPropertyRowMapper<Articolo> rm = new BeanPropertyRowMapper<Articolo>(Articolo.class);
		articolo = getJdbcTemplate().queryForObject(sql, new Object[] { codice }, rm);
		return articolo;
	}

	@Override
	public Integer update(String idArticolo, String codiceArticolo, String descrizioneArticolo) {
		int rs = 0;
		String sql = "UPDATE articoli set codice = ?, descrizione = ? where id = ? ";
		rs = getJdbcTemplate().update(sql, new Object[] { codiceArticolo, descrizioneArticolo, idArticolo });
		return rs;
	}
}
