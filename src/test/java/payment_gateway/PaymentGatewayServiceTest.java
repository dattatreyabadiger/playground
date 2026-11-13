package payment_gateway;

import org.junit.jupiter.api.Test;
import payment_gateway.model.PaymentInfo;
import payment_gateway.model.PaymentMethod;
import payment_gateway.model.PaymentRequest;
import payment_gateway.model.PaymentStatus;
import payment_gateway.model.ProviderResult;
import payment_gateway.payment_providers.PaymentProvider;
import payment_gateway.repository.InMemoryPaymentProviderRepository;
import payment_gateway.repository.InMemoryPaymentRepository;
import payment_gateway.repository.PaymentProviderRepository;
import payment_gateway.repository.PaymentRepository;
import payment_gateway.service.PaymentGatewayService;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentGatewayServiceTest {

    private final PaymentGatewayService paymentGatewayService;

    public PaymentGatewayServiceTest() {
        var paymentMethodToProvidersMap = getPaymentMethodSetMap();

        PaymentRepository paymentRepository = new InMemoryPaymentRepository();
        PaymentProviderRepository paymentProviderRepository
                = new InMemoryPaymentProviderRepository(paymentMethodToProvidersMap);
        this.paymentGatewayService = new PaymentGatewayService(paymentRepository,
                                                               paymentProviderRepository);
    }

    private static Map<PaymentMethod, Set<PaymentProvider>> getPaymentMethodSetMap() {
        PaymentProvider provider1 = mock(PaymentProvider.class);
        when(provider1.charge(anyInt())).thenReturn(ProviderResult.SUCCESS);

        PaymentProvider provider2 = mock(PaymentProvider.class);
        when(provider2.charge(anyInt())).thenReturn(ProviderResult.TIMED_OUT);
        PaymentProvider provider3 = mock(PaymentProvider.class);
        when(provider3.charge(anyInt())).thenReturn(ProviderResult.DECLINED);

        return Map.of(PaymentMethod.CARD, Set.of(provider1, provider3),
                      PaymentMethod.UPI, Set.of(provider3),
                      PaymentMethod.WALLET, Set.of(provider3, provider2));
    }

    @Test
    public void test() {
        PaymentRequest paymentRequest1 = new PaymentRequest(1, 100, PaymentMethod.CARD, 1111);
        int paymentId1 = paymentGatewayService.makePayment(paymentRequest1);
        assertNotEquals(-1, paymentId1);
        int reAttemptPaymentId1 = paymentGatewayService.makePayment(paymentRequest1);
        assertEquals(reAttemptPaymentId1, paymentId1);

        PaymentRequest paymentRequest2 = new PaymentRequest(2, 100, PaymentMethod.UPI, 1111);
        int paymentId2 = paymentGatewayService.makePayment(paymentRequest2);
        PaymentInfo paymentInfo2 = paymentGatewayService.getPaymentInfo(paymentId2);
        assertEquals(PaymentStatus.FAILED, paymentInfo2.getPaymentStatus());

        PaymentRequest paymentRequest2WithCard = new PaymentRequest(2, 100, PaymentMethod.CARD, 1113);
        int anotherAttemptPaymentId2 = paymentGatewayService.makePayment(paymentRequest2WithCard);
        PaymentInfo anotherAttemptPaymentInfo2 = paymentGatewayService.getPaymentInfo(anotherAttemptPaymentId2);
        assertEquals(PaymentStatus.SUCCESS, anotherAttemptPaymentInfo2.getPaymentStatus());

        PaymentRequest paymentRequest3 = new PaymentRequest(3, 100, PaymentMethod.WALLET, 1112);
        int paymentId3 = paymentGatewayService.makePayment(paymentRequest3);
        PaymentInfo paymentInfo3 = paymentGatewayService.getPaymentInfo(paymentId3);
        assertEquals(PaymentStatus.UNKNOWN, paymentInfo3.getPaymentStatus());

        int paymentId3Retry = paymentGatewayService.makePayment(paymentRequest3);
        PaymentInfo paymentInfo3Retry = paymentGatewayService.getPaymentInfo(paymentId3Retry);
        assertEquals(paymentInfo3, paymentInfo3Retry);
    }
}
