package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import it.polito.tdp.genes.db.GenesDao;


public class Model {
	GenesDao dao;
	Graph<Integer, DefaultWeightedEdge> grafo;
	Map<String,Genes> idMap;
	List<Interactions> interazioni;
	List<Integer> best;
	List<Integer> daVisitare;
	double max;
	double min;
	int magS;
	int minS;
	
	

	public Model() {
		super();
		dao= new GenesDao();
		grafo = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		idMap= dao.getAllGenes();
		interazioni = dao.getAllInteractions();
		
		
	}


	public void creaGrafo() {
		List<Integer> cromosomes= dao.getAllCromosomes();
		Graphs.addAllVertices(grafo, cromosomes);
	
		
		DefaultWeightedEdge e;
		for(Interactions i : interazioni) {
			Integer v1 = i.getGene1().getChromosome();
			Integer v2 = i.getGene2().getChromosome();
			if(grafo.vertexSet().contains(v1) && grafo.vertexSet().contains(v2) && !v1.equals(v2)){
			e = grafo.getEdge(v1, v2);
			if(e == null) {
				Graphs.addEdge(grafo, v1, v2, i.getExpressionCorr());
			}else {
				grafo.setEdgeWeight(e, grafo.getEdgeWeight(e)+i.getExpressionCorr());
			}}
		}
		double max = 0;
		double min = 99999;
		for (DefaultWeightedEdge edge : grafo.edgeSet()) {
			double w = grafo.getEdgeWeight(edge);
		if(w >max) {
			max = w;
			}
		if(w <min) {
			min = w;
			}
		}
		this.max = max;
		this.min = min;
		System.out.println("N vertici: "+grafo.vertexSet().size()+"\nN archi: "+grafo.edgeSet().size()+"\nMax: "+max+"\nMin: "+min);
		}


	public double getMax() {
		return this.max;
	}


	public double getMin() {
		return this.min;
	}


	public void contaArchi(double s) {
		magS = 0;
		minS = 0;
		for (DefaultWeightedEdge edge : grafo.edgeSet()) {
			double w = grafo.getEdgeWeight(edge);
		if(w >s) {
			this.magS++;
			}
		if(w <s) {
			this.minS++;
			}
		}
		
	}


	public int getMagS() {
		return magS;
	}


	public int getMinS() {
		return minS;
	}


	public List<Integer> calcolaPercorsoMassimo(double s) {
		List<Integer> v = new ArrayList<>();
		List<Integer> p = new ArrayList<>();
		daVisitare  = new ArrayList<>(this.grafo.vertexSet());
		double max = 0;
		return cerca(s,p);
		
	}


	private List<Integer> cerca(double s, List<Integer> p) {
		if(sommaPeso(p)>max) {
			 max = sommaPeso(p);
			 best = new ArrayList<>(p);
		}
		for(Integer i : daVisitare) {
			for (DefaultWeightedEdge vicino : this.grafo.outgoingEdgesOf(i)) {
				if( this.grafo.getEdgeWeight(vicino) > s ) {
					p.add(this.grafo.getEdgeTarget(vicino));
					cerca(s,p);
					p.remove(p.size()-1);
				}
			}
		}
		return best;
		
		
	}


	private double sommaPeso(List<Integer> p) {
		if(p.size() < 0) {
		double max = 0.0;
		for(int i=0;i<p.size()-1;i++) {
			max += this.grafo.getEdgeWeight(grafo.getEdge(p.get(i),p.get(i+1)));
		}
		return max;
		}else return 0;
	}
	
	
}



