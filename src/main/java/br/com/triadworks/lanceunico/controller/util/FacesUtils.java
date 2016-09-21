package br.com.triadworks.lanceunico.controller.util;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

@RequestScoped
public class FacesUtils {
	
	private FacesContext facesContext;

	public FacesUtils() {
		this.facesContext = FacesContext.getCurrentInstance();
	}

	/**
	 * Adiciona mensagem de sucesso na página
	 */
	public void info(String msg) {
		keepMessagesAfterRedirect();
		facesContext.addMessage(null, newMsg(msg, SEVERITY_INFO));
	}

	/**
	 * Adiciona mensagem de erro na página
	 */
	public void error(String msg) {
		keepMessagesAfterRedirect();
		facesContext.addMessage(null, newMsg(msg, SEVERITY_ERROR));
	}

	private FacesMessage newMsg(String msg, Severity severity) {
		FacesMessage message = new FacesMessage(msg);
		message.setSeverity(severity);
		return message;
	}
	
	private void keepMessagesAfterRedirect() {
		facesContext.getExternalContext().getFlash().setKeepMessages(true);
	}

}
