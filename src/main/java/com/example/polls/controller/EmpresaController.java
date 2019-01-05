package com.example.polls.controller;

import com.example.polls.model.*;
import com.example.polls.payload.*;
import com.example.polls.repository.ClientRepository;
import com.example.polls.repository.EmpresaRepository;
import com.example.polls.repository.PollRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.repository.VoteRepository;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.ClientService;
import com.example.polls.service.EmpresaService;
import com.example.polls.service.PollService;
import com.example.polls.util.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */

@RestController
@RequestMapping("/api/empresa")
public class EmpresaController {


    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private PollService pollService;
    
    @Autowired
    private EmpresaService empresaService;

    private static final Logger logger = LoggerFactory.getLogger(EmpresaController.class);

    @GetMapping
    public PagedResponse<EmpresaResponse>  getEmpresas(@CurrentUser UserPrincipal currentUser,
                                                @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return empresaService.getAllEmpresas(currentUser, page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createEmpresa(@Valid @RequestBody EmpresaRequest empresaRequest) {
    	Empresa empresa = empresaService.createEmpresa(empresaRequest);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{empresaId}")
                .buildAndExpand(empresa.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Empresa cadastrada com Sucesso."));
    }
    
    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> editEmpresa(@Valid @RequestBody EmpresaRequest empresaRequest) {
    	Empresa empresa = empresaService.editEmpresa(empresaRequest);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{empresaId}")
                .buildAndExpand(empresa.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Empresa cadastrado com Sucesso."));
    }


    @GetMapping("/{pollId}")
    public PollResponse getPollById(@CurrentUser UserPrincipal currentUser,
                                    @PathVariable Long pollId) {
        return pollService.getPollById(pollId, currentUser);
    }

    @PostMapping("/{pollId}/votes")
    @PreAuthorize("hasRole('USER')")
    public PollResponse castVote(@CurrentUser UserPrincipal currentUser,
                         @PathVariable Long pollId,
                         @Valid @RequestBody VoteRequest voteRequest) {
        return pollService.castVoteAndGetUpdatedPoll(pollId, voteRequest, currentUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> delete(@PathVariable long id) {
    	Empresa empresa = empresaRepository.getOne(id);
    	empresaRepository.delete(empresa);
    	
    	JSONObject obj = new JSONObject();
    	try {
			obj.put("ok", "ok");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return new ResponseEntity<>(obj, HttpStatus.OK);
}
    
}
