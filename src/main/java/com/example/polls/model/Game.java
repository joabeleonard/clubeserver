package com.example.polls.model;

import java.util.Date;

import javax.persistence.CascadeType;
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
@Table(name = "game"
)
public class Game extends UserDateAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade=CascadeType.PERSIST)
    @JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade=CascadeType.PERSIST)
    @JoinColumn(name = "dica_game_id", nullable = false)
	private DicasGames dicasGames;
	
	private Date dataInicioDicaAtual;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public DicasGames getDicasGames() {
		return dicasGames;
	}

	public void setDicasGames(DicasGames dicasGames) {
		this.dicasGames = dicasGames;
	}

	public Date getDataInicioDicaAtual() {
		return dataInicioDicaAtual;
	}

	public void setDataInicioDicaAtual(Date dataInicioDicaAtual) {
		this.dataInicioDicaAtual = dataInicioDicaAtual;
	}

}
