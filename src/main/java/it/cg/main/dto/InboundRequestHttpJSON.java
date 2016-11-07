package it.cg.main.dto;

import java.io.Serializable;

public class InboundRequestHttpJSON  implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	public InboundRequestHttpJSON() { }
	
	public InboundRequestHttpJSON(String testo, Integer numero)
	{
		this.testo=testo;
		this.numero=numero;
	}
	
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
