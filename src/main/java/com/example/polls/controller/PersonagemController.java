	package com.example.polls.controller;

import com.example.polls.model.*;
import com.example.polls.payload.*;
import com.example.polls.repository.EmpresaRepository;
import com.example.polls.repository.PersonagemRepository;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.EmpresaService;
import com.example.polls.service.GameService;
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
    private GameService gameService;
    
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
    
    @GetMapping("/selectPersonagem")
    @PreAuthorize("hasRole('USER')")
    public DicaResponse selectPersonagem(@CurrentUser UserPrincipal currentUser,@RequestParam(value = "idPersonagem") Long idPersonagem) {
        return gameService.selectPersonagens(currentUser, idPersonagem);
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


       
}
