package com.example.polls.service;

import com.example.polls.exception.AppException;
import com.example.polls.exception.BadRequestException;
import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.*;
import com.example.polls.payload.ClientRequest;
import com.example.polls.payload.ClientResponse;
import com.example.polls.payload.EmpresaRequest;
import com.example.polls.payload.EmpresaResponse;
import com.example.polls.payload.PagedResponse;
import com.example.polls.payload.PersonagemRequest;
import com.example.polls.payload.PersonagemResponse;
import com.example.polls.payload.PollRequest;
import com.example.polls.payload.PollResponse;
import com.example.polls.payload.VoteRequest;
import com.example.polls.repository.ClientRepository;
import com.example.polls.repository.EmpresaRepository;
import com.example.polls.repository.PersonagemRepository;
import com.example.polls.repository.PollRepository;
import com.example.polls.repository.RoleRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.repository.VoteRepository;
import com.example.polls.security.UserPrincipal;
import com.example.polls.util.AppConstants;
import com.example.polls.util.ModelMapper;
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

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

@Service
public class PersonagemService {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PersonagemRepository personagemRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    RoleRepository roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(PersonagemService.class);

    
    public PagedResponse<PersonagemResponse> getAllPersonagens(UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Personagem> personagens = personagemRepository.findAll(pageable);

        if(personagens.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), personagens.getNumber(),
            		personagens.getSize(), personagens.getTotalElements(), personagens.getTotalPages(), personagens.isLast());
        }

        List<PersonagemResponse> personagensResponses = personagens.map(personagem -> {
            return ModelMapper.mapPersonagemToPollResponse(personagem);
        }).getContent();
        
        return new PagedResponse<>(personagensResponses, personagens.getNumber(),
        		personagens.getSize(), personagens.getTotalElements(), personagens.getTotalPages(), personagens.isLast());
    }

    
    public Personagem createPersonagem(PersonagemRequest personagemRequest) {
        Personagem personagem = new Personagem();

        personagem.setNome(personagemRequest.getNome());
        personagem.setDescricao(personagemRequest.getDescricao());
        personagem.setHobbie(personagemRequest.getHobbie());

        return personagemRepository.save(personagem);
    }
    
    public Personagem editPersonagem(PersonagemRequest personagemRequest) {
        Personagem personagem = personagemRepository.getOne(personagemRequest.getId());
        
        personagem.setNome(personagemRequest.getNome());
        personagem.setDescricao(personagemRequest.getDescricao());
        personagem.setHobbie(personagemRequest.getHobbie());
       // empresa.setCpf(clientRequest.getCpf());

        return personagemRepository.save(personagem);
    }

     	 
//	public PagedResponse<EmpresaResponse> getEmpresasByFilters(UserPrincipal currentUser, String nome, String categoria,
//			int page, int size) {
//        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
//
//        CategoriaEmpresa categoriaEmpresa = null;
//        String nomeFilter = null;
//        if (categoria != null && !categoria.equals("")) {
//        	categoriaEmpresa = CategoriaEmpresa.valueOf(categoria);
//		}
//        
//        if (nome != null && !nome.equals("")) {
//        	nomeFilter = nome;
//		}
//        Page<Empresa> empresas = personagemRepository.getEmpresasByFilters(nomeFilter, categoriaEmpresa, pageable);
//        if(empresas.getNumberOfElements() == 0) {
//            return new PagedResponse<>(Collections.emptyList(), empresas.getNumber(),
//            		empresas.getSize(), empresas.getTotalElements(), empresas.getTotalPages(), empresas.isLast());
//        }
//
//        List<EmpresaResponse> empresasResponses = empresas.map(empresa -> {
//            return ModelMapper.mapEmpresaToPollResponse(empresa);
//        }).getContent();
//        
//        return new PagedResponse<>(empresasResponses, empresas.getNumber(),
//        		empresas.getSize(), empresas.getTotalElements(), empresas.getTotalPages(), empresas.isLast());
//	}

	 private void validatePageNumberAndSize(int page, int size) {
	        if(page < 0) {
	            throw new BadRequestException("Page number cannot be less than zero.");
	        }

	        if(size > AppConstants.MAX_PAGE_SIZE) {
	            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
	        }
	    }
}
