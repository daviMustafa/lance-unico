package br.com.triadworks.lanceunico.controller.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.triadworks.lanceunico.modelo.Cupom;

@FacesConverter(forClass=Cupom.class)
public class CupomConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
		if (value == null || value.isEmpty())
			return null;
		return new Cupom(value);
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent component, Object value) {
		if (value == null)
			return null;
		return ((Cupom) value).getNumero();
	}

}
