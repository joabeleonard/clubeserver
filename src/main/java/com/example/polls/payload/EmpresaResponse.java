package com.example.polls.payload;


import java.math.BigDecimal;

import com.example.polls.model.CategoriaEmpresa;
import com.example.polls.model.User;


public class EmpresaResponse {
private Long id;
	
	private User user;
	
	private String detalhes;
	
	private BigDecimal desconto;
	
	private String logo;
	
	private CategoriaEmpresa categoriaEmpresa;
	

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

	
}
