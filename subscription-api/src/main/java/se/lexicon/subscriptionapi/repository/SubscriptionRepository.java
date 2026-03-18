package se.lexicon.subscriptionapi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.domain.entity.Subscription;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
       List<Subscription> findByCustomerId (Long customerId);
       List<Subscription> findByIdAndCustomerId(Long id, Long customerId);


}

