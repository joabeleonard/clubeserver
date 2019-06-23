package com.example.polls.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.polls.model.ReportAvaliacoes;
import com.example.polls.model.ReportVendas;
import com.example.polls.repository.CupomRepository;
import com.example.polls.security.UserPrincipal;

@Service
public class ReportService {

    @Autowired
    private CupomRepository cupomRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);


	public ArrayList<ReportAvaliacoes> findDataReportAvaliacoes(UserPrincipal currentUser) {
		
		ArrayList<ReportAvaliacoes> avaliacoes = cupomRepository.findReportAvaliacao(currentUser.getId());
		
		return avaliacoes;
	}

	public ArrayList<ReportVendas> findDataReportVendas(UserPrincipal currentUser) {
		ArrayList<ReportVendas> reportVendas = cupomRepository.findDataReportVendas(currentUser.getId());
		
		return reportVendas;
	}

    
}
