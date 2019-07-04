	package com.example.polls.controller;

import com.example.polls.model.*;
import com.example.polls.payload.*;
import com.example.polls.repository.EmpresaRepository;
import com.example.polls.repository.PersonagemRepository;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.EmpresaService;
import com.example.polls.service.PersonagemService;
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
@RequestMapping("/api/personagens")
public class PersonagemController {


    @Autowired
    private PersonagemRepository personagemRepository;

    @Autowired
    private PollService pollService;
    
    @Autowired
    private PersonagemService personagemService;

    private static final Logger logger = LoggerFactory.getLogger(PersonagemController.class);

    @GetMapping
    public PagedResponse<PersonagemResponse>  getPersonagens(@CurrentUser UserPrincipal currentUser,
                                                @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return personagemService.getAllPersonagens(currentUser, page, size);
    }
    
    @GetMapping("empresaSearch")
    public PagedResponse<PersonagemResponse>  getEmpresasByFilters(@CurrentUser UserPrincipal currentUser,
									    		@RequestParam(value = "nome") String nome,
									            @RequestParam(value = "categoria") String categoria,
                                                @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return null;
    	//return personagemService.getEmpresasByFilters(currentUser,nome, categoria, page, size);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createPersonagem(@Valid @RequestBody PersonagemRequest personagemRequest) {
    	Personagem personagem = personagemService.createPersonagem(personagemRequest);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{personagemId}")
                .buildAndExpand(personagem.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Personagem cadastrado com Sucesso."));
    }
    
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editPersongem(@Valid @RequestBody PersonagemRequest personagemRequest) {
    	Personagem personagem = personagemService.editPersonagem(personagemRequest);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{personagemId}")
                .buildAndExpand(personagem.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Personagem cadastrado com Sucesso."));
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

//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> delete(@PathVariable long id) {
//    	Empresa empresa = per.getOne(id);
//    	empresaRepository.delete(empresa);
//    	
//    	JSONObject obj = new JSONObject();
//    	try {
//			obj.put("ok", "ok");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//    	return new ResponseEntity<>(obj, HttpStatus.OK);
//}
    
}
