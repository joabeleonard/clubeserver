package com.example.polls.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="indicacao")
public class Indicacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cliente_indicado", nullable = false)
	public Cliente indicado;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cliente_indicou", nullable = false)
	public Cliente indicou;
	
	public boolean pagou;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getIndicado() {
		return indicado;
	}

	public void setIndicado(Cliente indicado) {
		this.indicado = indicado;
	}

	public Cliente getIndicou() {
		return indicou;
	}

	public void setIndicou(Cliente indicou) {
		this.indicou = indicou;
	}

	public boolean isPagou() {
		return pagou;
	}

	public void setPagou(boolean pagou) {
		this.pagou = pagou;
	}
	
	
}
