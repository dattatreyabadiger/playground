package payment_gateway.repository;

import payment_gateway.model.PaymentMethod;
import payment_gateway.payment_providers.PaymentProvider;

import java.util.Map;
import java.util.Set;

public class InMemoryPaymentProviderRepository implements PaymentProviderRepository {

    private final Map<PaymentMethod, Set<PaymentProvider>> paymentTypeToProvidersMap;

    public InMemoryPaymentProviderRepository(Map<PaymentMethod, Set<PaymentProvider>> paymentTypeToProvidersMap) {
        this.paymentTypeToProvidersMap = paymentTypeToProvidersMap;
    }

    @Override
    public Set<PaymentProvider> getSupportedPaymentMethods(PaymentMethod paymentMethod) {
        Set<PaymentProvider> paymentProviders = paymentTypeToProvidersMap.get(paymentMethod);
        if (paymentProviders == null)
            throw new IllegalArgumentException("Invalid Payment provider");
        return paymentProviders;
    }
}
