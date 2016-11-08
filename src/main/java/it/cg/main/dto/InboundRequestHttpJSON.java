package it.cg.main.dto;

import java.io.Serializable;

import it.cg.main.conf.error.DtoImpl;

public class InboundRequestHttpJSON extends DtoImpl implements Serializable
{
	private static final long serialVersionUID = -4058572266373539270L;

	private String testo;
	private Integer numero;
	
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
}
