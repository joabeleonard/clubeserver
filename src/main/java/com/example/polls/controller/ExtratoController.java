package com.example.polls.controller;

import com.example.polls.model.*;
import com.example.polls.payload.*;
import com.example.polls.repository.ExtratoRepository;
import com.example.polls.repository.PollRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.repository.VoteRepository;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.ExtratoService;
import com.example.polls.service.PollService;
import com.example.polls.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/extrato")
public class ExtratoController {

    @Autowired
    private ExtratoService extratoService;

    @Autowired
    private UserRepository userRepository;


    private static final Logger logger = LoggerFactory.getLogger(ExtratoController.class);


    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public PagedResponse<Extrato> getExtratos(@CurrentUser UserPrincipal currentUser,
                                                @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return extratoService.getAllExtrato(currentUser, page, size);
    }
    
    @GetMapping("/extratosFinanceiro")
    @PreAuthorize("hasRole('USER')")
    public PagedResponse<ExtratoResponse> getExtratosFinanceiro(@CurrentUser UserPrincipal currentUser,
                                                @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return extratoService.getAllExtratoFinanceiro(currentUser, page, size);
    }
}
