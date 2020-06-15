package com.example.polls.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.polls.exception.AppException;
import com.example.polls.exception.BadRequestException;
import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.Choice;
import com.example.polls.model.ChoiceVoteCount;
import com.example.polls.model.Cliente;
import com.example.polls.model.Endereco;
import com.example.polls.model.Indicacao;
import com.example.polls.model.Poll;
import com.example.polls.model.Role;
import com.example.polls.model.RoleName;
import com.example.polls.model.User;
import com.example.polls.model.PreUser;
import com.example.polls.model.Vote;
import com.example.polls.payload.PreUserRequest;
import com.example.polls.payload.ClientRequest;
import com.example.polls.payload.PagedResponse;
import com.example.polls.payload.PollResponse;
import com.example.polls.payload.VoteRequest;
import com.example.polls.repository.PreUserRepository;
import com.example.polls.repository.ClientRepository;
import com.example.polls.repository.IndicacaoRepository;
import com.example.polls.repository.PollRepository;
import com.example.polls.repository.RoleRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.repository.VoteRepository;
import com.example.polls.repository.impl.ClientRepositoryImpl;
import com.example.polls.repository.impl.PreUserRepositoryImpl;
import com.example.polls.security.UserPrincipal;
import com.example.polls.util.AppConstants;
import com.example.polls.util.ModelMapper;

@Service
public class PreUserService {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private PreUserRepository userRepository;
    
    @Autowired
    private PreUserRepository preUserRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private IndicacaoRepository indicacaoRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    RoleRepository roleRepository;
    
    @Autowired
    PagamentoService pagamentoService;
    
    @Autowired
    private PreUserRepositoryImpl preUserRepositoryImpl;

    private static final Logger logger = LoggerFactory.getLogger(PreUserService.class);
    
    public Cliente createPreUser(PreUserRequest preUserRequest) {
    	
        Cliente cliente = new Cliente();
        
        User user = new User(preUserRequest.getEmail(), preUserRequest.getEmail(),
        		preUserRequest.getEmail(), preUserRequest.getSenha());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));
     
        cliente.setUser(user);
        cliente.setAtivo(true);
       
       UUID uuid = UUID.randomUUID();
       String myRandom = uuid.toString();
       cliente.setCodigoIndicacao(myRandom.substring(0,8));
        
        Endereco endereco = new Endereco();
        cliente.setEndereco(endereco);
        Cliente clienteSalvo = clientRepository.save(cliente);
        return clienteSalvo;
    }
    
    public PreUser editPreUser(PreUserRequest preUserRequest) {
        PreUser preUser = preUserRepository.getOne(preUserRequest.getId());
        

        return preUserRepository.save(preUser);
    }


}
