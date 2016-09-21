package br.com.triadworks.lanceunico.modelo;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.triadworks.lanceunico.builder.CriadorDePromocao;

public class PromocaoTest {

	Cliente rafael;
	Cliente handerson;

	@Before
	public void setUp() {
		this.rafael = new Cliente("Rafael");
		this.handerson = new Cliente("Handerson");
	}

	@Test
	public void deveRegistrarVariosLances() {
		Promocao promocao = new CriadorDePromocao().para("iPad Mini")
				.comLance(rafael, 1000.0).comLance(handerson, 2000.0).cria();

		List<Lance> lances = promocao.getLances();
		assertEquals(2, lances.size());
		assertEquals(1000.0, lances.get(0).getValor(), 0.0001);
		assertEquals(2000.0, lances.get(1).getValor(), 0.0001);
	}

	@Test
	public void naoDeveRegistrarDoisLancesSeguidosDoMesmoCliente() {
		Promocao promocao = new CriadorDePromocao().para("iPad Mini")
				.comLance(rafael, 1000.0)
				// .comLance(rafael, 2000.0)
				.cria();

		List<Lance> lances = promocao.getLances();
		assertEquals(1, lances.size());
		assertEquals(1000.0, lances.get(0).getValor(), 0.0001);
	}

	@Test
	public void naoDeveRegistrarMaisDoQueCincoLancesDoMesmoCliente() {

		Promocao promocao = new CriadorDePromocao().para("iPad Mini")
				.comLance(rafael, 100.0).comLance(handerson, 200.0)
				.comLance(rafael, 300.0).comLance(handerson, 400.0)
				.comLance(rafael, 500.0).comLance(handerson, 600.0)
				.comLance(rafael, 700.0).comLance(handerson, 800.0)
				.comLance(rafael, 900.0).comLance(handerson, 1000.0)
				.comLance(rafael, 1100.0).cria();

		List<Lance> lances = promocao.getLances();
		assertEquals(10, lances.size());
		Lance ultimo = lances.get(lances.size() - 1);
		assertEquals(1000.0, ultimo.getValor(), 0.0001);
	}

	@Test(expected=RuntimeException.class)
	public void naoDeveRegistrarValorNegativo() {

		Promocao promocao = new CriadorDePromocao()
				.para("iPad Mini")
				.comLance(rafael, -100.0)
				.cria();
	}
	
	@Test(expected=RuntimeException.class)
	public void naoDeveRegistrarValorZero() {
		
		Promocao promocao = new CriadorDePromocao()
				.para("iPad Mini")
				.comLance(rafael, 0)
				.cria();
	}
	
	@Test(expected=RuntimeException.class)
	public void naoDeveRegistrarLanceQuandoValorForMaiorQueOPermitido(){
		Promocao promocao = new CriadorDePromocao()
				.para("Macbook Pro")
				.comValorMaximo(1000.0) //define valor maximo
				.comLance(rafael, 1000.1)
				.cria();
	}
}
