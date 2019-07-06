package com.example.polls.payload;


import java.math.BigDecimal;

import com.example.polls.model.CategoriaEmpresa;
import com.example.polls.model.NivelGame;
import com.example.polls.model.User;


public class DicaResponse {

	private Long id;
	
	private String local;
	
	private String tempoDeLocomocao;
	
	private String quemEstaComADica;
	
	private String dica;
	
	private int ordemDica;

    private NivelGame nivelGame;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public NivelGame getNivelGame() {
		return nivelGame;
	}

	public void setNivelGame(NivelGame nivelGame) {
		this.nivelGame = nivelGame;
	}
		
	
}
