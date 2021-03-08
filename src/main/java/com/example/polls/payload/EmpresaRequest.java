package com.example.polls.payload;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public class EmpresaRequest {
	
	private Long id;
	
    private String detalhes;
    
    @NotBlank
    @Size(max = 140)
    private String nome;
    
    private String razaoSocial;
    
	private BigDecimal dotBack;
	private boolean revendedor;
	
    private BigDecimal desconto;
    
    private BigDecimal comissao;
    
    private String categoriaEmpresa;
    
    @NotBlank
    @Size(max = 140)
    private String email;
    
	private String url;
	
	private String cnpj;
	@NotBlank
    @Size(max = 140)
    private String logradouro;
    private String cep;
    private String bairro;
    private String numero;
    private String cidade;
    private String estado;
    private String complemento;

	private boolean representavel;
	private boolean instituicaoSocial;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	
	public BigDecimal getComissao() {
		return comissao;
	}

	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCategoriaEmpresa() {
		return categoriaEmpresa;
	}

	public void setCategoriaEmpresa(String categoriaEmpresa) {
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

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
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

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public boolean isInstituicaoSocial() {
		return instituicaoSocial;
	}

	public void setInstituicaoSocial(boolean instituicaoSocial) {
		this.instituicaoSocial = instituicaoSocial;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public BigDecimal getDotBack() {
		return dotBack;
	}

	public void setDotBack(BigDecimal dotBack) {
		this.dotBack = dotBack;
	}

	public boolean isRevendedor() {
		return revendedor;
	}

	public void setRevendedor(boolean revendedor) {
		this.revendedor = revendedor;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	
}
