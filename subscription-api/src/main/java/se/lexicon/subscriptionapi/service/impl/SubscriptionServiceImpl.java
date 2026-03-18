package se.lexicon.subscriptionapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.subscriptionapi.domain.entity.Customer;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.domain.entity.Subscription;
import se.lexicon.subscriptionapi.dto.request.SubscriptionRequest;
import se.lexicon.subscriptionapi.dto.response.SubscriptionResponse;
import se.lexicon.subscriptionapi.exception.ResourceNotFoundException;
import se.lexicon.subscriptionapi.mapper.SubscriptionMapper;
import se.lexicon.subscriptionapi.repository.CustomerRepository;
import se.lexicon.subscriptionapi.repository.PlanRepository;
import se.lexicon.subscriptionapi.repository.SubscriptionRepository;
import se.lexicon.subscriptionapi.service.SubscriptionService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final CustomerRepository customerRepository;
    private final PlanRepository planRepository;

    @Override
    @Transactional
    public SubscriptionResponse subscribe(Long userId, SubscriptionRequest request) {
        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found with id: " + userId));

        Plan plan = planRepository.findById(request.planId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Plan not found with id: " + request.planId()));

        Subscription subscription = subscriptionMapper.toEntity(request);
        subscription.setCustomer(customer);
        subscription.setPlan(plan);
        subscription.setPlan(plan);

        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toResponse(savedSubscription);
    }

    @Override
    @Transactional
    public void unsubscribe(Long userId, Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Subscription not found with id: " + subscriptionId));

        if (!subscription.getCustomer().getId().equals(userId)) {
            throw new ResourceNotFoundException("Subscription not found for user id: " + userId);
        }

        subscriptionRepository.delete(subscription);
        subscriptionMapper.toResponse(subscription);
    }

    @Override
    public List<SubscriptionResponse> findByCustomerId(Long customerId) {
        return subscriptionRepository.findByCustomerId(customerId).stream()
                .map(subscriptionMapper::toResponse)
                .toList();
    }

    @Override
    public List<SubscriptionResponse> findByIdAndCustomerId(Long id, Long customerId) {
        return subscriptionRepository.findByIdAndCustomerId(id, customerId).stream()
                .map(subscriptionMapper::toResponse)
                .toList();
    }

}
