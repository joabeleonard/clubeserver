	package com.example.polls.controller;

import com.example.polls.model.*;
import com.example.polls.payload.*;
import com.example.polls.repository.DicaRepository;
import com.example.polls.repository.EmpresaRepository;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.DicaService;
import com.example.polls.service.EmpresaService;
import com.example.polls.service.GameService;
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
@RequestMapping("/api/dicas")
public class DicaController {


    @Autowired
    private DicaRepository dicaRepository;

    @Autowired
    private DicaService dicaService;
    
    @Autowired
    private GameService gameService;
    
    private static final Logger logger = LoggerFactory.getLogger(DicaController.class);

    @GetMapping
    public PagedResponse<DicaResponse>  getDicas(@CurrentUser UserPrincipal currentUser,
                                                @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return dicaService.getAllDicas(currentUser, page, size);
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
    public ResponseEntity<?> createDica(@Valid @RequestBody DicaRequest dicaRequest) {
    	DicasGames dica = dicaService.createDica(dicaRequest);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{dicaId}")
                .buildAndExpand(dica.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Dica cadastrada com Sucesso."));
    }
    
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editDica(@Valid @RequestBody DicaRequest dicaRequest) {
    	DicasGames dica = dicaService.editDica(dicaRequest);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{dicaId}")
                .buildAndExpand(dica.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Dica cadastrado com Sucesso."));
    }


//    @GetMapping("/{pollId}")
//    public PollResponse getPollById(@CurrentUser UserPrincipal currentUser,
//                                    @PathVariable Long pollId) {
//        return dicaService.getPollById(pollId, currentUser);
//    }
//    
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
    
  
    @GetMapping("/proximaDica")
    @PreAuthorize("hasRole('USER')")
    public DicaResponse proximaDica(@CurrentUser UserPrincipal currentUser, @RequestParam(value = "id") Long id) {
    	return dicaService.proximaDica(currentUser,id);
    }

//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> delete(@PathVariable long id) {
//    	Empresa empresa = empresaRepository.getOne(id);
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
    
    @GetMapping("/game")
    @PreAuthorize("hasRole('USER')")
    public DicaResponse selectPersonagem(@CurrentUser UserPrincipal currentUser) {
        return gameService.loadGame(currentUser);
    }
}
