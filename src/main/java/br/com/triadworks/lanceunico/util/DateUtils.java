package br.com.triadworks.lanceunico.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jboss.weld.exceptions.IllegalArgumentException;

public class DateUtils {

	private static final int UM_DIA_EM_MILISSEGUNDOS = 1000 * 60 * 60 * 24;

	/**
	 * Retorna diferença entre duas datas em dias
	 */
	public static int diferencaEmDias(Date inicio, Date fim) {
		long diferenca = fim.getTime() - inicio.getTime();
		return (int) (diferenca / UM_DIA_EM_MILISSEGUNDOS);
	}

	/**
	 * Cria nova <code>Date</code> a partir de uma data no formato
	 * "dd/MM/yyyy"
	 */
	public static Date novaData(String valor) {
		try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(valor);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
}
