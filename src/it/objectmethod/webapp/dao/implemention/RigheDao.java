package it.objectmethod.webapp.dao.implemention;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import it.objectmethod.webapp.dao.RigheDocumentoDao;
import it.objectmethod.webapp.dati.Documento;
import it.objectmethod.webapp.dati.Lotto;
import it.objectmethod.webapp.dati.RigaDocumento;
import it.objectmethod.webapp.dati.mapper.LottiMapper;

public class RigheDao extends NamedParameterJdbcDaoSupport implements RigheDocumentoDao {

	@Override
	public List<RigaDocumento> getRighe(int idDocumento) {
		List<RigaDocumento> lista = new ArrayList<RigaDocumento>();
		BeanPropertyRowMapper<RigaDocumento> rm = new BeanPropertyRowMapper<RigaDocumento>(RigaDocumento.class);
		String sql= "select articoli.codice as codiceArticolo, articoli.descrizione as descrizioneArticolo, lotti.codice_lotto as codiceLotto, righe_documento.quantita as quantita from cometa_easy.documenti join cometa_easy.righe_documento on documenti.id=righe_documento.id_documento\r\n" + 
				"join cometa_easy.articoli on articoli.id=righe_documento.id_articolo join cometa_easy.lotti on lotti.id = righe_documento.id_lotto\r\n" + 
				"where documenti.id=?";
		lista = getJdbcTemplate().query(sql, new Object[] {idDocumento}, rm);
		return lista;
	}

	@Override
	public int inserisciRiga(int idDocumento, int idArticolo, int idLotto, int quantita) {
		int rs =0;
		String sql="INSERT INTO righe_documento(id_documento,id_articolo,id_lotto,quantita) VALUES(?,?,?,?)";
		rs =getJdbcTemplate().update(sql, new Object[] {idDocumento, idArticolo, idLotto, quantita});		
		return rs;
	}

}
