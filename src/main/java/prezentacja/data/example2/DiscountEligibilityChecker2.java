package prezentacja.data.example2;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
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
        if (customer.isBlacklisted) {
            return new DiscountDecision(false, "Customer is blacklisted", 0.0);
        }

        if (customer.lastPurchaseDate.isBefore(LocalDate.now().minusDays(30))) {
            return new DiscountDecision(false, "Last purchase too old", 0.0);
        }

        switch (customer.level) {
            case PLATINUM:
                return new DiscountDecision(true, "Platinum customer", 20.0);

            case GOLD:
                if (customer.totalSpent > 1000 && customer.numberOfOrders > 5) {
                    return new DiscountDecision(true, "Gold loyalty + good history", 15.0);
                }
                if (customer.getPromoCode().filter(code -> code.equals("GOLD2024")).isPresent()) {
                    return new DiscountDecision(true, "Gold promo code", 10.0);
                }
                break;

            case SILVER:
                if (customer.totalSpent > 500) {
                    return new DiscountDecision(true, "Silver loyalty + good spending", 5.0);
                }
                break;

            case NONE:
            default:
                break;
        }

        return new DiscountDecision(false, "No discount rules matched", 0.0);
    }
}
