package br.com.triadworks.lanceunico.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.triadworks.lanceunico.modelo.Lance;
import br.com.triadworks.lanceunico.modelo.Promocao;

public class Sorteio {
	
	private double maiorDeTodos = Double.NEGATIVE_INFINITY;
	private double menorDeTodos = Double.POSITIVE_INFINITY;
	List<Lance> menores;

	public void sorteia(Promocao promocao){
		this.verificaSeNaoExisteLances(promocao);
		this.procurarMaiorMenor(promocao);
		this.procurarTresMenores(promocao);
	}

	private void verificaSeNaoExisteLances(Promocao promocao) {
		if(promocao.getLances().isEmpty()){
			throw new RuntimeException();
		}
	}

	private void procurarMaiorMenor(Promocao promocao) {
		for(Lance lance : promocao.getLances()){
			if(lance.getValor() > maiorDeTodos){
				maiorDeTodos = lance.getValor();
			} 
			if (lance.getValor() < menorDeTodos){
				menorDeTodos = lance.getValor();
			}
		}
	}

	private void procurarTresMenores(Promocao promocao) {
		menores = new ArrayList<Lance>(promocao.getLances());
		
		Collections.sort(menores, new Comparator<Lance>() {
			@Override
			public int compare(Lance o1, Lance o2) {
				if(o1.getValor() < o2.getValor()) return -1;
				if(o1.getValor() > o2.getValor()) return 1;
				return 0;
			}
		});
		int tamanho = menores.size() > 3 ? 3 : menores.size();
		this.menores = menores.subList(0, tamanho);
	}
	
	public double getMenorDeTodos() {
		return menorDeTodos;
	}

	public double getMaiorDeTodos() {
		return maiorDeTodos;
	}

	public List<Lance> getMenores() {
		return menores;
	}
}
