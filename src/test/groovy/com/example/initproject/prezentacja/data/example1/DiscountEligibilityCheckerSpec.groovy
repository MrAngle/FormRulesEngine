package com.example.initproject.prezentacja.data.example1

import prezentacja.data.example1.DiscountEligibilityChecker
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class DiscountEligibilityCheckerSpec extends Specification {

    def discountEligibilityChecker = new DiscountEligibilityChecker()

    @Unroll
    def "Should return discount eligibility for a customer with loyalty level #loyaltyLevel"() {
        given: "A customer with specific attributes"
        def customer = new DiscountEligibilityChecker.Customer(
            "123", loyaltyLevel, totalSpent, numberOfOrders, lastPurchaseDate, isBlacklisted, promoCode
        )

        when: "Checking discount eligibility"
        def result = discountEligibilityChecker.checkDiscount(customer)

        then: "The decision is as expected"
        result.isEligible == expectedEligible
        result.reason == expectedReason
        result.discountPercentage == expectedDiscount

        where:
        loyaltyLevel       | totalSpent | numberOfOrders | lastPurchaseDate                         | isBlacklisted | promoCode   | expectedEligible | expectedReason                     | expectedDiscount
        DiscountEligibilityChecker.LoyaltyLevel.NONE     | 0          | 0              | LocalDate.now()                              | false         | null        | false            | "No discount rules matched"       | 0.0
        DiscountEligibilityChecker.LoyaltyLevel.SILVER   | 600        | 2              | LocalDate.now().minusDays(10)               | false         | null        | true             | "Silver loyalty + good spending"  | 5.0
        DiscountEligibilityChecker.LoyaltyLevel.GOLD     | 1100       | 6              | LocalDate.now().minusDays(5)                | false         | null        | true             | "Gold loyalty + good history"     | 15.0
        DiscountEligibilityChecker.LoyaltyLevel.GOLD     | 800        | 4              | LocalDate.now().minusDays(5)                | false         | "GOLD2024"  | true             | "Gold promo code"                 | 10.0
        DiscountEligibilityChecker.LoyaltyLevel.GOLD     | 800        | 4              | LocalDate.now().minusDays(5)                | false         | "SILVER2024"| false            | "No discount rules matched"       | 0.0
        DiscountEligibilityChecker.LoyaltyLevel.PLATINUM | 500        | 3              | LocalDate.now().minusDays(5)                | false         | null        | true             | "Platinum customer"               | 20.0
        DiscountEligibilityChecker.LoyaltyLevel.PLATINUM | 500        | 3              | LocalDate.now().minusDays(40)               | false         | null        | false            | "Last purchase too old"           | 0.0
        DiscountEligibilityChecker.LoyaltyLevel.PLATINUM | 500        | 3              | LocalDate.now().minusDays(5)                | true          | null        | false            | "Customer is blacklisted"         | 0.0
    }

    def "Should handle null promo code without exceptions"() {
        given: "A customer with a null promo code"
        def customer = new DiscountEligibilityChecker.Customer(
            "123", DiscountEligibilityChecker.LoyaltyLevel.GOLD, 800, 4, LocalDate.now(), false, null
        )

        when: "Checking discount eligibility"
        def result = discountEligibilityChecker.checkDiscount(customer)

        then: "The result is no discount"
        !result.isEligible
        result.reason == "No discount rules matched"
        result.discountPercentage == 0.0
    }

    def "Should handle null customer and throw an exception"() {
        when: "Checking discount eligibility for a null customer"
        discountEligibilityChecker.checkDiscount(null)

        then: "An exception is thrown"
        thrown(NullPointerException)
    }

    @Unroll
    def "Should handle edge cases for totalSpent = #totalSpent, numberOfOrders = #numberOfOrders"() {
        given: "A customer close to the discount eligibility threshold"
        def customer = new DiscountEligibilityChecker.Customer(
            "123", DiscountEligibilityChecker.LoyaltyLevel.GOLD, totalSpent, numberOfOrders, LocalDate.now(), false, null
        )

        when: "Checking discount eligibility"
        def result = discountEligibilityChecker.checkDiscount(customer)

        then: "The decision matches expectations"
        result.isEligible == expectedEligible
        result.reason == expectedReason
        result.discountPercentage == expectedDiscount

        where:
        totalSpent | numberOfOrders | expectedEligible | expectedReason                | expectedDiscount
        999.99     | 5              | false            | "No discount rules matched"  | 0.0
        1000.01    | 6              | true             | "Gold loyalty + good history"| 15.0
    }

    @Unroll
    def "Should handle customers with various loyalty levels and promo codes"() {
        given: "A customer with specific attributes including promo code"
        def customer = new DiscountEligibilityChecker.Customer(
                "123", loyaltyLevel, totalSpent, numberOfOrders, lastPurchaseDate, isBlacklisted, promoCode
        )

        when: "Checking discount eligibility"
        def result = discountEligibilityChecker.checkDiscount(customer)

        then: "The decision matches the expected outcome"
        result.isEligible == expectedEligible
        result.reason == expectedReason
        result.discountPercentage == expectedDiscount

        where:
        loyaltyLevel       | totalSpent | numberOfOrders | lastPurchaseDate       | isBlacklisted | promoCode   | expectedEligible | expectedReason                 | expectedDiscount
        DiscountEligibilityChecker.LoyaltyLevel.SILVER   | 500        | 3              | LocalDate.now().minusDays(10) | false         | "SILVER2023" | false            | "No discount rules matched"   | 0.0
        DiscountEligibilityChecker.LoyaltyLevel.SILVER   | 700        | 3              | LocalDate.now().minusDays(15) | false         | null         | true             | "Silver loyalty + good spending"| 5.0
        DiscountEligibilityChecker.LoyaltyLevel.GOLD     | 300        | 3              | LocalDate.now()               | false         | "GOLD2024"  | true             | "Gold promo code"             | 10.0
        DiscountEligibilityChecker.LoyaltyLevel.GOLD     | 300        | 3              | LocalDate.now()               | false         | null         | false            | "No discount rules matched"   | 0.0
        DiscountEligibilityChecker.LoyaltyLevel.PLATINUM | 0          | 0              | LocalDate.now()               | false         | "ANYCODE"   | true             | "Platinum customer"           | 20.0
    }

    def "Should return no discount for blacklisted customers regardless of parameters"() {
        given: "A blacklisted customer"
        def customer = new DiscountEligibilityChecker.Customer(
                "123", DiscountEligibilityChecker.LoyaltyLevel.GOLD, 1500, 10, LocalDate.now().minusDays(5), true, null
        )

        when: "Checking discount eligibility"
        def result = discountEligibilityChecker.checkDiscount(customer)

        then: "The customer is not eligible for a discount due to being blacklisted"
        !result.isEligible
        result.reason == "Customer is blacklisted"
        result.discountPercentage == 0.0
    }

    def "Should return no discount for stale purchase date beyond 30 days"() {
        given: "A customer with a last purchase date over 30 days ago"
        def customer = new DiscountEligibilityChecker.Customer(
                "123", DiscountEligibilityChecker.LoyaltyLevel.SILVER, 600, 2, LocalDate.now().minusDays(31), false, null
        )

        when: "Checking discount eligibility"
        def result = discountEligibilityChecker.checkDiscount(customer)

        then: "The customer is not eligible for a discount due to last purchase being too old"
        !result.isEligible
        result.reason == "Last purchase too old"
        result.discountPercentage == 0.0
    }

    @Unroll
    def "Should handle edge cases for loyalty levels #loyaltyLevel near spending threshold"() {
        given: "A customer close to the spending threshold for their loyalty level"
        def customer = new DiscountEligibilityChecker.Customer(
                "123", loyaltyLevel, totalSpent, 5, LocalDate.now(), false, null
        )

        when: "Checking discount eligibility"
        def result = discountEligibilityChecker.checkDiscount(customer)

        then: "The discount decision is as expected"
        result.isEligible == expectedEligible
        result.reason == expectedReason
        result.discountPercentage == expectedDiscount

        where:
        loyaltyLevel       | totalSpent | expectedEligible | expectedReason                     | expectedDiscount
        DiscountEligibilityChecker.LoyaltyLevel.SILVER   | 499.99     | false            | "No discount rules matched"       | 0.0
        DiscountEligibilityChecker.LoyaltyLevel.SILVER   | 500.0      | true             | "Silver loyalty + good spending"  | 5.0
        DiscountEligibilityChecker.LoyaltyLevel.GOLD     | 999.99     | false            | "No discount rules matched"       | 0.0
        DiscountEligibilityChecker.LoyaltyLevel.GOLD     | 1000.01    | true             | "Gold loyalty + good history"     | 15.0
    }

    def "Should gracefully handle customers with no orders but GOLD level with promo code"() {
        given: "A customer with GOLD loyalty level and promo code but no orders"
        def customer = new DiscountEligibilityChecker.Customer(
                "123", DiscountEligibilityChecker.LoyaltyLevel.GOLD, 0, 0, LocalDate.now(), false, "GOLD2024"
        )

        when: "Checking discount eligibility"
        def result = discountEligibilityChecker.checkDiscount(customer)

        then: "The customer is eligible for a discount due to the promo code"
        result.isEligible
        result.reason == "Gold promo code"
        result.discountPercentage == 10.0
    }

    def "Should handle null lastPurchaseDate and fail gracefully"() {
        given: "A customer with a null lastPurchaseDate"
        def customer = new DiscountEligibilityChecker.Customer(
                "123", DiscountEligibilityChecker.LoyaltyLevel.GOLD, 1000, 5, null, false, null
        )

        when: "Checking discount eligibility"
        def result = discountEligibilityChecker.checkDiscount(customer)

        then: "An exception is thrown due to invalid data"
        thrown(NullPointerException)
    }

    @Unroll
    def "Should handle invalid parameters like totalSpent=#totalSpent, numberOfOrders=#numberOfOrders"() {
        given: "A customer with edge case values (e.g., negative spending or orders)"
        def customer = new DiscountEligibilityChecker.Customer(
                "123", DiscountEligibilityChecker.LoyaltyLevel.GOLD, totalSpent, numberOfOrders, LocalDate.now(), false, null
        )

        when: "Checking discount eligibility"
        def result = discountEligibilityChecker.checkDiscount(customer)

        then: "The decision matches expectations for invalid values"
        result.isEligible == expectedEligible
        result.reason == expectedReason
        result.discountPercentage == expectedDiscount

        where:
        totalSpent | numberOfOrders | expectedEligible | expectedReason                     | expectedDiscount
        -100       | 5              | false            | "No discount rules matched"       | 0.0
        1000       | -1             | false            | "No discount rules matched"       | 0.0
        10000000   | 1000           | true             | "Gold loyalty + good history"     | 15.0
    }

}