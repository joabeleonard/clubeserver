package com.example.polls.controller;

import com.example.polls.model.*;
import com.example.polls.payload.*;
import com.example.polls.repository.ClientRepository;
import com.example.polls.repository.CupomRepository;
import com.example.polls.repository.EmpresaRepository;
import com.example.polls.repository.PollRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.repository.VoteRepository;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.ClientService;
import com.example.polls.service.CupomService;
import com.example.polls.service.PollService;
import com.example.polls.util.AppConstants;
import com.example.polls.util.ModelMapper;

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

import java.math.BigDecimal;
import java.net.URI;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */

@RestController
@RequestMapping("/api/cupom")
public class CupomController {

    @Autowired
    private CupomRepository cupomRepository;

    @Autowired
    private EmpresaRepository empresaRepository;
    
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PollService pollService;
    
    @Autowired
    private CupomService cupomService;

    private static final Logger logger = LoggerFactory.getLogger(CupomController.class);

    @GetMapping
    @PreAuthorize("hasRole('ENTERPRISE')")
    public ResponseEntity<?>  validarCupon(@CurrentUser UserPrincipal currentUser,
                                                @RequestParam(value = "cupom") String codigo,
                                                @RequestParam(value = "empresaid") String empresaId) {
       CupomResponse cupom = cupomService.findCupomByCodigo(codigo);
       Empresa empresa  = empresaRepository.findByEmpresaId(new Long(empresaId));
       
       if (cupom != null && cupom.getEmpresaId() == empresa.getId()) {
    	   return ResponseEntity.ok(cupom);
       }
    	return ResponseEntity.noContent().build();
    }

    @GetMapping("/generate")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createCupom(@RequestParam(value = "empresa") String empresaId,
    		@RequestParam(value = "cliente") long clienteId) {
    	Cupom cupom = cupomService.createCupom(empresaRepository.getOne(new Long(empresaId)),
    			clientRepository.findByUser(clienteId));
    
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{cupomId}")
                .buildAndExpand(cupom.getId()).toUri();

        
        CupomResponse cupomResponse = ModelMapper.mapCupomToPollResponse(cupom);
        return ResponseEntity.ok(cupomResponse);
    }
    
    @PutMapping
    @PreAuthorize("hasRole('ENTERPRISE')")
    public ResponseEntity<?> validarCupom(@Valid @RequestBody CupomResponse cupomRequest) {
    	
    	Cupom cupom = cupomRepository.getOne(cupomRequest.getId());
    	cupom.setStatusCupom(StatusCupom.ULTILIZADO);
    	cupom.setValorCupom(new BigDecimal(cupomRequest.getValorCupom().replace(",", ".")));
    	cupom = cupomRepository.save(cupom);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{clientId}")
                .buildAndExpand(cupom.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Cliente cadastrado com Sucesso."));
    }
    
    @PutMapping("/avaliar")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> avaliarCupom(@Valid @RequestBody CupomResponse cupomRequest) {
    	
    	Cupom cupom = cupomRepository.getOne(cupomRequest.getId());
    	cupom.setStatusCupom(StatusCupom.AVALIADO);
    	cupom.setNotaAvaliacao(cupomRequest.getNotaAvaliacao());
    	cupom.setDescricaoAvaliacao(cupomRequest.getDescricaoAvaliacao());
    	cupom.setNomeArquivoComprovante(cupomRequest.getNomeArquivoComprovante());
		cupom = cupomRepository.save(cupom);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{clientId}")
                .buildAndExpand(cupom.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Cliente cadastrado com Sucesso."));
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
//    	Cliente cliente = clientRepository.getOne(id);
//    	clientRepository.delete(cliente);
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
    	return null;
}
 
    @GetMapping("/cliente/{username}")
    public PagedResponse<CupomResponse> getCuponsByCliente(@PathVariable(value = "username") String username,
                                                       @CurrentUser UserPrincipal currentUser,
                                                       @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return cupomService.getCupomByCliente(username, currentUser, page, size);
    }
    
    @GetMapping("/cuponsParaAvaliar")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> cuponsParaAvaliar(@CurrentUser UserPrincipal currentUser) {
    	 CupomResponse cupom = cupomService.findCupomByUser(currentUser);
         
         if (cupom != null) {
      	   return ResponseEntity.ok(cupom);
         }
      	return ResponseEntity.noContent().build();
    }
}
