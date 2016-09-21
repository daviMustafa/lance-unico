package br.com.triadworks.lanceunico.service;

import java.util.Date;
import java.util.List;

import br.com.triadworks.lanceunico.dao.PromocaoDao;
import br.com.triadworks.lanceunico.modelo.Promocao;
import br.com.triadworks.lanceunico.modelo.Status;

public class EncerradorDePromocoes {

	private PromocaoDao dao;

	public EncerradorDePromocoes(PromocaoDao dao) {
		this.dao = dao;
	}

	public int encerra() {

		int total = 0;
		List<Promocao> promocoes = dao.abertas();

		for (Promocao promocao : promocoes) {
			try {
				if (promocao.foraDaVigencia(new Date())) {
					promocao.setStatus(Status.ENCERRADA);
					dao.atualiza(promocao);
					total++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return total;
	}
}
