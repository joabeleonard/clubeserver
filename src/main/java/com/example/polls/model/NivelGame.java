package com.example.polls.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */

@Entity
@Table(name = "nivel_game")
public class NivelGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String nome;
    
    @NotBlank
    private String descricao;
    
    @NotBlank
    private String missao;
    
    @NotBlank
    private String resposta;
    
    @NotBlank
    private String premio;
    
    @NotBlank
    private Integer ordemNivel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "personagem_id", nullable = false)
    private Personagem personagem;
    
    @OneToMany(
            mappedBy = "nivelGame",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Size(min = 2, max = 6)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 30)
    private List<DicasGames> dicasGames = new ArrayList<>();

    public NivelGame() {

    }

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

	public Personagem getPersonagem() {
		return personagem;
	}

	public void setPersonagem(Personagem personagem) {
		this.personagem = personagem;
	}

	public List<DicasGames> getDicasGames() {
		return dicasGames;
	}

	public void setDicasGames(List<DicasGames> dicasGames) {
		this.dicasGames = dicasGames;
	}

	public Integer getOrdemNivel() {
		return ordemNivel;
	}

	public void setOrdemNivel(Integer ordemNivel) {
		this.ordemNivel = ordemNivel;
	}

}
