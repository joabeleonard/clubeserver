package com.example.polls.util;

import com.example.polls.model.Cliente;
import com.example.polls.model.Cupom;
import com.example.polls.model.Empresa;
import com.example.polls.model.Poll;
import com.example.polls.model.User;
import com.example.polls.payload.ChoiceResponse;
import com.example.polls.payload.ClientResponse;
import com.example.polls.payload.CupomResponse;
import com.example.polls.payload.EmpresaResponse;
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

        return clientResponse;
    }
    
    public static EmpresaResponse mapEmpresaToPollResponse(Empresa empresa) {
        EmpresaResponse empresaResponse = new EmpresaResponse();
        empresaResponse.setId(empresa.getId());
        empresaResponse.setDesconto(empresa.getDesconto());
        empresaResponse.setLogo(empresa.getLogo());
        empresaResponse.setDetalhes(empresa.getDetalhes());
        empresaResponse.setUser(empresa.getUser());

        return empresaResponse;
    }


	public static CupomResponse mapCupomToPollResponse(Cupom cupom) {
		CupomResponse cupomResponse = new CupomResponse();
		cupomResponse.setDataGeracao(cupom.getDataGeracao());
		cupomResponse.setStatus(cupom.getStatusCupom());
		cupomResponse.setUserName(cupom.getCliente().getUser().getUsername());
		cupomResponse.setEmpresa(cupom.getEmpresa().getUser().getName());
		return cupomResponse;
	}
    
}
