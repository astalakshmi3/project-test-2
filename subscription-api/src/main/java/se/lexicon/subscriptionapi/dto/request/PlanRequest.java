package se.lexicon.subscriptionapi.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import se.lexicon.subscriptionapi.domain.constant.ServiceType;

import java.math.BigDecimal;

public record PlanRequest(
        @NotBlank(message = "Plan name is required")
        String name,
        @NotBlank(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false,message = "Price must be greater than 0")
        BigDecimal price,
        @NotNull(message = "Active status is required")
        boolean active,
        @NotNull(message = "Service type is required")
        ServiceType serviceType,
        @NotNull(message = "Operator id is required")
        Long operatorId
) {
}
