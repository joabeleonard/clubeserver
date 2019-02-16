package com.example.polls.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rajeevkumarsingh on 07/12/17.
 */
public enum CategoriaEmpresa {
	ALIMENTACAO("ALIMENTACAO","Alimentacao"), 
	SERVICO_AUTOMATIVO("SERVICO_AUTOMATIVO","Serviço Automativo"), 
	EDUCACAO("EDUCACAO","Educação"), 
	TURISMO("TURISMO","Turismo"), 
	SERVICOS("SERVICOS","Serviços"),
	SUPERMERCADO("SUPERMERCADO","Supermercado"),
	ELETRODOMESTICOS("ELETRODOMESTICOS","Eletrodomesticos"), 
	OUTROS("OUTROS","Outros");
	
	 private static final Map<String, String> MAP = new HashMap<String, String>();
	    static {
	        for (CategoriaEmpresa s : CategoriaEmpresa.values()) {
	            MAP.put(s.code, s.descricao);
	        }
	    }
	
    private final String code;

	private	String descricao;
    
    CategoriaEmpresa(String code, String descricao){
    	 this.code = code;
    	this.descricao = descricao;
    }
	public String getDescricao() {
		return descricao;
	}
	
	 public String getCode() {                                                                                                       
	        return code;                                                                                                                
	    } 
}
