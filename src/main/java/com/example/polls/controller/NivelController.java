	package com.example.polls.controller;

import com.example.polls.model.*;
import com.example.polls.payload.*;
import com.example.polls.repository.EmpresaRepository;
import com.example.polls.repository.NivelRepository;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.EmpresaService;
import com.example.polls.service.NivelService;
import com.example.polls.service.PollService;
import com.example.polls.util.AppConstants;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */

@RestController
@RequestMapping("/api/nivel")
public class NivelController {


    @Autowired
    private NivelRepository nivel;

    @Autowired
    private PollService pollService;
    
    @Autowired
    private NivelService nivelService;

    private static final Logger logger = LoggerFactory.getLogger(NivelController.class);

    @GetMapping
    public PagedResponse<NivelResponse>  getNiveis(@CurrentUser UserPrincipal currentUser,
                                                @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return nivelService.getAllNiveis(currentUser, page, size);
    }
    
//    @GetMapping("empresaSearch")
//    public PagedResponse<EmpresaResponse>  getEmpresasByFilters(@CurrentUser UserPrincipal currentUser,
//									    		@RequestParam(value = "nome") String nome,
//									            @RequestParam(value = "categoria") String categoria,
//                                                @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
//                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
//        return empresaService.getEmpresasByFilters(currentUser,nome, categoria, page, size);
//    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNivel(@Valid @RequestBody NivelRequest nivelRequest) {
    	NivelGame nivel = nivelService.createNivel(nivelRequest);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{empresaId}")
                .buildAndExpand(nivel.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Nivel do game cadastrado com Sucesso."));
    }
  
    @GetMapping("/proximoNivel")
    @PreAuthorize("hasRole('USER')")
    public DicaResponse proximoNivel(@CurrentUser UserPrincipal currentUser,@RequestParam(value = "id") Long id) {
    	return nivelService.proximoNivel(currentUser, id);
    }
    
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editNivel(@Valid @RequestBody NivelRequest nivelRequest) {
    	NivelGame nivelGame = nivelService.editNivel(nivelRequest);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{nivelId}")
                .buildAndExpand(nivelGame.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Nivel cadastrado com Sucesso."));
    }


    @GetMapping("/{pollId}")
    public PollResponse getPollById(@CurrentUser UserPrincipal currentUser,
                                    @PathVariable Long pollId) {
        return pollService.getPollById(pollId, currentUser);
    }
    
    @GetMapping("/categorias")
    public List<CategoriaEmpresa> getCategorias(@CurrentUser UserPrincipal currentUser) {
//    	ArrayList<String> enumNames =  (ArrayList<String>) Stream.of(CategoriaEmpresa.values())
//                .map(Enum::name)
//                .collect(Collectors.toList());
    	
    	List<CategoriaEmpresa> categorias = Arrays.asList(CategoriaEmpresa.values());

		return  categorias;
    }

    private static Map<String, String> initializeMapping() {
        Map<String, String> mMap = new HashMap<String, String>();
        for (CategoriaEmpresa s : CategoriaEmpresa.values()) {
            mMap.put(s.getCode(), s.getDescricao());
        }
        return mMap;
    }
    
    @PostMapping("/{pollId}/votes")
    @PreAuthorize("hasRole('USER')")
    public PollResponse castVote(@CurrentUser UserPrincipal currentUser,
                         @PathVariable Long pollId,
                         @Valid @RequestBody VoteRequest voteRequest) {
        return pollService.castVoteAndGetUpdatedPoll(pollId, voteRequest, currentUser);
    }

   
    
}
