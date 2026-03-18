package se.lexicon.subscriptionapi.mapper;

import org.mapstruct.Mapper;
import se.lexicon.subscriptionapi.domain.entity.Subscription;
import se.lexicon.subscriptionapi.dto.request.SubscriptionRequest;
import se.lexicon.subscriptionapi.dto.response.SubscriptionResponse;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    Subscription toEntity (SubscriptionRequest subscriptionRequest);
        SubscriptionResponse toResponse(Subscription subscription);
}
