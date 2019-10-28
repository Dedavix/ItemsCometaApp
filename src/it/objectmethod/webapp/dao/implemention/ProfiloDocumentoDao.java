package it.objectmethod.webapp.dao.implemention;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import it.objectmethod.webapp.dao.ProfiloDocumentoInterface;
import it.objectmethod.webapp.dati.ProfiloDocumento;

public class ProfiloDocumentoDao extends NamedParameterJdbcDaoSupport implements ProfiloDocumentoInterface {

	@Override
	public List<ProfiloDocumento> getProfiles() {
		List<ProfiloDocumento> profili = null;
		String sql = "Select id, codice from cometa_easy.profilo_documento order by id";
		BeanPropertyRowMapper<ProfiloDocumento> rm = new BeanPropertyRowMapper<ProfiloDocumento>(
				ProfiloDocumento.class);
		profili = getNamedParameterJdbcTemplate().query(sql, rm);
		return profili;
	}

	@Override
	public String getCodeById(int id) {
		String sql = "Select codice from cometa_easy.profilo_documento where id = ?";
		String codice = getJdbcTemplate().queryForObject(sql, new Object[] {id}, String.class);
		return codice;
				
	}

}
