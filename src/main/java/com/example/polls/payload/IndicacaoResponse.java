package com.example.polls.payload;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import com.example.polls.model.StatusCupom;
import com.example.polls.model.TipoExtrato;
import com.example.polls.model.User;


public class IndicacaoResponse {
	
	private Long id;
	
	private boolean situacao;
	
	private Instant date;
		
	private String nomeIndicado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean getSituacao() {
		return situacao;
	}

	public void setSituacao(boolean situacao) {
		this.situacao = situacao;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public String getNomeIndicado() {
		return nomeIndicado;
	}

	public void setNomeIndicado(String nomeIndicado) {
		this.nomeIndicado = nomeIndicado;
	}

}
