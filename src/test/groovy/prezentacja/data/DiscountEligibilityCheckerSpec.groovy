package prezentacja.data

import prezentacja.data.example1.DiscountEligibilityChecker
import spock.lang.Specification
import java.time.LocalDate

class DiscountEligibilityCheckerSpec extends Specification {

    def "Test discount eligibility for various customers"() {
        given: "An instance of DiscountEligibilityChecker"
        def checker = new DiscountEligibilityChecker()

        and: "A customer with specific attributes"
        def customer = new DiscountEligibilityChecker.Customer(
                customerId,
                loyaltyLevel,
                totalSpent,
                numberOfOrders,
                lastPurchaseDate,
                isBlacklisted,
                promoCode
        )

        when: "Checking discount eligibility"
        def decision = checker.checkDiscount(customer)

        then: "The result should match the expected values"
        decision.isEligible == expectedEligibility
        decision.reason == expectedReason
        Math.abs(decision.discountPercentage - expectedDiscountPercentage) < 0.01

        where:
        customerId | loyaltyLevel                   | totalSpent | numberOfOrders | lastPurchaseDate            | isBlacklisted | promoCode    || expectedEligibility | expectedReason                | expectedDiscountPercentage
        "1"        | DiscountEligibilityChecker.LoyaltyLevel.NONE     | 0          | 0               | LocalDate.now().minusDays(10) | false         | null         || false               | "No discount rules matched"  | 0.0
        "2"        | DiscountEligibilityChecker.LoyaltyLevel.SILVER   | 600        | 3               | LocalDate.now().minusDays(5)  | false         | null         || true                | "Silver loyalty + good spending" | 5.0
        "3"        | DiscountEligibilityChecker.LoyaltyLevel.GOLD     | 1200       | 6               | LocalDate.now().minusDays(10) | false         | null         || true                | "Gold loyalty + good history" | 15.0
        "4"        | DiscountEligibilityChecker.LoyaltyLevel.GOLD     | 500        | 2               | LocalDate.now().minusDays(10) | false         | "GOLD2024"   || true                | "Gold promo code"            | 10.0
        "5"        | DiscountEligibilityChecker.LoyaltyLevel.PLATINUM | 2000       | 10              | LocalDate.now().minusDays(1)  | false         | null         || true                | "Platinum customer"          | 20.0
        "6"        | DiscountEligibilityChecker.LoyaltyLevel.GOLD     | 100        | 1               | LocalDate.now().minusDays(31) | false         | null         || false               | "Last purchase too old"      | 0.0
        "7"        | DiscountEligibilityChecker.LoyaltyLevel.PLATINUM | 5000       | 20              | LocalDate.now().minusDays(1)  | true          | null         || false               | "Customer is blacklisted"    | 0.0
    }
}