package com.example.polls.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.polls.model.audit.UserDateAudit;

import java.util.Objects;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */

@Entity
@Table(name = "dicas_games")
public class DicasGames extends UserDateAudit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String local;
    
    @NotBlank
    private String tempoDeLocomocao;
    
    @NotBlank
    private String quemEstaComADica;
    
    @NotBlank
    private String dica;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nivel_game_id", nullable = false)
    private NivelGame nivelGame;
    
    @NotBlank
    private Integer ordemDica;

    public DicasGames() {

    }

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

	public NivelGame getNivelGame() {
		return nivelGame;
	}

	public void setNivelGame(NivelGame nivelGame) {
		this.nivelGame = nivelGame;
	}
	
	public Integer getOrdemDica() {
		return ordemDica;
	}

	public void setOrdemDica(Integer ordemDica) {
		this.ordemDica = ordemDica;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dica == null) ? 0 : dica.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((local == null) ? 0 : local.hashCode());
		result = prime * result + ((nivelGame == null) ? 0 : nivelGame.hashCode());
		result = prime * result + ((quemEstaComADica == null) ? 0 : quemEstaComADica.hashCode());
		result = prime * result + ((tempoDeLocomocao == null) ? 0 : tempoDeLocomocao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DicasGames other = (DicasGames) obj;
		if (dica == null) {
			if (other.dica != null)
				return false;
		} else if (!dica.equals(other.dica))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (local == null) {
			if (other.local != null)
				return false;
		} else if (!local.equals(other.local))
			return false;
		if (nivelGame == null) {
			if (other.nivelGame != null)
				return false;
		} else if (!nivelGame.equals(other.nivelGame))
			return false;
		if (quemEstaComADica == null) {
			if (other.quemEstaComADica != null)
				return false;
		} else if (!quemEstaComADica.equals(other.quemEstaComADica))
			return false;
		if (tempoDeLocomocao == null) {
			if (other.tempoDeLocomocao != null)
				return false;
		} else if (!tempoDeLocomocao.equals(other.tempoDeLocomocao))
			return false;
		return true;
	}

}
