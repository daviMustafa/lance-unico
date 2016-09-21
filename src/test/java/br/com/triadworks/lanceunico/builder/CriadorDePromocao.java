package br.com.triadworks.lanceunico.builder;

import java.util.Date;

import br.com.triadworks.lanceunico.modelo.Cliente;
import br.com.triadworks.lanceunico.modelo.Lance;
import br.com.triadworks.lanceunico.modelo.Promocao;
import br.com.triadworks.lanceunico.modelo.Status;

public class CriadorDePromocao {
	
	private Promocao promocao;
	
	public CriadorDePromocao para(String nome){
		promocao = new Promocao(nome);
		promocao.setValorMaximo(10000.0);
		return this;
	}
	
	public CriadorDePromocao comLance(Cliente cliente, double valor){
		promocao.registra(new Lance(cliente, valor));
		return this;
	}
	
	public CriadorDePromocao naData(Date data){
		promocao.setData(data);
		return this;
	}
	
	public Promocao cria(){
		return promocao;
	}

	public CriadorDePromocao comValorMaximo(double valorMaximo) {
		promocao.setValorMaximo(valorMaximo);
		return this;
	}

	public CriadorDePromocao comStatus(Status status) {
		promocao.setStatus(status);
		return this;
	}
}
