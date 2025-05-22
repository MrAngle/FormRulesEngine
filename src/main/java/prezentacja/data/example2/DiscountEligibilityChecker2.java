package prezentacja.data.example2;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class DiscountEligibilityChecker2 {

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

    public DiscountDecision checkDiscount(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer must not be null");
        }

        List<DiscountRule> rules = List.of(
                new BlacklistRule(),
                new LastPurchaseRule(),
                new PlatinumRule(),
                new GoldHistoryRule(),
                new GoldPromoRule(),
                new SilverRule()
        );

        for (DiscountRule rule : rules) {
            Optional<DiscountDecision> result = rule.evaluate(customer);
            if (result.isPresent()) {
                return result.get();
            }
        }

        return new DiscountDecision(false, "No discount rules matched", 0.0);
    }

    interface DiscountRule {
        Optional<DiscountDecision> evaluate(Customer customer);
    }

    static class BlacklistRule implements DiscountRule {
        public Optional<DiscountDecision> evaluate(Customer customer) {
            if (customer.isBlacklisted) {
                return Optional.of(new DiscountDecision(false, "Customer is blacklisted", 0.0));
            }
            return Optional.empty();
        }
    }

    static class LastPurchaseRule implements DiscountRule {
        public Optional<DiscountDecision> evaluate(Customer customer) {
            if (customer.lastPurchaseDate.isBefore(LocalDate.now().minusDays(30))) {
                return Optional.of(new DiscountDecision(false, "Last purchase too old", 0.0));
            }
            return Optional.empty();
        }
    }

    static class PlatinumRule implements DiscountRule {
        public Optional<DiscountDecision> evaluate(Customer customer) {
            if (customer.level == LoyaltyLevel.PLATINUM) {
                return Optional.of(new DiscountDecision(true, "Platinum customer", 20.0));
            }
            return Optional.empty();
        }
    }

    static class GoldHistoryRule implements DiscountRule {
        public Optional<DiscountDecision> evaluate(Customer customer) {
            if (customer.level == LoyaltyLevel.GOLD &&
                    customer.totalSpent > 1000 &&
                    customer.numberOfOrders > 5) {
                return Optional.of(new DiscountDecision(true, "Gold loyalty + good history", 15.0));
            }
            return Optional.empty();
        }
    }

    static class GoldPromoRule implements DiscountRule {
        public Optional<DiscountDecision> evaluate(Customer customer) {
            if (customer.level == LoyaltyLevel.GOLD &&
                    customer.getPromoCode().filter(code -> code.equals("GOLD2024")).isPresent()) {
                return Optional.of(new DiscountDecision(true, "Gold promo code", 10.0));
            }
            return Optional.empty();
        }
    }

    static class SilverRule implements DiscountRule {
        public Optional<DiscountDecision> evaluate(Customer customer) {
            if (customer.level == LoyaltyLevel.SILVER && customer.totalSpent > 500) {
                return Optional.of(new DiscountDecision(true, "Silver loyalty + good spending", 5.0));
            }
            return Optional.empty();
        }
    }
}