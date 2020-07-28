package com.example.polls.model;

import java.math.BigDecimal;
import java.util.Date;

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

import com.example.polls.model.audit.UserDateAudit;

@Entity
@Table(name = "extrato")
public class Extrato extends UserDateAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade=CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade=CascadeType.PERSIST)
    @JoinColumn(name = "cupom_id", nullable = false)
	private Cupom cupom;
	
	@Column(name="valor_comissao")
	private BigDecimal valorComissao;
	
	private int pontos;
	
	@Column(name="pontos_experiencia")
	private int pontosExperiencia;
	
	private Date data;

	private String descricao;

	@Column(name="tipo_extrato")
	private TipoExtrato tipoExtrato;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public int getPontosExperiencia() {
		return pontosExperiencia;
	}

	public void setPontosExperiencia(int pontosExperiencia) {
		this.pontosExperiencia = pontosExperiencia;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Cupom getCupom() {
		return cupom;
	}

	public void setCupom(Cupom cupom) {
		this.cupom = cupom;
	}

	public BigDecimal getValorComissao() {
		return valorComissao;
	}

	public void setValorComissao(BigDecimal valorComissao) {
		this.valorComissao = valorComissao;
	}

	public TipoExtrato getTipoExtrato() {
		return tipoExtrato;
	}

	public void setTipoExtrato(TipoExtrato tipoExtrato) {
		this.tipoExtrato = tipoExtrato;
	}
	
	

}
