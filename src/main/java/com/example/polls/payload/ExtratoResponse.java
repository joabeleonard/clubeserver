package com.example.polls.payload;


import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import com.example.polls.model.StatusCupom;
import com.example.polls.model.TipoExtrato;
import com.example.polls.model.User;


public class ExtratoResponse {
	
	private Long id;
	
	private String nomeCliente;
	
	private String nomeRepresentante;
		
	private TipoExtrato tipoExtrato;
	
	private Date data;
	
	private BigDecimal valor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getNomeRepresentante() {
		return nomeRepresentante;
	}

	public void setNomeRepresentante(String nomeRepresentante) {
		this.nomeRepresentante = nomeRepresentante;
	}

	public TipoExtrato getTipoExtrato() {
		return tipoExtrato;
	}

	public void setTipoExtrato(TipoExtrato tipoExtrato) {
		this.tipoExtrato = tipoExtrato;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	
}
