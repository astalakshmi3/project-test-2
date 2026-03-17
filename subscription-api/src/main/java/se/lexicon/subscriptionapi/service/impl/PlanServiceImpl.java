package se.lexicon.subscriptionapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.domain.entity.Operator;
import se.lexicon.subscriptionapi.domain.entity.Plan;
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
    public PlanResponse createPlan( Plan planRequest) {
        if (planRepository.existsById(planRequest.getId())){
            throw new ResourceNotFoundException("Plan already exists with operator id: " + planRequest.getId());
        }
        Plan plan = planMapper.toEntity(planRequest);
        Operator operator = operatorRepository.findById(planRequest.getOperator().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Operator not found with id: " + planRequest.getOperator().getId()));
        plan.setOperator(operator);
        Plan savedPlan = planRepository.save(plan);
        return planMapper.toResponse(savedPlan);

    }

    @Override
    @Transactional
    public PlanResponse updatePlan(Long id, Plan planRequest) {
        if (planRepository.existsById(id)) {
            throw new ResourceNotFoundException("Plan already exists with id: " + id);
        }
        Plan plan = planMapper.toEntity(planRequest);
        plan.setId(id);
        Operator operator = operatorRepository.findById(planRequest.getOperator().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Operator not found with id: " + planRequest.getOperator().getId()));
        plan.setOperator(operator);
        Plan updatedPlan = planRepository.save(plan);
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