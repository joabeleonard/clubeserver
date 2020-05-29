package com.example.polls.payload;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.polls.model.StatusCupom;
import com.example.polls.model.User;


public class CupomResponse {
	
	private Long id;
	
	private String userName;
	
	private String empresa;
		
	private String status;
	
	private Date dataGeracao;
	
	private String codigo;
	
	private String valorCupom;
	
	private int notaAvaliacao;
	
	private String descricaoAvaliacao;
	
	private String nomeArquivoComprovante;
	
	private Long cliente_id;
	
	private Long empresa_id;
	
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDataGeracao() {
		return dataGeracao;
	}

	public void setDataGeracao(Date dataGeracao) {
		this.dataGeracao = dataGeracao;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getValorCupom() {
		return valorCupom;
	}

	public void setValorCupom(String valorCupom) {
		this.valorCupom = valorCupom;
	}

	public int getNotaAvaliacao() {
		return notaAvaliacao;
	}

	public void setNotaAvaliacao(int notaAvaliacao) {
		this.notaAvaliacao = notaAvaliacao;
	}

	public String getDescricaoAvaliacao() {
		return descricaoAvaliacao;
	}

	public void setDescricaoAvaliacao(String descricaoAvaliacao) {
		this.descricaoAvaliacao = descricaoAvaliacao;
	}

	public String getNomeArquivoComprovante() {
		return nomeArquivoComprovante;
	}

	public void setNomeArquivoComprovante(String nomeArquivoComprovante) {
		this.nomeArquivoComprovante = nomeArquivoComprovante;
	}
	
    public Long getEmpresaId() {
        return empresa_id;
    }

    public void setEmpresaId(Long empresa_id) {
        this.empresa_id = empresa_id;
    }
	
    public Long getClienteId() {
        return cliente_id;
    }

    public void setClienteId(Long cliente_id) {
        this.cliente_id = cliente_id;
    }
}
