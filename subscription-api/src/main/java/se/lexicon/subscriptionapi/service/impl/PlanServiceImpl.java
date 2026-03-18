package se.lexicon.subscriptionapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.domain.entity.Operator;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.dto.request.PlanRequest;
import se.lexicon.subscriptionapi.dto.response.PlanResponse;
import se.lexicon.subscriptionapi.exception.ResourceNotFoundException;
import se.lexicon.subscriptionapi.mapper.PlanMapper;
import se.lexicon.subscriptionapi.repository.OperatorRepository;
import se.lexicon.subscriptionapi.repository.PlanRepository;
import se.lexicon.subscriptionapi.service.PlanService;

import java.util.List;

import static java.util.Locale.filter;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;
    private final PlanMapper planMapper;
    private final OperatorRepository operatorRepository;


    @Override
    @Transactional
    public PlanResponse createPlan( PlanRequest planRequest) {

        Plan plan = planMapper.toEntity(planRequest);
        Operator operator = operatorRepository.findById(planRequest.operatorId())
                .orElseThrow(() -> new ResourceNotFoundException("Operator not found with id: " + planRequest.operatorId()));
        plan.setOperator(operator);
        Plan savedPlan = planRepository.save(plan);
        return planMapper.toResponse(savedPlan);
    }

    @Override
    @Transactional
    public PlanResponse updatePlan(Long id, PlanRequest planRequest) {
        Plan existingPlan = planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found with id: " + id));

        existingPlan.setName(planRequest.name());
        existingPlan.setPrice(planRequest.price());
        existingPlan.setServiceType(planRequest.serviceType());
        existingPlan.setActive(planRequest.active());
        Operator operator = operatorRepository.findById(planRequest.operatorId())
                .orElseThrow(() -> new ResourceNotFoundException("Operator not found with id: " + planRequest.operatorId()));
        existingPlan.setOperator(operator);
        Plan updatedPlan = planRepository.save(existingPlan);
        return planMapper.toResponse(updatedPlan);

    }


    @Override
    @Transactional
    public void deletePlan(Long id) {
        if (planRepository.existsById(id)){
            throw new ResourceNotFoundException("Plan already exists with id: " + id);
        }
        planRepository.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanResponse> findAllActivePlan() {
        return planRepository.findByActiveTrue().stream()
                .filter(Plan::isActive)
                .map(planMapper::toResponse)
                .toList();

    }

    @Override
    public List<PlanResponse> findAllPlanByServiceType(ServiceType serviceType) {
        return planRepository.findByActiveTrueAndServiceType(serviceType).stream()
                .filter(plan -> plan.getServiceType().equals(serviceType))
                .map(planMapper::toResponse)
                .toList();
    }

    @Override
    public List<PlanResponse> findByOperatorIdAndActiveTrue(Long operatorId, Boolean active) {
        return planRepository.findByOperatorIdAndActiveTrue(operatorId).stream()
                .filter(plan -> plan.getOperator().getId().equals(operatorId))
                .map(planMapper::toResponse)
                .toList();
    }
}