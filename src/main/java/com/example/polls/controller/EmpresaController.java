	package com.example.polls.controller;

import com.example.polls.model.*;
import com.example.polls.payload.*;
import com.example.polls.repository.EmpresaRepository;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.EmpresaService;
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
    
    @GetMapping("empresaDTO")
    public List<Empresa>  getEmpresas() {
        return empresaService.getAllEmpresas();
    }
    @GetMapping("empresaSearch")
    public PagedResponse<EmpresaResponse>  getEmpresasByFilters(@CurrentUser UserPrincipal currentUser,
									    		@RequestParam(value = "nome") String nome,
									            @RequestParam(value = "categoria") String categoria,
									            @RequestParam(value = "bairro") String bairro,
									            @RequestParam(value = "cidade") String cidade,
                                                @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return empresaService.getEmpresasByFilters(currentUser,nome, categoria,bairro , cidade, page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createEmpresa(@Valid @RequestBody EmpresaRequest empresaRequest) {
    	Empresa empresa = empresaService.createEmpresa(empresaRequest);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{empresaId}")
                .buildAndExpand(empresa.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Empresa cadastrada com Sucesso."));
    }
    
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editEmpresa(@Valid @RequestBody EmpresaRequest empresaRequest) {
    	Empresa empresa = empresaService.editEmpresa(empresaRequest);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{empresaId}")
                .buildAndExpand(empresa.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Empresa cadastrado com Sucesso."));
    }

    @GetMapping("/userid")
    @PreAuthorize("hasRole('ENTERPRISE')")
    public ResponseEntity<Empresa> getEmpresaByUserId(@RequestParam(value = "userid") String userId){
    	Empresa empresa = empresaRepository.findByUser(new Long(userId));
    	return new ResponseEntity<Empresa>(empresa, HttpStatus.OK);
    	
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
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
