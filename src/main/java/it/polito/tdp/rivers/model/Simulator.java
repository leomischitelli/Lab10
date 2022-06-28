package it.polito.tdp.rivers.model;

import java.util.List;
import java.util.SplittableRandom;

public class Simulator {

	
	//parametri di simulazione
	private double flowMin;
	private double capienzaMassima; //q
	private SplittableRandom random;
	//output di simulazione
	private int numeroFallimenti;
	private double avgCapienza;
	//stato del mondo
	private double livelloAttuale;

	public void init(long k, River river) {

		this.capienzaMassima = (int)( k * 30 * 86400 * river.getFlowAvg()); 
		this.livelloAttuale = this.capienzaMassima / 2;
		this.flowMin = 0.8 * river.getFlowAvg() * 86400;
		this.random = new SplittableRandom();
		this.avgCapienza = 0;
		this.numeroFallimenti = 0;
			
		simula(river.getFlows());
	}
	
	private void simula(List<Flow> flows) {
		for(Flow f : flows) {
			
			double fIn = f.getFlow() * 86400;
			double fOut;
			
			if(random.nextInt(20) == 0) { //randomizzazione, 5% di probabilita
				fOut = 10 * flowMin;
			}
			else {
				fOut = flowMin;
			}
			
			double risultato = this.livelloAttuale + fIn - fOut;
			
			if(risultato < 0) { //erogazione non garantita
				this.livelloAttuale = 0;
				this.numeroFallimenti++;
			}
			
			else if(risultato > this.capienzaMassima) { //tracimazione
				this.livelloAttuale = this.capienzaMassima;
			}
			
			else {
				this.livelloAttuale = risultato;
			}
			
			this.avgCapienza += this.livelloAttuale; //sommo in tutti i passaggi, divido fuori dal ciclo
					
		}
		
		this.avgCapienza /= flows.size();		
		
		
	}

	public int getNumeroFallimenti() {
		return numeroFallimenti;
	}

	public double getAvgCapienza() {
		return avgCapienza;
	}
	
	


}
