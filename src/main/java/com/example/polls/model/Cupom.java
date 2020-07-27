package com.example.polls.model;

import java.io.Serializable;
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
import javax.persistence.UniqueConstraint;

import com.example.polls.model.audit.UserDateAudit;

@Entity
@Table(name = "cupom", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "codigo"
            })}
)
public class Cupom extends UserDateAudit implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade=CascadeType.PERSIST)
    @JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade=CascadeType.PERSIST)
    @JoinColumn(name = "empresa_id", nullable = false)
	private Empresa empresa;
	
	private String codigo;
	
	private StatusCupom statusCupom;
	
	private Date dataGeracao;

	private Date dataConsumo;
	
	private BigDecimal valorCupom;
	
	@Column(name="nota_avaliacao")
	private int notaAvaliacao;
	
	private String descricaoAvaliacao;
	
	private String nomeArquivoComprovante;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public StatusCupom getStatusCupom() {
		return statusCupom;
	}

	public void setStatusCupom(StatusCupom statusCupom) {
		this.statusCupom = statusCupom;
	}

	public Date getDataGeracao() {
		return dataGeracao;
	}

	public void setDataGeracao(Date dataGeracao) {
		this.dataGeracao = dataGeracao;
	}

	public Date getDataConsumo() {
		return dataConsumo;
	}

	public void setDataConsumo(Date dataConsumo) {
		this.dataConsumo = dataConsumo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getValorCupom() {
		return valorCupom;
	}

	public void setValorCupom(BigDecimal valorCupom) {
		this.valorCupom = valorCupom;
	}

	public int getNotaAvaliacao() {
		return notaAvaliacao;
	}

	public void setNotaAvaliacao(int notaAvaliacao) {
		this.notaAvaliacao = notaAvaliacao;
	}

	public String getDescricaoAvaliacao() {
		return descricaoAvaliacao;
	}

	public void setDescricaoAvaliacao(String descricaoAvaliacao) {
		this.descricaoAvaliacao = descricaoAvaliacao;
	}

	public String getNomeArquivoComprovante() {
		return nomeArquivoComprovante;
	}

	public void setNomeArquivoComprovante(String nomeArquivoComprovante) {
		this.nomeArquivoComprovante = nomeArquivoComprovante;
	}
	
}
