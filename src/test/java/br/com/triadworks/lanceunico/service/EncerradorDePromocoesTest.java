package br.com.triadworks.lanceunico.service;

import br.com.triadworks.lanceunico.builder.CriadorDePromocao;
import br.com.triadworks.lanceunico.dao.PromocaoDao;
import br.com.triadworks.lanceunico.modelo.Promocao;
import br.com.triadworks.lanceunico.util.DateUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
public class EncerradorDePromocoesTest {

	@Test
	public void deveEncerrarPromocoesForaDaVigencia() {
		
		Date antiga = DateUtils.novaData("01/01/2016");

		Promocao ps3 = new CriadorDePromocao().para("Playstation").naData(antiga).cria();
		Promocao tv = new CriadorDePromocao().para("TV LED 52").naData(antiga).cria();
		
		List<Promocao> promocoes = Arrays.asList(ps3, tv);
		
		PromocaoDao daoFalso = mock(PromocaoDao.class);
		when(daoFalso.abertas()).thenReturn(promocoes);
		
		EncerradorDePromocoes encerrador = new EncerradorDePromocoes(daoFalso);
		encerrador.encerra();

		assertTrue("promocao 1 encerrada", ps3.isEncerrada());
		assertTrue("promocao 2 encerrada", tv.isEncerrada());
	}
	
	@Test
	public void naoDeveEncerrarPromocoesAindaVigentes(){
		Date ontem = DateUtils.novaData("21/09/2016");
		Date hoje = DateUtils.novaData("22/09/2016");
		Date mesPassado = DateUtils.novaData("22/08/2016");
		
		Promocao ps3 = new CriadorDePromocao().para("Playstation").naData(ontem).cria();
		Promocao tv = new CriadorDePromocao().para("TV").naData(hoje).cria();
		Promocao xbox = new CriadorDePromocao().para("Xbox").naData(mesPassado).cria();
		
		List<Promocao> promocoes = Arrays.asList(ps3, tv, xbox);
		
		PromocaoDao daoFalso = mock(PromocaoDao.class);
		when(daoFalso.abertas()).thenReturn(promocoes);
		
		EncerradorDePromocoes encerrador = new EncerradorDePromocoes(daoFalso);
		encerrador.encerra();	
		
		assertFalse("promocao 1 encerrada", ps3.isEncerrada());
		assertFalse("promocao 2 encerrada", tv.isEncerrada());
		assertTrue("promocao 3 encerrada", xbox.isEncerrada());
		
		assertEquals(1, encerrador.encerra());
		verify(daoFalso, never()).atualiza(ps3);
		verify(daoFalso, never()).atualiza(tv); 
	}
	
	@Test
	public void deveAtualizarPromocoesEncerradas(){
		Date antiga = DateUtils.novaData("01/01/2015");
		
		Promocao ipad = new CriadorDePromocao().para("Ipad").naData(antiga).cria();
		
		List<Promocao> promocoes = Arrays.asList(ipad);
		PromocaoDao daoFalso = mock(PromocaoDao.class);
		when(daoFalso.abertas()).thenReturn(promocoes);
		
		EncerradorDePromocoes encerrador = new EncerradorDePromocoes(daoFalso);
		encerrador.encerra();
		
		verify(daoFalso).atualiza(ipad); // verifica se o método foi invocado
		verify(daoFalso, times(1)).atualiza(ipad);
	}
	
	@Test
	public void deveEncerrarAsPromocoesRestantesMesmoEmCasoDeFalha(){
		Date antiga = DateUtils.novaData("01/01/2015");
		
		Promocao ps3 = new CriadorDePromocao().para("Playstation").naData(antiga).cria();
		Promocao tv = new CriadorDePromocao().para("TV").naData(antiga).cria();
		
		PromocaoDao daoFalso = mock(PromocaoDao.class);
		when(daoFalso.abertas()).thenReturn(Arrays.asList(ps3, tv));
		
		//ensina mock quando lançar exceção
		doThrow(new RuntimeException()).when(daoFalso).atualiza(ps3);
		
		EncerradorDePromocoes encerrador = new EncerradorDePromocoes(daoFalso);
		int encerrados = encerrador.encerra();
		
		verify(daoFalso).atualiza(tv);
		assertEquals("total", 1, encerrados);
	}

}
