package com.example.polls.service;

import com.example.polls.exception.AppException;
import com.example.polls.exception.BadRequestException;
import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.*;
import com.example.polls.payload.ClientRequest;
import com.example.polls.payload.ClientResponse;
import com.example.polls.payload.EmpresaRequest;
import com.example.polls.payload.EmpresaResponse;
import com.example.polls.payload.NivelRequest;
import com.example.polls.payload.NivelResponse;
import com.example.polls.payload.PagedResponse;
import com.example.polls.payload.PollRequest;
import com.example.polls.payload.PollResponse;
import com.example.polls.payload.VoteRequest;
import com.example.polls.repository.ClientRepository;
import com.example.polls.repository.EmpresaRepository;
import com.example.polls.repository.NivelRepository;
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
public class NivelService {

    @Autowired
    private PersonagemRepository personagemRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private NivelRepository nivelRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    RoleRepository roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(NivelService.class);

    
    public PagedResponse<NivelResponse> getAllNiveis(UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<NivelGame> niveis = nivelRepository.findAll(pageable);

        if(niveis.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), niveis.getNumber(),
            		niveis.getSize(), niveis.getTotalElements(), niveis.getTotalPages(), niveis.isLast());
        }

        List<NivelResponse> niveisResponses = niveis.map(nivel -> {
            return ModelMapper.mapNivelToNivelResponse(nivel);
        }).getContent();
        
        return new PagedResponse<>(niveisResponses, niveis.getNumber(),
        		niveis.getSize(), niveis.getTotalElements(), niveis.getTotalPages(), niveis.isLast());
    }

  
    public NivelGame createNivel(NivelRequest nivelRequest) {
        NivelGame nivel = new NivelGame();
        
            
        nivel.setDescricao(nivelRequest.getDescricao());
        nivel.setMissao(nivelRequest.getMissao());
        nivel.setNome(nivelRequest.getNome());
        nivel.setOrdemNivel(nivelRequest.getOrdemNivel());
        nivel.setPersonagem(personagemRepository.findById(nivelRequest.getPersonagemId()).get());
        nivel.setPremio(nivelRequest.getPremio());
        nivel.setResposta(nivelRequest.getResposta());
        return nivelRepository.save(nivel);
    }
    
    public NivelGame editNivel(NivelRequest nivelRequest) {
        NivelGame nivel = nivelRepository.getOne(nivelRequest.getId());
        
        nivel.setDescricao(nivelRequest.getDescricao());
        nivel.setMissao(nivelRequest.getMissao());
        nivel.setNome(nivelRequest.getNome());
        nivel.setOrdemNivel(nivelRequest.getOrdemNivel());
        nivel.setPersonagem(personagemRepository.findById(nivelRequest.getPersonagemId()).get());
        nivel.setPremio(nivelRequest.getPremio());
        nivel.setResposta(nivelRequest.getResposta());
       // empresa.setCpf(clientRequest.getCpf());

        return nivelRepository.save(nivel);
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

}
