package com.example.polls.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.polls.exception.AppException;
import com.example.polls.exception.BadRequestException;
import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.Choice;
import com.example.polls.model.ChoiceVoteCount;
import com.example.polls.model.Cliente;
import com.example.polls.model.Endereco;
import com.example.polls.model.Indicacao;
import com.example.polls.model.Poll;
import com.example.polls.model.Role;
import com.example.polls.model.RoleName;
import com.example.polls.model.User;
import com.example.polls.model.Vote;
import com.example.polls.payload.ClientRequest;
import com.example.polls.payload.ClientResponse;
import com.example.polls.payload.PagedResponse;
import com.example.polls.payload.PollResponse;
import com.example.polls.payload.VoteRequest;
import com.example.polls.repository.ClientRepository;
import com.example.polls.repository.IndicacaoRepository;
import com.example.polls.repository.PollRepository;
import com.example.polls.repository.RoleRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.repository.VoteRepository;
import com.example.polls.repository.impl.ClientRepositoryImpl;
import com.example.polls.security.UserPrincipal;
import com.example.polls.util.AppConstants;
import com.example.polls.util.ModelMapper;

@Service
public class ClientService {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private IndicacaoRepository indicacaoRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    RoleRepository roleRepository;
    
    @Autowired
    PagamentoService pagamentoService;
    
    @Autowired
    private ClientRepositoryImpl clientRepositoryImpl;

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    
    public PagedResponse<ClientResponse> getAllClients(UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Cliente> clients = clientRepository.findAll(pageable);

        if(clients.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), clients.getNumber(),
            		clients.getSize(), clients.getTotalElements(), clients.getTotalPages(), clients.isLast());
        }

        List<ClientResponse> clientsResponses = clients.map(client -> {
            return ModelMapper.mapClientToPollResponse(client);
        }).getContent();
        
        return new PagedResponse<>(clientsResponses, clients.getNumber(),
        		clients.getSize(), clients.getTotalElements(), clients.getTotalPages(), clients.isLast());
    }


    public Cliente createClient(ClientRequest clientRequest) {
    	

        Cliente cliente = new Cliente();
        
        User user = new User(clientRequest.getNome(), clientRequest.getEmail(),
        		clientRequest.getEmail(), clientRequest.getSenha());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));
     
        cliente.setUser(user);
        cliente.setAtivo(true);
        cliente.setCpf(clientRequest.getCpf());
        cliente.setSexo(clientRequest.getSexo());
        cliente.setRg(clientRequest.getRg());
        cliente.setTelefone(clientRequest.getTelefone());
       cliente.setDataNascimento(clientRequest.getDataNascimento());
       

       Indicacao indicacao = new Indicacao();
       if (clientRequest.getCodigoIndicacao() != null && !clientRequest.getCodigoIndicacao().equals("") ) {
		indicacao.setIndicou(clientRepository.findByCodigoIndicacao(clientRequest.getCodigoIndicacao()));
		indicacao.setIndicado(cliente);
		indicacao.setPagou(false);
       }
       UUID uuid = UUID.randomUUID();
       String myRandom = uuid.toString();
       cliente.setCodigoIndicacao(myRandom.substring(0,8));
        
        Endereco endereco = new Endereco();
        endereco.setBairro(clientRequest.getBairro());
        endereco.setCep(clientRequest.getCep());
        endereco.setCidade(clientRequest.getCidade());
        endereco.setEstado(clientRequest.getEstado());
        endereco.setNumero(clientRequest.getNumero());
        endereco.setComplemento(clientRequest.getComplemento());
        endereco.setLogradouro(clientRequest.getLogradouro());
        cliente.setEndereco(endereco);
        Cliente clienteSalvo = clientRepository.save(cliente);
        indicacaoRepository.save(indicacao);
        return clienteSalvo;
    }
    
    public Cliente editClient(ClientRequest clientRequest) {
        Cliente cliente = clientRepository.getOne(clientRequest.getId());
        
     
        cliente.setCpf(clientRequest.getCpf());
        cliente.setSexo(clientRequest.getSexo());
        cliente.setRg(clientRequest.getRg());
        cliente.setTelefone(clientRequest.getTelefone());
       
        cliente.setDataNascimento(clientRequest.getDataNascimento());
        
        cliente.getEndereco().setBairro(clientRequest.getBairro());
        cliente.getEndereco().setCep(clientRequest.getCep());
        cliente.getEndereco().setCidade(clientRequest.getCidade());
        cliente.getEndereco().setEstado(clientRequest.getEstado());
        cliente.getEndereco().setNumero(clientRequest.getNumero());
        cliente.getEndereco().setLogradouro(clientRequest.getLogradouro());

        cliente.getEndereco().setComplemento(clientRequest.getComplemento());

        return clientRepository.save(cliente);
    }

   
    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

   
	public ArrayList<ClientResponse> findCincoMelhoresRankingDot(int pontos) {
		ArrayList<Cliente> clients = clientRepositoryImpl.findCincoMelhoresRankingDot(pontos);
		
		ArrayList<ClientResponse> clientsResponse = new ArrayList<ClientResponse>();
		for (Cliente cliente : clients) {
			clientsResponse.add(ModelMapper.mapClientToPollResponse(cliente));
		}
		
		return clientsResponse;
	}

	public ArrayList<ClientResponse> findCincoPioresRankingDot(int pontos) {
		ArrayList<Cliente> clients =  clientRepositoryImpl.findCincoPioresRankingDot(pontos);
		ArrayList<ClientResponse> clientsResponse = new ArrayList<ClientResponse>();
		for (Cliente cliente : clients) {
			clientsResponse.add(ModelMapper.mapClientToPollResponse(cliente));
		}
		
		return clientsResponse;
	}
}
