package com.example.polls.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.polls.model.Cliente;

import cieloecommerce.sdk.Merchant;
import cieloecommerce.sdk.ecommerce.CieloEcommerce;
import cieloecommerce.sdk.ecommerce.Customer;
import cieloecommerce.sdk.ecommerce.Environment;
import cieloecommerce.sdk.ecommerce.Payment;
import cieloecommerce.sdk.ecommerce.RecurrentPayment;
import cieloecommerce.sdk.ecommerce.RecurrentPayment.Interval;
import cieloecommerce.sdk.ecommerce.RecurrentSale;
import cieloecommerce.sdk.ecommerce.Sale;
import cieloecommerce.sdk.ecommerce.SaleResponse;
import cieloecommerce.sdk.ecommerce.request.CieloError;
import cieloecommerce.sdk.ecommerce.request.CieloRequestException;

@Service
public class PagamentoService {

	public static void main(String[] args) {

		Cliente cliente = new Cliente();
		cliente.setCodigoSeguranca("123");
		cliente.setBandeira("Visa");
		cliente.setDataValidade("12/2021");
		cliente.setNomeTitular("Fulano de Tal");
		cliente.setNumeroCartao("1232222212321234");
		Map<String, String> pagamento = pagamentoTeste(cliente);
        cliente.setRecurrentPaymentId(pagamento.get("recurrentPaymentId"));
        cliente.setPaymentId(pagamento.get("paymentId"));
	}
	
	public static Map<String, String> pagamentoTeste(Cliente cliente){
		Merchant merchant = new Merchant("971ab897-0989-4770-91a8-3c9fbc2833ae", "PDUADUKEWPQEQCBETQUCOYKUUTDJCJHAXPJLUOOW");

		// Crie uma instância de Sale informando o ID do pagamento
		Sale sale = new Sale("ID do pagamento");
		RecurrentSale recurrentSale = new RecurrentSale();
		Customer customer = recurrentSale.customer("Nome teste");
		RecurrentPayment recurrentPayment = new RecurrentPayment(true);
		recurrentPayment.setAmount(12);
		recurrentPayment.setAuthorizeNow(true);
		recurrentPayment.setInterval(Interval.Monthly);
		
		// Crie uma instância de Customer informando o nome do cliente
		Customer customer2 = sale.customer("Nome teste");

		// Crie uma instância de Payment informando o valor do pagamento
		Payment payment = sale.payment(15700);
		payment.setRecurrentPayment(recurrentPayment);

		// Crie  uma instância de Credit Card utilizando os dados de teste
		// esses dados estão disponíveis no manual de integração
//		payment.creditCard("123", "Visa").setExpirationDate("12/2020")
//		                                 .setCardNumber("1232222212321234")
//		                                 .setHolder("Fulano de Tal");
		
		payment.creditCard(cliente.getCodigoSeguranca(), cliente.getBandeira()).setExpirationDate(cliente.getDataValidade())
        .setCardNumber(cliente.getNumeroCartao())
        .setHolder(cliente.getNomeTitular());


		// Crie o pagamento na Cielo
		try {
		    // Configure o SDK com seu merchant e o ambiente apropriado para criar a venda
		    sale = new CieloEcommerce(merchant, Environment.SANDBOX).createSale(sale);

		    // Com a venda criada na Cielo, já temos o ID do pagamento, TID e demais
		    // dados retornados pela Cielo
		    String paymentId = sale.getPayment().getPaymentId();
		    String recurrentPaymentId = sale.getPayment().getRecurrentPayment().getRecurrentPaymentId();
		    // Com o ID do pagamento, podemos fazer sua captura, se ela não tiver sido capturada ainda
		    SaleResponse saleResponse = new CieloEcommerce(merchant, Environment.SANDBOX).captureSale(paymentId, 15700, 0);

		    Map<String, String> pagamento = new HashMap<String, String>();
		    pagamento.put("paymentId", paymentId);
		    pagamento.put("recurrentPaymentId", recurrentPaymentId);
		    
		    // E também podemos fazer seu cancelamento, se for o caso
		   // sale = new CieloEcommerce(merchant, Environment.SANDBOX).cancelSale(paymentId, 15700);
		    return pagamento;
		} catch (CieloRequestException e) {
		    // Em caso de erros de integração, podemos tratar o erro aqui.
		    // os códigos de erro estão todos disponíveis no manual de integração.
		    CieloError error = e.getError();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Map<String, String> pagamento(Cliente cliente){
		//Teste Merchant merchant = new Merchant("971ab897-0989-4770-91a8-3c9fbc2833ae", "PDUADUKEWPQEQCBETQUCOYKUUTDJCJHAXPJLUOOW");
		Merchant merchant = new Merchant("b4098410-bcbe-41c8-8c49-cc280fe2f260", "A7kvX3FcfCg2Jct70cMJOPdQDslbovcmQbrDajTv");

		

		// Crie uma instância de Sale informando o ID do pagamento
		Sale sale = new Sale("ID do pagamento");
		RecurrentSale recurrentSale = new RecurrentSale();
		Customer customer = recurrentSale.customer(cliente.getUser().getName());
		RecurrentPayment recurrentPayment = new RecurrentPayment(true);
		recurrentPayment.setAmount(20);
		recurrentPayment.setAuthorizeNow(true);
		recurrentPayment.setInterval(Interval.Monthly);
		
		// Crie uma instância de Customer informando o nome do cliente
		Customer customer2 = sale.customer(cliente.getUser().getName());

		// Crie uma instância de Payment informando o valor do pagamento
		Payment payment = sale.payment(10);
		payment.setRecurrentPayment(recurrentPayment);

		// Crie  uma instância de Credit Card utilizando os dados de teste
		// esses dados estão disponíveis no manual de integração
//		payment.creditCard("123", "Visa").setExpirationDate("12/2019")
//		                                 .setCardNumber("0000000000000001")
//		                                 .setHolder("Fulano de Tal");
		
		//payment.creditCard(cliente.getCodigoSeguranca(), cliente.getBandeira()).setExpirationDate(cliente.getDataValidade())
       // .setCardNumber(cliente.getNumeroCartao())
       // .setHolder(cliente.getNomeTitular());


		// Crie o pagamento na Cielo
		try {
		    // Configure o SDK com seu merchant e o ambiente apropriado para criar a venda
		    sale = new CieloEcommerce(merchant, Environment.PRODUCTION).createSale(sale);

		    // Com a venda criada na Cielo, já temos o ID do pagamento, TID e demais
		    // dados retornados pela Cielo
		    String paymentId = sale.getPayment().getPaymentId();
		    String recurrentPaymentId = sale.getPayment().getRecurrentPayment().getRecurrentPaymentId();
		    // Com o ID do pagamento, podemos fazer sua captura, se ela não tiver sido capturada ainda
		    SaleResponse saleResponse = new CieloEcommerce(merchant, Environment.PRODUCTION).captureSale(paymentId, 15700, 0);

		    Map<String, String> pagamento = new HashMap<String, String>();
		    pagamento.put("paymentId", paymentId);
		    pagamento.put("recurrentPaymentId", recurrentPaymentId);
		    
		    // E também podemos fazer seu cancelamento, se for o caso
		   // sale = new CieloEcommerce(merchant, Environment.SANDBOX).cancelSale(paymentId, 15700);
		    return pagamento;
		} catch (CieloRequestException e) {
		    // Em caso de erros de integração, podemos tratar o erro aqui.
		    // os códigos de erro estão todos disponíveis no manual de integração.
		    CieloError error = e.getError();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
