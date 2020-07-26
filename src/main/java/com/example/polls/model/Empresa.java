package com.example.polls.model;

import java.math.BigDecimal;

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
@Table(name = "empresa")
public class Empresa extends UserDateAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade=CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	private String razaoSocial;
	
	private String cnpj;

	private String logo;
		
	private BigDecimal desconto;
	
	private BigDecimal comissao;
//abc
	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "endereco_id", nullable = false)
	private Endereco endereco;

	private String detalhes;

	private String telefone;

	private CategoriaEmpresa categoriaEmpresa;
	
	private String url;

	private boolean representavel;

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

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}
	
	
	public BigDecimal getComissao() {
		return comissao;
	}

	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public CategoriaEmpresa getCategoriaEmpresa() {
		return categoriaEmpresa;
	}

	public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa) {
		this.categoriaEmpresa = categoriaEmpresa;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isRepresentavel() {
		return representavel;
	}

	public void setRepresentavel(boolean representavel) {
		this.representavel = representavel;
	}

}
