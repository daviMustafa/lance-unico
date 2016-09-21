package br.com.triadworks.lanceunico.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.triadworks.lanceunico.modelo.Cliente;
import br.com.triadworks.lanceunico.util.JPAUtil;

public class ClienteDaoTest {

	private EntityManager entityManager;

	@Before
	public void setUp() {
		entityManager = new JPAUtil().getEntityManager();
		entityManager.getTransaction().begin();
	}
	
	@After
	public void tearDown(){
		entityManager.getTransaction().rollback(); // commita a transacao
		entityManager.close();
	}
	
	@Test
	public void deveEncontrarClientePorEmail() {
		// passo 1: cenario
		Cliente cliente = new Cliente("Principe do Oceano", "principe@oceano.com");
		entityManager.persist(cliente);

		// passo 2: açao
		ClienteDao dao = new ClienteDao(entityManager);
		Cliente clienteDoBanco = dao.buscaPorEmail("principe@oceano.com");

		// passo 3: validacao
		assertEquals(cliente.getNome(), clienteDoBanco.getNome());
		assertEquals(cliente.getEmail(), clienteDoBanco.getEmail());

	}

	@Test
	public void naoDeveEncontrarClientePorEmail() {
		// passo 1: cenario
		Cliente cliente = new Cliente("Principe do Oceano", "principe@oceano.com");
		entityManager.persist(cliente);

		// passo 2: açao
		ClienteDao dao = new ClienteDao(entityManager);
		Cliente clienteDoBanco = dao.buscaPorEmail("hue@oceano.com");

		// passo 3: validacao
		assertNull(clienteDoBanco);

	}

}
