package com.example.polls.payload;

import java.math.BigDecimal;

import com.example.polls.model.CategoriaEmpresa;
import com.example.polls.model.User;
import com.example.polls.model.Endereco;

/**
 * @author marce
 *
 */
public class EmpresaResponse {
	private Long id;

	private User user;

	private String detalhes;

	private Endereco endereco;

	private BigDecimal desconto;

	private String logo;

	private CategoriaEmpresa categoriaEmpresa;

	private String url;

	private boolean representavel;

	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String numero;
	private String cidade;
	private String estado;
	

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

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public CategoriaEmpresa getCategoriaEmpresa() {
		return categoriaEmpresa;
	}

	public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa) {
		this.categoriaEmpresa = categoriaEmpresa;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
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

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	
}
