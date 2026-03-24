package se.lexicon.subscriptionapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.domain.entity.Operator;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.dto.request.PlanRequest;
import se.lexicon.subscriptionapi.dto.response.PlanResponse;
import se.lexicon.subscriptionapi.mapper.PlanMapper;
import se.lexicon.subscriptionapi.repository.OperatorRepository;
import se.lexicon.subscriptionapi.repository.PlanRepository;
import se.lexicon.subscriptionapi.service.impl.PlanServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanServiceImplTest {

    @Mock
    private PlanRepository planRepository;

    @Mock
    private PlanMapper planMapper;

    @Mock
    private OperatorRepository operatorRepository;

    @InjectMocks
    private PlanServiceImpl planService;

    private PlanRequest planRequest;
    private Plan plan;
    private Operator operator;
    private PlanResponse planResponse;

    @BeforeEach
    void setUp() {
        operator = new Operator();
        operator.setId(1L);
        operator.setName("Telia");

        planRequest = new PlanRequest(
                        "Basic Internet",
                        BigDecimal.valueOf(299),
                true,
                ServiceType.INTERNET,
                        1L
                );

        plan = new Plan();
        plan.setId(10L);
        plan.setName("Basic Internet");
        plan.setPrice(BigDecimal.valueOf(299));
        plan.setServiceType(ServiceType.INTERNET);
        plan.setActive(true);
        plan.setOperator(operator);

        planResponse = new PlanResponse(
                10L,
                "Basic Internet",
                BigDecimal.valueOf(299),
                true,
                "INTERNET",
                1L,
                "Telia",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    void createPlan() {
        when(operatorRepository.findById(1L)).thenReturn(Optional.of(operator));
        when(planMapper.toEntity(planRequest)).thenReturn(plan);
        when(planRepository.save(plan)).thenReturn(plan);
        when(planMapper.toResponse(plan)).thenReturn(planResponse);

        PlanResponse result = planService.createPlan(planRequest);

        assertNotNull(result);
        assertEquals("Basic Internet", result.name());

        verify(operatorRepository).findById(1L);
        verify(planMapper).toEntity(planRequest);
        verify(planRepository).save(plan);
        verify(planMapper).toResponse(plan);
    }
}