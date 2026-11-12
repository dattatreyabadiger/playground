package payment_gateway.payment_providers;

import payment_gateway.model.ProviderResult;

public class FakePaymentProvider implements PaymentProvider {

    private final ProviderResult providerResult;

    public FakePaymentProvider(ProviderResult providerResult) {
        this.providerResult = providerResult;
    }

    @Override
    public ProviderResult charge(int orderAmount) {
        return this.providerResult;
    }
}
