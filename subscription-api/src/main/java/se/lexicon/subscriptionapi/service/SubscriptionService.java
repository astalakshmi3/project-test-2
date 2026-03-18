package se.lexicon.subscriptionapi.service;

import se.lexicon.subscriptionapi.dto.request.SubscriptionRequest;
import se.lexicon.subscriptionapi.dto.response.SubscriptionResponse;

import java.util.List;

public interface SubscriptionService {
    SubscriptionResponse subscribe(Long userId, SubscriptionRequest subscriptionRequest);
    void unsubscribe(Long userId, Long SubscriptionId);
    List<SubscriptionResponse> findByCustomerId(Long customerId);
    List<SubscriptionResponse> findByIdAndCustomerId(Long id, Long customerId);
}
