package se.lexicon.subscriptionapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se.lexicon.subscriptionapi.domain.constant.Role;
import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.domain.entity.Customer;
import se.lexicon.subscriptionapi.domain.entity.Operator;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.repository.CustomerRepository;
import se.lexicon.subscriptionapi.repository.OperatorRepository;
import se.lexicon.subscriptionapi.repository.PlanRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;



@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final OperatorRepository operatorRepository;
    private final PlanRepository planRepository;

    @Override
    public void run(String... args) throws Exception {
        seedAdminUser();
        seedRegularUser();
        seedOperatorsAndPlans();
    }

    private void seedOperatorsAndPlans() {
        if (operatorRepository.count() > 0) {
            return;
        }
        Operator telia = new Operator();
        telia.setName("Telia");
        telia.setCreatedAt(LocalDateTime.now());
        telia.setUpdatedAt(LocalDateTime.now());
        operatorRepository.save(telia);

        Operator telenor =  new Operator();
        telenor.setName("Lenor");
        telenor.setCreatedAt(LocalDateTime.now());
        telenor.setUpdatedAt(LocalDateTime.now());
        operatorRepository.save(telenor);

        // Telia Plans
        planRepository.save(createPlan("Fiber 50", 299, ServiceType.INTERNET, null, true, telia));
        planRepository.save(createPlan("Fiber 100", 399, ServiceType.INTERNET, null, true, telia));
        planRepository.save(createPlan("Mobile Basic", 199, ServiceType.MOBILE, 10, true, telia));
        planRepository.save(createPlan("Mobile Unlimited", 499, ServiceType.MOBILE, null, false, telia)); // inactive

        // Telenor Plans
        planRepository.save(createPlan("Fiber 300", 549, ServiceType.INTERNET, null, true, telenor));
        planRepository.save(createPlan("Mobile Plus", 299, ServiceType.MOBILE, 30, true, telenor));
        System.out.println("[DATA_SEED] Operators and plans seeded");


    }
    private Plan createPlan(String name, int price, ServiceType serviceType, Integer dataLimit, boolean active, Operator operator) {
        Plan plan = new Plan();
        plan.setName(name);
        plan.setPrice(BigDecimal.valueOf(price));
        plan.setServiceType(serviceType);
        plan.setActive(active);
        plan.setOperator(operator);
        plan.setCreatedAt(LocalDateTime.now());
        plan.setUpdatedAt(LocalDateTime.now());
        return plan;
    }

    private void seedAdminUser() {
        String adminEmail = "admin@example.com";
        if (!customerRepository.existsByEmail(adminEmail)) {
            Customer admin = new Customer();
            admin.setEmail(adminEmail);
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setCreatedAt(LocalDateTime.now());
            admin.setUpdatedAt(LocalDateTime.now());
            admin.setRoles(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER));
            customerRepository.save(admin);
            System.out.println("[DATA_SEED] Admin user created: " + adminEmail);
        }
    }

    private void seedRegularUser() {
        String userEmail = "user@example.com";
        if (!customerRepository.existsByEmail(userEmail)) {
            Customer user = new Customer();
            user.setEmail(userEmail);
            user.setFirstName("User");
            user.setLastName("Userson");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRoles(Set.of(Role.ROLE_USER));
            customerRepository.save(user);
            System.out.println("[DATA_SEED] Regular user created: " + userEmail);
        }
    }

}
