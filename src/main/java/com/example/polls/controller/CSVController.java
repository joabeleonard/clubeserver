package com.example.polls.controller;

import java.io.IOException;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.example.polls.model.Cliente;
import com.example.polls.repository.ClientRepository;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;

@RestController
@RequestMapping("/api/arquivos")
public class CSVController {
   
	
	Cliente cliente;
	
	@Autowired
	ClientRepository clientRepository;
	
	@GetMapping("/arquivos")
	public void exportarCSV(HttpServletResponse response) throws IOException {

		response.setContentType("text/csv");
		String nomeArquivo = "clientes.csv";

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; nomeArquivo=" + nomeArquivo;

		response.setHeader(headerKey, headerValue);

		List<Cliente> listaClientes = clientRepository.findAll();

		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

		String[] cabecalhos = { "ID_usuario", "data_criacao", "data_atualizacao", "criado_por", "atualizado_por",
				"ativo", "codigo_indicacao", "cpf", "data_nascimento", "pontos", "pontos_experiencia", "rg", "sexo",
				"telefone", "endereco_id", "user_id", "representante", "id_pagamento", "id_recorrente", "id_empresa" };

		String[] atributos = { "id", "created_at", "updated_at", "created_by", "updated_by", "ativo",
				"codigo_indicacao", "cpf", "data_nascimento", "pontos", "pontos_experiencia", "rg", "sexo", "telefone",
				"endereco_id", "user_id", "representante", "payment_id", "recurrent_id", "empresa_id" };

		csvWriter.writeHeader(cabecalhos);

		for (Cliente cliente : listaClientes) {
			csvWriter.write(cliente, atributos);
		}

		csvWriter.close();

	}
}