package com.example.polls.payload;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public class NivelRequest {
	
	private Long id;
	
    private String detalhes;
    
    @NotBlank
    @Size(max = 140)
    private String nome;
    
    private BigDecimal desconto;
    
    private String categoriaEmpresa;
    
    @NotBlank
    @Size(max = 140)
    private String email;
    
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

}
