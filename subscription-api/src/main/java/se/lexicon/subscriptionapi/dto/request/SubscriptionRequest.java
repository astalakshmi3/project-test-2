package se.lexicon.subscriptionapi.dto.request;

import jakarta.validation.constraints.NotNull;

public record SubscriptionRequest(
        @NotNull(message = "Plan id is required")
        Long planId,
        @NotNull(message = "Subscriber id is required")
        Long subscriberId,
        @NotNull(message = "Subscription name is required")
        String Name

) {
}
