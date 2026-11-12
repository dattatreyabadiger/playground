package payment_gateway.payment_providers;


import payment_gateway.model.ProviderResult;

public interface PaymentProvider {

    ProviderResult charge(int orderAmount);
}
