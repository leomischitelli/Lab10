package it.polito.tdp.rivers.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {

	private RiversDAO dao;
	private Map<Integer, River> idMap;
	private Simulator sim;
	
	public Model() {
		dao = new RiversDAO();
		idMap = new HashMap<>();
		sim = new Simulator();
	}
	
	public List<River> getAllRivers(){
		return dao.getAllRivers();
	}
	
	public List<Flow> getInfoRiver(River river) {
		dao.getMisurazioniFiume(river, idMap);
		dao.getAverageFlow(river);
		return river.getFlows();
	}
	
	public boolean simula(long k, River river) {
		if(k < 0)
			return false;
		sim.init(k, river);
		return true;
	}
	
	public int getFallimenti() {
		return sim.getNumeroFallimenti();
	}
	
	public double getAvgCapienza() {
		return sim.getAvgCapienza();
	}
}
