package com.example.polls.service;

import com.example.polls.exception.BadRequestException;
import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.*;
import com.example.polls.payload.ClientResponse;
import com.example.polls.payload.ExtratoResponse;
import com.example.polls.payload.IndicacaoResponse;
import com.example.polls.payload.PagedResponse;
import com.example.polls.payload.PollRequest;
import com.example.polls.payload.PollResponse;
import com.example.polls.payload.VoteRequest;
import com.example.polls.repository.EmpresaRepository;
import com.example.polls.repository.ExtratoRepository;
import com.example.polls.repository.IndicacaoRepository;
import com.example.polls.repository.PollRepository;
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
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class IndicacaoService {

    @Autowired
    private IndicacaoRepository indicacaoRepository;

    private static final Logger logger = LoggerFactory.getLogger(IndicacaoService.class);

   

	public PagedResponse<IndicacaoResponse> findByUserIndicou(UserPrincipal currentUser,  int page, int size) {
		 Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
	        Page<Indicacao> indicacaoList = indicacaoRepository.findByUserIndicou(currentUser.getId(),pageable);

	        if(indicacaoList.getNumberOfElements() == 0) {
	            return new PagedResponse<>(Collections.emptyList(), indicacaoList.getNumber(),
	            		indicacaoList.getSize(), indicacaoList.getTotalElements(), indicacaoList.getTotalPages(), indicacaoList.isLast());
	        }


	        List<IndicacaoResponse> indicacoesResponses = indicacaoList.map(indicacao -> {
	            return ModelMapper.mapIndicacaoToIndicacaooResponse(indicacao);
	        }).getContent();

	        return new PagedResponse<>(indicacoesResponses, indicacaoList.getNumber(),
	        		indicacaoList.getSize(), indicacaoList.getTotalElements(), indicacaoList.getTotalPages(), indicacaoList.isLast());
	}
    
}
