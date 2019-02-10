package com.example.polls.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import cieloecommerce.sdk.Merchant;
import cieloecommerce.sdk.ecommerce.CieloEcommerce;
import cieloecommerce.sdk.ecommerce.Customer;
import cieloecommerce.sdk.ecommerce.Environment;
import cieloecommerce.sdk.ecommerce.Payment;
import cieloecommerce.sdk.ecommerce.Sale;
import cieloecommerce.sdk.ecommerce.SaleResponse;
import cieloecommerce.sdk.ecommerce.request.CieloError;
import cieloecommerce.sdk.ecommerce.request.CieloRequestException;

@Service
public class PagamentoService {

	
	public void pagamento(){
		Merchant merchant = new Merchant("MERCHANT ID", "MERCHANT KEY");

		// Crie uma instância de Sale informando o ID do pagamento
		Sale sale = new Sale("ID do pagamento");

		// Crie uma instância de Customer informando o nome do cliente
		Customer customer = sale.customer("Comprador Teste");

		// Crie uma instância de Payment informando o valor do pagamento
		Payment payment = sale.payment(15700);

		// Crie  uma instância de Credit Card utilizando os dados de teste
		// esses dados estão disponíveis no manual de integração
		payment.creditCard("123", "Visa").setExpirationDate("12/2018")
		                                 .setCardNumber("0000000000000001")
		                                 .setHolder("Fulano de Tal");

		// Crie o pagamento na Cielo
		try {
		    // Configure o SDK com seu merchant e o ambiente apropriado para criar a venda
		    sale = new CieloEcommerce(merchant, Environment.SANDBOX).createSale(sale);

		    // Com a venda criada na Cielo, já temos o ID do pagamento, TID e demais
		    // dados retornados pela Cielo
		    String paymentId = sale.getPayment().getPaymentId();

		    // Com o ID do pagamento, podemos fazer sua captura, se ela não tiver sido capturada ainda
		    SaleResponse saleResponse = new CieloEcommerce(merchant, Environment.SANDBOX).captureSale(paymentId, 15700, 0);

		    // E também podemos fazer seu cancelamento, se for o caso
		   // sale = new CieloEcommerce(merchant, Environment.SANDBOX).cancelSale(paymentId, 15700);
		} catch (CieloRequestException e) {
		    // Em caso de erros de integração, podemos tratar o erro aqui.
		    // os códigos de erro estão todos disponíveis no manual de integração.
		    CieloError error = e.getError();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
