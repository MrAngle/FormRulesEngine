package com.example.initproject.prezentacja.data.example2


import prezentacja.data.example2.DiscountEligibilityChecker2New.Customer
import prezentacja.data.example2.DiscountEligibilityChecker2New.LoyaltyLevel
import prezentacja.data.example2.DiscountEligibilityChecker2New
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class DiscountEligibilityChecker2NewSpec extends Specification {

    def checker = new DiscountEligibilityChecker2New()

    @Unroll
    def "Customer with ID #id and description: #description should receive expected discount decision"() {
        given: "A customer object with specific properties"
        def customer = new Customer(
                id,
                loyaltyLevel,
                totalSpent,
                numberOfOrders,
                lastPurchaseDate,
                isBlacklisted,
                promoCode
        )

        when: "Discount eligibility is checked"
        def decision = checker.checkDiscount(customer)

        then: "Decision matches expected eligibility and discount percentage"
        decision.isEligible == expectedEligibility
        decision.reason == expectedReason
        Math.abs(decision.discountPercentage - expectedDiscountPercentage) < 0.01

        where:
        id          | loyaltyLevel        | totalSpent | numberOfOrders | lastPurchaseDate           | isBlacklisted | promoCode    || expectedEligibility | expectedReason                   | expectedDiscountPercentage | description
        "CUST-001"  | LoyaltyLevel.NONE   | 0          | 0              | LocalDate.now().minusDays(10) | false         | null         || false               | "No discount rules matched"      | 0.0                        | "no loyalty level"
        "CUST-002"  | LoyaltyLevel.SILVER | 600        | 2              | LocalDate.now().minusDays(5)  | false         | null         || true                | "Silver loyalty + good spending" | 5.0                        | "silver customer with enough spending"
        "CUST-003"  | LoyaltyLevel.GOLD   | 1200       | 6              | LocalDate.now().minusDays(5)  | false         | null         || true                | "Gold loyalty + good history"    | 15.0                       | "gold customer with high spending and orders"
        "CUST-004"  | LoyaltyLevel.GOLD   | 800        | 2              | LocalDate.now().minusDays(31) | false         | null         || false               | "Last purchase too old"          | 0.0                        | "gold customer with outdated purchase"
        "CUST-005"  | LoyaltyLevel.PLATINUM | 0        | 0              | LocalDate.now().minusDays(1)  | false         | null         || true                | "Platinum customer"              | 20.0                       | "platinum level"
        "CUST-006"  | LoyaltyLevel.GOLD   | 500        | 2              | LocalDate.now().minusDays(5)  | false         | "GOLD2024"   || true                | "Gold promo code"                | 10.0                       | "gold customer with valid promoCode"
        "CUST-007"  | LoyaltyLevel.PLATINUM | 2000     | 20             | LocalDate.now().minusDays(1)  | true          | null         || false               | "Customer is blacklisted"        | 0.0                        | "blacklisted platinum customer"
    }

    def "Gold customer exactly on boundary of orders and spending does not qualify for discount"() {
        given: "A gold customer with boundary values"
        def customer = new Customer(
                "BOUNDARY-GOLD",
                LoyaltyLevel.GOLD,
                1000.0, // Exactly at spending threshold
                5,      // Exactly at order threshold
                LocalDate.now(),
                false,
                null
        )

        when: "Discount eligibility is checked"
        def decision = checker.checkDiscount(customer)

        then: "Customer does not qualify for gold history discount"
        !decision.isEligible
        decision.reason == "No discount rules matched"
        decision.discountPercentage == 0.0
    }

    def "Blacklisted customer should never qualify for discount"() {
        given: "A blacklisted customer"
        def customer = new Customer(
                "BLACKLISTED-CUST",
                LoyaltyLevel.SILVER,
                1000.0,
                10,
                LocalDate.now(),
                true, // Blacklisted
                null
        )

        when: "Discount eligibility is checked"
        def decision = checker.checkDiscount(customer)

        then: "Customer is not eligible regardless of other attributes"
        !decision.isEligible
        decision.reason == "Customer is blacklisted"
        decision.discountPercentage == 0.0
    }

    def "Null customer object should trigger validation failure"() {
        when: "Null is passed to checkDiscount"
        checker.checkDiscount(null)

        then: "IllegalArgumentException is thrown indicating invalid input"
        def e = thrown(IllegalArgumentException)
        e.message == "Customer cannot be null"

        /* TODO: Add check for null customer in method:
           if (customer == null) throw new IllegalArgumentException("Customer cannot be null");
         */
    }

    @Unroll
    def "PromoCode validation with invalid or incorrectly formatted codes - #promoCode"() {
        given: "A customer with improperly formatted promoCode"
        def customer = new Customer(
                "PROMO-CHECK",
                LoyaltyLevel.GOLD,
                800.0,
                4,
                LocalDate.now(),
                false,
                promoCode // Incorrectly formatted promoCode
        )

        when: "Discount eligibility is checked"
        def decision = checker.checkDiscount(customer)

        then: "Discount is not applied due to incorrect promoCode"
        !decision.isEligible
        decision.reason == "No discount rules matched"
        decision.discountPercentage == 0.0

        where:
        promoCode << [" gold2024", "GOLD2024 ", "GOLD_2024", "gold2024"]

        /* TODO: Consider adding logic for lenient promoCode validation:
           - Allow case-insensitive matches
           - Trim leading/trailing whitespace
         */
    }
}