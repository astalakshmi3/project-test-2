package se.lexicon.subscriptionapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.dto.request.SubscriptionRequest;
import se.lexicon.subscriptionapi.dto.response.SubscriptionResponse;
import se.lexicon.subscriptionapi.service.SubscriptionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;



    @PostMapping("/{customerId}")
    @Operation(summary = "Subscribe to a plan", description = "Subscribes a customer to a specific plan")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<SubscriptionResponse> subscribe(@PathVariable Long customerId, @Valid @RequestBody SubscriptionRequest subscriptionRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionService.subscribe(customerId, subscriptionRequest));
    }

    @PostMapping("/{subscriptionId}")
    @Operation(summary = "Unsubscribe from a plan", description = "Unsubscribes a customer from a specific plan")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> unsubscribe(@RequestParam Long userId, @RequestParam Long planId) {
        subscriptionService.unsubscribe(userId, planId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customer")
    @Operation(summary = "Get subscriptions by customer ID", description = "Retrieves a list of subscriptions associated with a specific customer ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<SubscriptionResponse>> findByCustomerId(@RequestParam Long customerId) {
        return ResponseEntity.ok(subscriptionService.findByCustomerId(customerId));
    }

    @GetMapping("/search")
    @Operation(summary = "Get subscriptions by subscription ID and customer ID", description = "Retrieves a list of subscriptions based on both subscription ID and customer ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<SubscriptionResponse>> findByIdAndCustomerId(@RequestParam Long id, @RequestParam Long customerId) {
        return ResponseEntity.ok(subscriptionService.findByIdAndCustomerId(id, customerId));
    }
}
