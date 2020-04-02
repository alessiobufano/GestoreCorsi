package it.polito.tdp.corsi.model;

import java.util.*;

import it.polito.tdp.corsi.db.CorsoDAO;

public class Model {
	
	private CorsoDAO dao;

	public Model() {
		this.dao = new CorsoDAO();
	}

	public List<Corso> getCorsiPerPeriodo(Integer pd) {
		
		return dao.getCorsiPerPeriodo(pd);
		
	}
	
	public Map<Corso, Integer> getIscrittiPerPeriodo(Integer pd) {
		
		return dao.getIscrittiPerPeriodo(pd);
		
	}
	
	public List<Studente> getStudentiPerCorso(Corso corso) {
		
		return dao.getStudentiPerCorso(corso);
	}
	
	public Map<String, Integer> getDivisioneStudentiPerCorso(Corso corso) {
		
		List<Studente> studenti = dao.getStudentiPerCorso(corso);
		
		Map<String, Integer> statistiche = new HashMap<>();
		for(Studente s : studenti)
		{
			if(s.getCDS()!=null && !s.getCDS().equals(""))
			{
				int num = 1;
				if(statistiche.containsKey(s.getCDS()))
				{
					num += statistiche.get(s.getCDS());
					statistiche.remove(s.getCDS());
				}
				statistiche.put(s.getCDS(), num);	
			}		
		}
		
		return statistiche;
			
	}
	
public Map<String, Integer> getDivisioneStudentiPerCorsoDAO(Corso corso) {
		
		return dao.getDivisioneStudentiPerCorso(corso);
			
	}
	
	public boolean esisteCorso(String codins) {
		
		return dao.esisteCorso(codins);
	}
	
}
