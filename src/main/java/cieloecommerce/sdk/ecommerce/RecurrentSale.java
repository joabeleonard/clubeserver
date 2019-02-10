package cieloecommerce.sdk.ecommerce;

import com.google.gson.annotations.SerializedName;

public class RecurrentSale {
    @SerializedName("Customer")
    private Customer customer;

    @SerializedName("RecurrentPayment")
    private RecurrentPayment recurrentPayment;

    public Customer getCustomer() {
        return customer;
    }

    public RecurrentSale setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }
    
	public Customer customer(String name) {
		setCustomer(new Customer(name));

		return getCustomer();
	}

    public RecurrentPayment getRecurrentPayment() {
        return recurrentPayment;
    }

    public RecurrentSale setRecurrentPayment(RecurrentPayment recurrentPayment) {
        this.recurrentPayment = recurrentPayment;
        return this;
    }
}
