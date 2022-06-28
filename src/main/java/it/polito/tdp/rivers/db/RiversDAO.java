package it.polito.tdp.rivers.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiversDAO {

	public List<River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	
	public void getMisurazioniFiume(River river, Map<Integer, River> idMap) {
		String sql = "SELECT id, day, flow "
				+ "FROM flow "
				+ "WHERE river = ? "
				+ "ORDER BY day";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, river.getId());
			ResultSet rs = st.executeQuery();
			List<Flow> result = new ArrayList<>();
			
			while(rs.next()) {
				result.add(new Flow(rs.getDate("day").toLocalDate(), rs.getDouble("flow"), river));
				
			}
			
			river.setFlows(result);
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void getAverageFlow(River river) {
		String sql = "SELECT AVG(flow) AS media "
				+ "FROM flow "
				+ "WHERE river = ?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, river.getId());
			ResultSet rs = st.executeQuery();
			if(rs.next())
				river.setFlowAvg(rs.getDouble("media"));
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
