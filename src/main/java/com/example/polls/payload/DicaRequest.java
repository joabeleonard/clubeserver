package com.example.polls.payload;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public class DicaRequest {
	
	private Long id;
	
    private Long nivelId;

    private String local;
    
    private String tempoDeLocomocao;
    
    private String quemEstaComADica;

    private String dica;

    
    private int ordemDica;
    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNivelId() {
		return nivelId;
	}

	public void setNivelId(Long nivelId) {
		this.nivelId = nivelId;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getTempoDeLocomocao() {
		return tempoDeLocomocao;
	}

	public void setTempoDeLocomocao(String tempoDeLocomocao) {
		this.tempoDeLocomocao = tempoDeLocomocao;
	}

	public String getQuemEstaComADica() {
		return quemEstaComADica;
	}

	public void setQuemEstaComADica(String quemEstaComADica) {
		this.quemEstaComADica = quemEstaComADica;
	}

	public String getDica() {
		return dica;
	}

	public void setDica(String dica) {
		this.dica = dica;
	}

	public int getOrdemDica() {
		return ordemDica;
	}

	public void setOrdemDica(int ordemDica) {
		this.ordemDica = ordemDica;
	}

}
