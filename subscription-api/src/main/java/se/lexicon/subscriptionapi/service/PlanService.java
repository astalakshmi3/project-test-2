package se.lexicon.subscriptionapi.service;

import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.dto.request.PlanRequest;
import se.lexicon.subscriptionapi.dto.response.PlanResponse;

import java.security.Provider;
import java.util.List;

public interface PlanService {
    PlanResponse createPlan(Plan planRequest);
    PlanResponse updatePlan(Long id,Plan planRequest);
    void deletePlan(Long id);
    List<PlanResponse> findAllActivePlan();
    List<PlanResponse> findAllPlanByServiceType(ServiceType serviceType);
    List<PlanResponse> findByOperatorIdAndActiveTrue(Long operatorId, Boolean active);

}
