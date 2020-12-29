package com.example.polls.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.example.polls.model.Cliente;
import com.example.polls.repository.ClientRepository;

@RestController
@RequestMapping("/api/arquivos")
public class CSVController {
   
	
	Cliente cliente;
	
	@Autowired
	ClientRepository clientRepository;
	
	@GetMapping("/arquivos")
	public void exportarCSV(HttpServletResponse response) throws IOException {

		response.setContentType("text/csv");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String nomeArquivo = "clientes" + currentDateTime  + ".csv";

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; nomeArquivo=" + nomeArquivo;

		response.setHeader(headerKey, headerValue);

		List<Cliente> listaClientes = clientRepository.findAll(Sort.by("id").ascending());

		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

		String[] cabecalhos = {"ID_usuario", "cpf", "data_nascimento"};

		String[] atributos = {"id", "cpf", "dataNascimento"};

		csvWriter.writeHeader(cabecalhos);

		for (Cliente cliente : listaClientes) {
			csvWriter.write(cliente, atributos);
		}

		csvWriter.close();

	}
}
