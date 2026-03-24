package se.lexicon.subscriptionapi.dto.response;

import se.lexicon.subscriptionapi.domain.constant.ServiceType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PlanResponse(
        Long id,
        String name,
        BigDecimal price,
        boolean active,
        String serviceType,
        Long operatorId,
        String operatorName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}
