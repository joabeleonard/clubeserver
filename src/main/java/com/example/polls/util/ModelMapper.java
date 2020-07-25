package com.example.polls.util;

import com.example.polls.model.Cliente;
import com.example.polls.model.Cupom;
import com.example.polls.model.DicasGames;
import com.example.polls.model.Empresa;
import com.example.polls.model.NivelGame;
import com.example.polls.model.Personagem;
import com.example.polls.model.Poll;
import com.example.polls.model.User;
import com.example.polls.payload.ChoiceResponse;
import com.example.polls.payload.ClientResponse;
import com.example.polls.payload.CupomResponse;
import com.example.polls.payload.DicaResponse;
import com.example.polls.payload.EmpresaResponse;
import com.example.polls.payload.NivelResponse;
import com.example.polls.payload.PersonagemResponse;
import com.example.polls.payload.PollResponse;
import com.example.polls.payload.UserSummary;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModelMapper {

    public static PollResponse mapPollToPollResponse(Poll poll, Map<Long, Long> choiceVotesMap, User creator, Long userVote) {
        PollResponse pollResponse = new PollResponse();
        pollResponse.setId(poll.getId());
        pollResponse.setQuestion(poll.getQuestion());
        pollResponse.setCreationDateTime(poll.getCreatedAt());
        pollResponse.setExpirationDateTime(poll.getExpirationDateTime());
        pollResponse.setEmpresa(poll.getEmpresa());
        Instant now = Instant.now();
        pollResponse.setExpired(poll.getExpirationDateTime().isBefore(now));

        List<ChoiceResponse> choiceResponses = poll.getChoices().stream().map(choice -> {
            ChoiceResponse choiceResponse = new ChoiceResponse();
            choiceResponse.setId(choice.getId());
            choiceResponse.setText(choice.getText());

            if(choiceVotesMap.containsKey(choice.getId())) {
                choiceResponse.setVoteCount(choiceVotesMap.get(choice.getId()));
            } else {
                choiceResponse.setVoteCount(0);
            }
            return choiceResponse;
        }).collect(Collectors.toList());

        pollResponse.setChoices(choiceResponses);
        UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getName());
        pollResponse.setCreatedBy(creatorSummary);

        if(userVote != null) {
            pollResponse.setSelectedChoice(userVote);
        }

        long totalVotes = pollResponse.getChoices().stream().mapToLong(ChoiceResponse::getVoteCount).sum();
        pollResponse.setTotalVotes(totalVotes);

        return pollResponse;
    }

    
    public static ClientResponse mapClientToPollResponse(Cliente cliente) {
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setId(cliente.getId());
        clientResponse.setCpf(cliente.getCpf());
        clientResponse.setPontos(cliente.getPontos());
        clientResponse.setAtivo(cliente.isAtivo());
        clientResponse.setSexo(cliente.getSexo());
        clientResponse.setRg(cliente.getRg());
        clientResponse.setTelefone(cliente.getTelefone());     
        clientResponse.setBairro(cliente.getEndereco().getBairro());
        clientResponse.setLogradouro(cliente.getEndereco().getLogradouro());   
        clientResponse.setComplemento(cliente.getEndereco().getComplemento());
        clientResponse.setCep(cliente.getEndereco().getCep());          
        clientResponse.setNumero(cliente.getEndereco().getNumero());       
        clientResponse.setCidade(cliente.getEndereco().getCidade());       
        clientResponse.setEstado(cliente.getEndereco().getEstado());       
        clientResponse.setDataNascimento(DateUtil.converteData(cliente.getDataNascimento()) );
        clientResponse.setUser(cliente.getUser());
        clientResponse.setPontosExperiencia(cliente.getPontosExperiencia());

        return clientResponse;
    }
    
    public static EmpresaResponse mapEmpresaToPollResponse(Empresa empresa) {
        EmpresaResponse empresaResponse = new EmpresaResponse();
        empresaResponse.setId(empresa.getId());
        empresaResponse.setDesconto(empresa.getDesconto());
        empresaResponse.setLogo(empresa.getLogo());
        empresaResponse.setEndereco(empresa.getEndereco());
        empresaResponse.setDetalhes(empresa.getDetalhes());
        empresaResponse.setUser(empresa.getUser());
        empresaResponse.setCategoriaEmpresa(empresa.getCategoriaEmpresa());
        empresaResponse.setUrl(empresa.getUrl());
        return empresaResponse;
    }
    public static PersonagemResponse mapPersonagemToPollResponse(Personagem personagem) {
    	PersonagemResponse personagemResponse = new PersonagemResponse();
    	personagemResponse.setId(personagem.getId());
    	personagemResponse.setNome(personagem.getNome());
    	personagemResponse.setDescricao(personagem.getDescricao());
    	personagemResponse.setHobbie(personagem.getHobbie());
        return personagemResponse;
    }
    
    public static NivelResponse mapNivelToNivelResponse(NivelGame nivelGame) {
    	nivelGame.getPersonagem().setNiveisGame(null);
    	NivelResponse nivelResponse = new NivelResponse();
        nivelResponse.setId(nivelGame.getId());
        nivelResponse.setNome(nivelGame.getNome());
        nivelResponse.setDescricao(nivelGame.getDescricao());
        nivelResponse.setMissao(nivelGame.getMissao());
        nivelResponse.setOrdemNivel(nivelGame.getOrdemNivel());
        nivelResponse.setPersonagem(nivelGame.getPersonagem());
        nivelResponse.setPremio(nivelGame.getPremio());
        nivelResponse.setResposta(nivelGame.getResposta());
        return nivelResponse;
    }
    
    public static DicaResponse mapDicaToDicaResponse(DicasGames dicasGames) {
    	DicaResponse dicaResponse = new DicaResponse();
        dicaResponse.setId(dicasGames.getId());
        dicaResponse.setLocal(dicasGames.getLocal());
        dicaResponse.setTempoDeLocomocao(dicasGames.getTempoDeLocomocao());
        dicaResponse.setDica(dicasGames.getDica());
        dicaResponse.setQuemEstaComADica(dicasGames.getQuemEstaComADica());
        dicaResponse.setOrdemDica(dicasGames.getOrdemDica());
        dicasGames.getNivelGame().getPersonagem().setNiveisGame(null);

        dicasGames.getNivelGame().setDicasGames(null);
        dicaResponse.setNivelGame(dicasGames.getNivelGame());
        return dicaResponse;
    }

	public static CupomResponse mapCupomToPollResponse(Cupom cupom) {
		CupomResponse cupomResponse = new CupomResponse();
		cupomResponse.setEmpresaId((cupom.getEmpresa().getId()));
		cupomResponse.setClienteId((cupom.getCliente().getId()));
		cupomResponse.setId(cupom.getId());
		cupomResponse.setDataGeracao(cupom.getDataGeracao());
		cupomResponse.setStatus(cupom.getStatusCupom().toString());
		cupomResponse.setUserName(cupom.getCliente().getUser().getUsername());
		cupomResponse.setEmpresa(cupom.getEmpresa().getUser().getName());
		cupomResponse.setCodigo(cupom.getCodigo());
		return cupomResponse;
	}
    
}
