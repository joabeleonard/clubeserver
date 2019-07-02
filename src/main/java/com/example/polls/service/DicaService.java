package com.example.polls.service;

import com.example.polls.exception.AppException;
import com.example.polls.exception.BadRequestException;
import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.*;
import com.example.polls.payload.ClientRequest;
import com.example.polls.payload.ClientResponse;
import com.example.polls.payload.DicaRequest;
import com.example.polls.payload.DicaResponse;
import com.example.polls.payload.EmpresaRequest;
import com.example.polls.payload.EmpresaResponse;
import com.example.polls.payload.PagedResponse;
import com.example.polls.payload.PollRequest;
import com.example.polls.payload.PollResponse;
import com.example.polls.payload.VoteRequest;
import com.example.polls.repository.ClientRepository;
import com.example.polls.repository.DicaRepository;
import com.example.polls.repository.EmpresaRepository;
import com.example.polls.repository.NivelRepository;
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
public class DicaService {

    @Autowired
    private NivelRepository nivelRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DicaRepository dicaRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    RoleRepository roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(DicaService.class);

    
    public PagedResponse<DicaResponse> getAllDicas(UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<DicasGames> dicas = dicaRepository.findAll(pageable);

        if(dicas.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), dicas.getNumber(),
            		dicas.getSize(), dicas.getTotalElements(), dicas.getTotalPages(), dicas.isLast());
        }

        List<DicaResponse> dicasResponses = dicas.map(dicasgames -> {
            return ModelMapper.mapDicaToDicaResponse(dicasgames);
        }).getContent();
        
        return new PagedResponse<>(dicasResponses, dicas.getNumber(),
        		dicas.getSize(), dicas.getTotalElements(), dicas.getTotalPages(), dicas.isLast());
    }


    public DicasGames createDica(DicaRequest dicaRequest) {
        DicasGames dica = new DicasGames();
        
        dica.setDica(dicaRequest.getDica());
        dica.setLocal(dicaRequest.getLocal());
        dica.setNivelGame(nivelRepository.getOne(dicaRequest.getNivelId()));
        dica.setOrdemDica(dicaRequest.getOrdemDica());
        dica.setQuemEstaComADica(dicaRequest.getQuemEstaComADica());
        dica.setTempoDeLocomocao(dicaRequest.getTempoDeLocomocao());
        
        return dicaRepository.save(dica);
    }
    
    public DicasGames editDica(DicaRequest dicaRequest) {
    	DicasGames dica = dicaRepository.getOne(dicaRequest.getId());
        
    	 dica.setDica(dicaRequest.getDica());
         dica.setLocal(dicaRequest.getLocal());
         dica.setNivelGame(nivelRepository.getOne(dicaRequest.getNivelId()));
         dica.setOrdemDica(dicaRequest.getOrdemDica());
         dica.setQuemEstaComADica(dicaRequest.getQuemEstaComADica());
         dica.setTempoDeLocomocao(dicaRequest.getTempoDeLocomocao());

        return dicaRepository.save(dica);
    }


    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    private Map<Long, Long> getChoiceVoteCountMap(List<Long> pollIds) {
        // Retrieve Vote Counts of every Choice belonging to the given pollIds
        List<ChoiceVoteCount> votes = voteRepository.countByPollIdInGroupByChoiceId(pollIds);

        Map<Long, Long> choiceVotesMap = votes.stream()
                .collect(Collectors.toMap(ChoiceVoteCount::getChoiceId, ChoiceVoteCount::getVoteCount));

        return choiceVotesMap;
    }

    private Map<Long, Long> getPollUserVoteMap(UserPrincipal currentUser, List<Long> pollIds) {
        // Retrieve Votes done by the logged in user to the given pollIds
        Map<Long, Long> pollUserVoteMap = null;
        if(currentUser != null) {
            List<Vote> userVotes = voteRepository.findByUserIdAndPollIdIn(currentUser.getId(), pollIds);

            pollUserVoteMap = userVotes.stream()
                    .collect(Collectors.toMap(vote -> vote.getPoll().getId(), vote -> vote.getChoice().getId()));
        }
        return pollUserVoteMap;
    }

    Map<Long, User> getPollCreatorMap(List<Poll> polls) {
        // Get Poll Creator details of the given list of polls
        List<Long> creatorIds = polls.stream()
                .map(Poll::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());

        List<User> creators = userRepository.findByIdIn(creatorIds);
        Map<Long, User> creatorMap = creators.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return creatorMap;
    }

	public Cliente save(@Valid ClientRequest clientRequest) {
		// TODO Auto-generated method stub
		return null;
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
//        Page<Empresa> empresas = empresaRepository.getEmpresasByFilters(nomeFilter, categoriaEmpresa, pageable);
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

}
