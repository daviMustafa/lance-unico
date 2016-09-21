package br.com.triadworks.lanceunico.dao;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.triadworks.lanceunico.builder.CriadorDePromocao;
import br.com.triadworks.lanceunico.modelo.Cliente;
import br.com.triadworks.lanceunico.modelo.Lance;
import br.com.triadworks.lanceunico.modelo.Promocao;
import br.com.triadworks.lanceunico.modelo.Status;
import br.com.triadworks.lanceunico.util.JPAUtil;

public class PromocaoDaoTest {

	private EntityManager entityManager;

	@Before
	public void setUp() {
		entityManager = new JPAUtil().getEntityManager();
		entityManager.getTransaction().begin();
	}

	@After
	public void tearDown() {
		entityManager.getTransaction().rollback(); // desfaz a transação
		entityManager.close();
	}

	@Test
	public void deveContarPromocoesEncerradas() {
		// passo 1: cenario
		Promocao aberta = new CriadorDePromocao()
				.para("Notebook DELL").comStatus(Status.ABERTA).cria();
		Promocao encerrada = new CriadorDePromocao()
				.para("TV LED 32").comStatus(Status.ENCERRADA).cria();

		entityManager.persist(aberta);
		entityManager.persist(encerrada);

		// passo 2: ação
		PromocaoDao dao = new PromocaoDao(entityManager);
		Long total = dao.totalDeEncerradas();

		// passo 3: validação
		Long totalEsperado = 1L;
		assertEquals(totalEsperado, total);
	}
	
	@Test
	public void deveRemoverUmaPromocao(){
		// passo 1: cenario
		Promocao promocao = new CriadorDePromocao().para("Netflix por 3 anos").cria();
		entityManager.persist(promocao);
		
		// passo 2: ação
		PromocaoDao dao = new PromocaoDao(entityManager);
		dao.remove(promocao);
		
		// passo 3: validacao
		Promocao promocaoDoBanco = dao.carrega(promocao.getId());
		assertNull(promocaoDoBanco);
	}
	
	@Test
	public void deveRegistrarNovoLanceNaPromocao(){
		//passo 1 : cenario
		Cliente rafael = new Cliente("Rafael");
		Promocao promocao = new CriadorDePromocao().para("Apple TV").cria();
		
		entityManager.persist(rafael);
		entityManager.persist(promocao);
		
		// passo 2: ação
		Integer id = promocao.getId();
		Lance lance = new Lance(rafael, 100.0);
		
		PromocaoDao dao = new PromocaoDao(entityManager);
		dao.registraLance(id, lance);
		
		//passo 3 : validação
		Promocao promocaoDoBanco = dao.carrega(id);
		assertEquals(1, promocaoDoBanco.getLances().size());
	}
	
}
