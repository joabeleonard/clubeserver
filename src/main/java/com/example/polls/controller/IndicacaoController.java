package com.example.polls.controller;

import com.example.polls.model.*;
import com.example.polls.payload.*;
import com.example.polls.repository.ExtratoRepository;
import com.example.polls.repository.IndicacaoRepository;
import com.example.polls.repository.PollRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.repository.VoteRepository;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.ExtratoService;
import com.example.polls.service.IndicacaoService;
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
import java.util.List;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */

@RestController
@RequestMapping("/api/indicacao")
public class IndicacaoController {

    @Autowired
    private IndicacaoService indicacaoService;



    private static final Logger logger = LoggerFactory.getLogger(IndicacaoController.class);


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ENTERPRISE')")
    public PagedResponse<IndicacaoResponse>  getIndicados(@CurrentUser UserPrincipal currentUser,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return indicacaoService.findByUserIndicou(currentUser, page, size);
    }
}
