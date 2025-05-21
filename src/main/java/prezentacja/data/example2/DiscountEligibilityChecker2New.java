package prezentacja.data.example2;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
class DiscountEligibilityChecker2New {

    public enum LoyaltyLevel { NONE, SILVER, GOLD, PLATINUM }

    @Getter
    @AllArgsConstructor
    public static class Customer {
        private final String id;
        private final LoyaltyLevel level;
        private final double totalSpent;
        private final int numberOfOrders;
        private final LocalDate lastPurchaseDate;
        private final boolean isBlacklisted;
        private final String promoCode;

        public Optional<String> getPromoCode() {
            return Optional.ofNullable(promoCode);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class DiscountDecision {
        private final boolean isEligible;
        private final String reason;
        private final double discountPercentage;
    }

    @AllArgsConstructor
    private static class DiscountRule {
        private final Predicate<Customer> condition;
        private final Function<Customer, DiscountDecision> decision;
    }

    private final List<DiscountRule> rules = List.of(
            new DiscountRule(
                    c -> c.isBlacklisted,
                    c -> new DiscountDecision(false, "Customer is blacklisted", 0.0)
            ),
            new DiscountRule(
                    c -> c.lastPurchaseDate.isBefore(LocalDate.now().minusDays(30)),
                    c -> new DiscountDecision(false, "Last purchase too old", 0.0)
            ),
            new DiscountRule(
                    c -> c.level == LoyaltyLevel.PLATINUM,
                    c -> new DiscountDecision(true, "Platinum customer", 20.0)
            ),
            new DiscountRule(
                    c -> c.level == LoyaltyLevel.GOLD &&
                            c.totalSpent > 1000 &&
                            c.numberOfOrders > 5,
                    c -> new DiscountDecision(true, "Gold loyalty + good history", 15.0)
            ),
            new DiscountRule(
                    c -> c.level == LoyaltyLevel.GOLD &&
                            c.getPromoCode().filter(code -> code.equals("GOLD2024")).isPresent(),
                    c -> new DiscountDecision(true, "Gold promo code", 10.0)
            ),
            new DiscountRule(
                    c -> c.level == LoyaltyLevel.SILVER && c.totalSpent > 500,
                    c -> new DiscountDecision(true, "Silver loyalty + good spending", 5.0)
            )
    );

    public DiscountDecision checkDiscount(Customer customer) {
        return rules.stream()
                .filter(rule -> rule.condition.test(customer))
                .findFirst()
                .map(rule -> rule.decision.apply(customer))
                .orElseGet(() -> new DiscountDecision(false, "No discount rules matched", 0.0));
    }
}
