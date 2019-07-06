package com.example.polls.payload;


import java.math.BigDecimal;

import com.example.polls.model.CategoriaEmpresa;
import com.example.polls.model.Personagem;
import com.example.polls.model.User;


public class NivelResponse {
	
	private Long id;
	
    private String nome;
    
    private String descricao;
    
    private String missao;
    
    private String resposta;
    
    private String premio;
    
    private int ordemNivel;
    
    private Personagem personagem;

	

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

	public String getMissao() {
		return missao;
	}

	public void setMissao(String missao) {
		this.missao = missao;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public String getPremio() {
		return premio;
	}

	public void setPremio(String premio) {
		this.premio = premio;
	}

	public int getOrdemNivel() {
		return ordemNivel;
	}

	public void setOrdemNivel(int ordemNivel) {
		this.ordemNivel = ordemNivel;
	}

	public Personagem getPersonagem() {
		return personagem;
	}

	public void setPersonagem(Personagem personagem) {
		this.personagem = personagem;
	}

}
