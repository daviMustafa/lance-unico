package br.com.triadworks.lanceunico.controller;

import br.com.triadworks.lanceunico.controller.util.FacesUtils;
import br.com.triadworks.lanceunico.dao.ClienteDao;
import br.com.triadworks.lanceunico.modelo.Cliente;
import br.com.triadworks.lanceunico.util.Transacional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class ClientesBean {

	private Cliente cliente = new Cliente();
	private List<Cliente> clientes = new ArrayList<>();
	
	@Inject
	private ClienteDao dao;
	@Inject
	private FacesUtils facesUtils;
	
	@PostConstruct
	public void init() {
		this.clientes = this.dao.lista();
	}
	
	/**
	 * Lista todos os clientes
	 */
	public void lista() {
		this.clientes = dao.lista();
	}
	
	/**
	 * Grava novo cliente no banco
	 */
	@Transacional
	public String salva() {
		dao.salva(this.cliente);
		facesUtils.info("Cliente criado com sucesso!");
		return "lista?faces-redirect=true";
	}
	
	/**
	 * Remove cliente do banco
	 */
	@Transacional
	public String remove(Cliente cliente) {
		dao.remove(cliente);
		facesUtils.info("Cliente removido com sucesso!");
		return "lista?faces-redirect=true"; 
	}
	
	/**
	 * Carrega cliente para edição
	 */
	public String edita(Integer id) {
		this.cliente = dao.carrega(id);
		return null;
	}
	
	/**
	 * Atualiza dados do cliente no banco
	 */
	@Transacional
	public String atualiza() {
		dao.atualiza(this.cliente);
		facesUtils.info("Cliente atualizado com sucesso!");
		return "lista?faces-redirect=true";
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	public List<Cliente> getClientes() {
		return clientes;
	}
	
}
