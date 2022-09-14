package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interactions;


public class GenesDao {
	private Map<String, Genes> idMap;
	
	public Map<String,Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		idMap = new HashMap<String, Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				
				idMap.put(genes.getGeneId(), genes);
				result.add(genes);
				
			}
			res.close();
			st.close();
			conn.close();
			return idMap;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}

	
	public List<Integer> getAllCromosomes() {
		String sql = "SELECT DISTINCT Chromosome FROM Genes";
		List<Integer> result = new ArrayList<Integer>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				if(res.getInt("Chromosome")!=0){
					result.add(res.getInt("Chromosome"));
				}
			}
			res.close();
			st.close();
			conn.close();
			return result;
		} catch(SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
		
	}
		
	
	public List<Interactions> getAllInteractions() {
		String sql = "SELECT * FROM interactions";
		List<Interactions> result = new ArrayList<Interactions>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(idMap.get(res.getString("GeneID1"))!=null && idMap.get(res.getString("GeneID2"))!= null)
				{
				Interactions c = new Interactions(idMap.get(res.getString("GeneID1")), idMap.get(res.getString("GeneID2")), res.getString("Type"),  res.getDouble("Expression_Corr"));
				result.add(c);
				}
				
			}
			res.close();
			st.close();
			conn.close();
			return result;
		} catch(SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
		
	}
	


	
}
