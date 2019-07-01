package com.example.polls.payload;


import java.math.BigDecimal;

import com.example.polls.model.CategoriaEmpresa;
import com.example.polls.model.User;


public class PersonagemResponse {
private Long id;
	
	private String nome;
	
	private String descricao;
	
	private String hobbie;
	
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getHobbie() {
		return hobbie;
	}

	public void setHobbie(String hobbie) {
		this.hobbie = hobbie;
	}

}
