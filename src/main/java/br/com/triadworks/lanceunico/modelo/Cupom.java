package br.com.triadworks.lanceunico.modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Cupom implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String numero;
	
	protected Cupom(){}

	public Cupom(String numero) {
		this.numero = numero;
	}
	
	public String getNumero() {
		return numero;
	}
}
