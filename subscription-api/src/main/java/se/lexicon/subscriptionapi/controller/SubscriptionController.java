package se.lexicon.subscriptionapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<SubscriptionResponse> subscription(@RequestParam Long customerId, @Valid @RequestBody SubscriptionRequest subscriptionRequest) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(subscriptionService.subscribe(customerId, subscriptionRequest));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<SubscriptionResponse> subscribe(@PathVariable Long userId, @RequestBody SubscriptionRequest subscriptionRequest) {
        SubscriptionResponse subscriptionResponse = subscriptionService.subscribe(userId, subscriptionRequest);
        return ResponseEntity.ok(subscriptionResponse);
    }

    @DeleteMapping("/unsubscribe")
    public ResponseEntity<Void> unsubscribe(@RequestParam Long userId, @RequestParam Long planId) {
        subscriptionService.unsubscribe(userId, planId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<SubscriptionResponse>> findByCustomerId(@RequestParam Long customerId) {
        return ResponseEntity.ok(subscriptionService.findByCustomerId(customerId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<SubscriptionResponse>> findByIdAndCustomerId(@RequestParam Long id, @RequestParam Long customerId) {
        return ResponseEntity.ok(subscriptionService.findByIdAndCustomerId(id, customerId));
    }
}
