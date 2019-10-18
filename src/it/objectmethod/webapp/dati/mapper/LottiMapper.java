package it.objectmethod.webapp.dati.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import it.objectmethod.webapp.dati.Lotto;

public class LottiMapper implements RowMapper<Lotto>{

	@Override
	public Lotto mapRow(ResultSet rs, int rowNum) throws SQLException {
		Lotto lotto = new Lotto();
		lotto.setId(rs.getInt("id"));
		lotto.setCodice(rs.getString("codice_lotto"));
		lotto.setId_articolo(rs.getInt("id_articolo"));
		lotto.setQuantita(rs.getInt("quantita"));
		return lotto;
	}

}
