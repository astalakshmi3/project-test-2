package se.lexicon.subscriptionapi.dto.response;

import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.domain.constant.SubscriptionStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SubscriptionResponse(
        Long id,
        Long userId,
        String customerEmail,
        Long planId,
        String planeName,
        ServiceType serviceType,
        String operatorName,
        SubscriptionStatus subscriptionStatus,
        LocalDate startDate,
        LocalDate cancelledAt,
        LocalDateTime createdAt,
        LocalDateTime updateAt
) {
}
