package se.lexicon.subscriptionapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import se.lexicon.subscriptionapi.domain.entity.Customer;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.domain.entity.Subscription;
import se.lexicon.subscriptionapi.dto.request.SubscriptionRequest;
import se.lexicon.subscriptionapi.dto.response.SubscriptionResponse;
import se.lexicon.subscriptionapi.mapper.SubscriptionMapper;
import se.lexicon.subscriptionapi.repository.CustomerRepository;
import se.lexicon.subscriptionapi.repository.PlanRepository;
import se.lexicon.subscriptionapi.repository.SubscriptionRepository;
import se.lexicon.subscriptionapi.service.impl.SubscriptionServiceImpl;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceImplTest {
    @Mock
    SubscriptionRepository subscriptionRepository;
    @Mock
    private SubscriptionMapper subscriptionMapper;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PlanRepository planRepository;
    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;
    private Customer customer;
    private Plan plan;
    private Subscription subscription;
    private SubscriptionResponse subscriptionResponse;
    private SubscriptionResponse subscriptionResponse2;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setEmail("as@gmail.com");

        plan = new Plan();
        plan.setId(1L);
        plan.setName("Plan 1");
        plan.setPrice(BigDecimal.valueOf(100.0));

        subscription = new Subscription();
        subscription.setId(1L);
        subscription.setCustomer(customer);
        subscription.setPlan(plan);
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(2L, 1L, "as");
        subscriptionResponse = new SubscriptionResponse(1L, 1L, "asta@gmail.com", 1L, "Plan 1", null, null, null, null, null, null, null);
        
    }  
    @Test
    void saveSubscription() {

         SubscriptionResponse result = subscriptionService.subscribe(1L, new SubscriptionRequest(2L, 1L, "as"));

         assertNotNull (result);
    }

    }
