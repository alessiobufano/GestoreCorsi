package it.polito.tdp.corsi.db;

import java.sql.*;
import java.util.*;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Studente;

public class CorsoDAO {
	
	public boolean esisteCorso(String codins) {
		
		String sql = "SELECT * FROM corso WHERE codins=?";
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				conn.close();
				return true;
			}
			conn.close();
			return false;
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Corso> getCorsiPerPeriodo(Integer pd) {
		
		String sql = "SELECT * FROM corso WHERE pd=?";
		List<Corso> result = new ArrayList<>();
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, pd);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Corso tempC = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				result.add(tempC);
			}
			
			conn.close();
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}
	
public Map<Corso, Integer> getIscrittiPerPeriodo(Integer pd) {
		
		String sql = "SELECT c.codins, c.nome, c.crediti, c.pd, COUNT(*) AS tot " + 
				"FROM corso c, iscrizione i " + 
				"WHERE c.codins=i.codins and c.pd=? " + 
				"GROUP BY c.codins, c.nome, c.crediti, c.pd";
		Map<Corso, Integer> result = new HashMap<Corso, Integer>();
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, pd);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Corso tempC = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				Integer num = rs.getInt("tot");
				result.put(tempC, num);
			}
			
			conn.close();
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}



public List<Studente> getStudentiPerCorso(Corso corso) {
	
	String sql = "SELECT s.matricola, s.nome, s.cognome, s.cds " + 
			"FROM studente s, iscrizione i " + 
			"WHERE s.matricola=i.matricola AND i.codins=?";
	
	List<Studente> result = new LinkedList<>();
	
	try {
		
		Connection conn = ConnectDB.getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1, corso.getCodIns());
		ResultSet rs = st.executeQuery();
		
		while(rs.next()) {
			Studente tempS = new Studente(rs.getInt("matricola"), rs.getString("nome"), rs.getString("cognome"), rs.getString("cds"));
			result.add(tempS);
		}
		
		conn.close();
		
	} catch(SQLException e) {
		throw new RuntimeException(e);
	}
	
	return result;
}


public Map<String, Integer> getDivisioneStudentiPerCorso(Corso corso) {
	
	String sql = "SELECT s.cds, COUNT(*) AS tot "+
			"FROM studente s, iscrizione i "+
			"WHERE s.matricola=i.matricola AND i.codins=? AND s.cds<>? "+
			"GROUP BY s.cds";
	
	Map<String, Integer> result = new HashMap<>();
	
	try {
		
		Connection conn = ConnectDB.getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1, corso.getCodIns());
		st.setString(2, "");
		ResultSet rs = st.executeQuery();
		
		while(rs.next()) {
			String s = rs.getString("cds");
			Integer i = rs.getInt("tot");
			result.put(s, i);
		}
		
		conn.close();
		
	} catch(SQLException e) {
		throw new RuntimeException(e);
	}
	
	return result;
}


}
