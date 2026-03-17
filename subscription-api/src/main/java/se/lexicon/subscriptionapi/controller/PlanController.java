package se.lexicon.subscriptionapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.dto.response.PlanResponse;
import se.lexicon.subscriptionapi.service.PlanService;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Tag(name = "Plan Controller", description = "Endpoints for managing subscription plans")
@RestController
@RequestMapping("/api/v1/plans")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PlanController {
    private final PlanService planService;

    @PostMapping
    @Operation(summary = "Create a new subscription plan", description = "Creates a new subscription plan with the provided details")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlanResponse> createPlan(@RequestBody Plan plan) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planService.createPlan(plan));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing subscription plan", description = "Updates the details of an existing subscription plan by its ID")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlanResponse> updatePlan(@PathVariable Long id, @RequestBody Plan planRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(planService.updatePlan(id, planRequest));
    }

    @DeleteMapping ("/{id}")
    @Operation(summary = "Delete a subscription plan", description = "Deletes an existing subscription plan by its ID")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/active")
    @Operation(summary = "Get all active subscription plans", description = "Retrieves a list of all active subscription plans")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<PlanResponse>> findAllActivePlan() {
        return ResponseEntity.ok(planService.findAllActivePlan());
    }

    @GetMapping("/operator/{operatorId}")
    @Operation(summary = "Get active subscription plans by operator ID", description = "Retrieves a list of active subscription plans associated with a specific operator ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<PlanResponse>> findByOperatorIdAndActiveTrue(@PathVariable String operatorId, @RequestParam Boolean active) {
        return ResponseEntity.ok(planService.findByOperatorIdAndActiveTrue(Long.valueOf(operatorId), active));
    }
}
