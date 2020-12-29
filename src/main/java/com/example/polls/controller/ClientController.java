package com.example.polls.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.example.polls.City;
import com.example.polls.GenerateCertificado;
import com.example.polls.model.Cliente;
import com.example.polls.payload.ApiResponse;
import com.example.polls.payload.ClientRequest;
import com.example.polls.payload.ClientResponse;
import com.example.polls.payload.PagamentoRequest;
import com.example.polls.payload.PagedResponse;
import com.example.polls.payload.PollResponse;
import com.example.polls.payload.VoteRequest;
import com.example.polls.repository.ClientRepository;
import com.example.polls.repository.PollRepository;
import com.example.polls.repository.VoteRepository;
import com.example.polls.repository.impl.ClientRepositoryImpl;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.ClientService;
import com.example.polls.service.PagamentoService;
import com.example.polls.service.PollService;
import com.example.polls.util.AppConstants;


@RestController
@RequestMapping("/api/clients")
public class ClientController {

	@Autowired
	private PollRepository pollRepository;

	@Autowired
	private VoteRepository voteRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientRepositoryImpl clientRepositoryImpl;

	@Autowired
	private PollService pollService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private PagamentoService pagamentoService;

	private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

	@GetMapping
	public PagedResponse<ClientResponse> getClients(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
		return clientService.getAllClients(currentUser, page, size);
	}

	@PostMapping
	public ResponseEntity<?> createClient(@Valid @RequestBody ClientRequest clientRequest) {

		Cliente cliente = clientService.createClient(clientRequest);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{clientId}")
				.buildAndExpand(cliente.getId()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "Cliente cadastrado com Sucesso."));
	}

	@PutMapping
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> editClient(@Valid @RequestBody ClientRequest clientRequest) {
		System.out.println("teste" + clientRequest.getEmail());
		Cliente cliente = clientService.editClient(clientRequest);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{clientId}")
				.buildAndExpand(cliente.getId()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "Cliente cadastrado com Sucesso."));
	}

	@GetMapping("/{pollId}")
	public PollResponse getPollById(@CurrentUser UserPrincipal currentUser, @PathVariable Long pollId) {
		return pollService.getPollById(pollId, currentUser);
	}

	@PostMapping("/{pollId}/votes")
	@PreAuthorize("hasRole('USER')")
	public PollResponse castVote(@CurrentUser UserPrincipal currentUser, @PathVariable Long pollId,
			@Valid @RequestBody VoteRequest voteRequest) {
		return pollService.castVoteAndGetUpdatedPoll(pollId, voteRequest, currentUser);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable long id) {
		Cliente cliente = clientRepository.getOne(id);
		clientRepository.delete(cliente);

		JSONObject obj = new JSONObject();
		try {
			obj.put("ok", "ok");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@GetMapping("/username")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Cliente> getClientByUsername(@RequestParam(value = "username") String username) {
		Cliente cliente = clientRepository.findByUsername(username);
		cliente.setEndereco(null);
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);

	}

	@GetMapping("/userid")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Cliente> getClientByUserId(@RequestParam(value = "userid") String userId) {
		Cliente cliente = clientRepository.findByUser(new Long(userId));
		cliente.setEndereco(null);
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);

	}

	@GetMapping("/rankingDot")
	@PreAuthorize("hasRole('USER')")
	public ArrayList<ClientResponse> rankingDot(@CurrentUser UserPrincipal currentUser) {

		Cliente cliente = clientRepository.findByUser(currentUser.getId());

		ArrayList<ClientResponse> clients = clientService.findCincoMelhoresRankingDot(cliente.getPontos());

		clients.addAll(clientService.findCincoPioresRankingDot(cliente.getPontos()));

		if (clients != null) {
			return clients;
		}
		return null;
	}

	@GetMapping("/rankingExperiencia")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> rankingExperiencia(@CurrentUser UserPrincipal currentUser) {

		Cliente cliente = clientRepository.findByUser(currentUser.getId());

		ArrayList<Cliente> clients = clientRepositoryImpl.findCincoMelhoresExperiencia(cliente.getPontosExperiencia());

		clients.addAll(clientRepositoryImpl.findCincoPioresExperiencia(cliente.getPontosExperiencia()));
		if (clients != null) {
			return ResponseEntity.ok(clients);
		}
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/pagamento")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> createPagamento(@CurrentUser UserPrincipal currentUser,
			@Valid @RequestBody PagamentoRequest clientRequest) {

		Cliente cliente = clientRepository.findByUser(currentUser.getId());

		cliente = clientService.createPagamento(clientRequest, cliente);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{clientId}")
				.buildAndExpand(cliente.getId()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "Cliente cadastrado com Sucesso."));
	}

	@RequestMapping(value = "/pdfreport", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	@PreAuthorize("permitAll()")
	public ResponseEntity<InputStreamResource> citiesReport(@RequestParam(value = "clientId") String clientId)
			throws MalformedURLException, IOException {

		Cliente cliente = clientRepository.findById(new Long(clientId)).get();

		ArrayList<City> cities = new ArrayList<>();

		ByteArrayInputStream bis = GenerateCertificado.citiesReport(cliente);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

}

