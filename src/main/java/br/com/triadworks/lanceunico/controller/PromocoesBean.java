package br.com.triadworks.lanceunico.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.triadworks.lanceunico.controller.util.FacesUtils;
import br.com.triadworks.lanceunico.dao.ClienteDao;
import br.com.triadworks.lanceunico.dao.PromocaoDao;
import br.com.triadworks.lanceunico.modelo.Cliente;
import br.com.triadworks.lanceunico.modelo.Lance;
import br.com.triadworks.lanceunico.modelo.Promocao;
import br.com.triadworks.lanceunico.util.Transacional;

@Named
@ViewScoped
public class PromocoesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Promocao promocao = new Promocao();
	private List<Promocao> promocoes = new ArrayList<>();
	
	private Lance lance;
	private List<Cliente> clientes;
	
	@Inject
	private transient PromocaoDao dao;
	@Inject
	private transient FacesUtils facesUtils;
	
	@Inject
	private transient ClienteDao clienteDao;
	
	@PostConstruct
	public void init() {
		this.promocoes = this.dao.lista();
		this.resetaLance();
	}
	
	/**
	 * Lista todos os clientes
	 */
	public void lista() {
		this.promocoes = dao.lista();
	}
	
	/**
	 * Grava nova promoção no banco
	 */
	@Transacional
	public String salva() {
		this.promocao.setData(new Date());
		dao.salva(this.promocao);
		facesUtils.info("Promoção criada com sucesso!");
		return "lista?faces-redirect=true";
	}
	
	/**
	 * Carrega promoção para edição
	 */
	public String gerenciar(Integer id) {
		this.promocao = dao.carrega(id);
		return null;
	}
	
	/**
	 * Registra novo lance na promoção
	 */
	@Transacional
	public void registra() {
		try {
			lance.setId(null);
			lance.setData(new Date());
			
			Integer id = promocao.getId();
			dao.registraLance(id, lance);
			facesUtils.info("Lance registrado com sucesso!");
			
			resetaLance();
			this.promocao = dao.carrega(id);
			
		} catch (Exception e) {
			facesUtils.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	private void resetaLance() {
		this.lance = new Lance();
		this.lance.setCliente(new Cliente());
	}
	
	public Promocao getPromocao() {
		return promocao;
	}
	public List<Promocao> getPromocoes() {
		return promocoes;
	}
	public Lance getLance() {
		return lance;
	}
	public List<Cliente> getClientes() {
		if (this.clientes == null) {
			this.clientes = this.clienteDao.lista();
		}
		return this.clientes;
	}
	
}
