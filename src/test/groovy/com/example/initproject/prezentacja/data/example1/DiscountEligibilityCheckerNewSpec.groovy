//package com.example.initproject.prezentacja.data.example1
//
//import prezentacja.data.example1.DiscountEligibilityCheckerNew
//import prezentacja.data.example1.DiscountEligibilityCheckerNew.Customer
//import prezentacja.data.example1.DiscountEligibilityCheckerNew.LoyaltyLevel
//import spock.lang.Specification
//import spock.lang.Unroll
//
//import java.time.LocalDate
//
//class DiscountEligibilityCheckerNewSpec extends Specification {
//
//    def checker = new DiscountEligibilityCheckerNew()
//
//    @Unroll
//    def "Customer with ID #id and #description should have expected discount decision"() {
//        given: "A customer object with specific attributes"
//        def customer = new Customer(
//                id,
//                loyaltyLevel,
//                totalSpent,
//                numberOfOrders,
//                lastPurchaseDate,
//                isBlacklisted,
//                promoCode
//        )
//
//        when: "The checkDiscount method is called"
//        def decision = checker.checkDiscount(customer)
//
//        then: "The eligibility and discount decision match the expectations"
//        decision.isEligible == expectedEligibility
//        decision.reason == expectedReason
//        Math.abs(decision.discountPercentage - expectedDiscountPercentage) < 0.01
//
//        where:
//        id        | loyaltyLevel            | totalSpent | numberOfOrders | lastPurchaseDate            | isBlacklisted | promoCode    || expectedEligibility | expectedReason                   | expectedDiscountPercentage | description
//        "CUST-001" | LoyaltyLevel.NONE       | 0          | 0              | LocalDate.now().minusDays(10) | false         | null         || false               | "No discount rules matched"      | 0.0                        | "no loyalty level"
//        "CUST-002" | LoyaltyLevel.SILVER     | 600        | 2              | LocalDate.now().minusDays(5)  | false         | null         || true                | "Silver loyalty + good spending" | 5.0                        | "silver level with enough spending"
//        "CUST-003" | LoyaltyLevel.GOLD       | 1200       | 6              | LocalDate.now().minusDays(5)  | false         | null         || true                | "Gold loyalty + good history"    | 15.0                       | "gold level with high spending"
//        "CUST-004" | LoyaltyLevel.PLATINUM   | 0          | 0              | LocalDate.now()               | false         | null         || true                | "Platinum customer"              | 20.0                       | "platinum customer"
//        "CUST-005" | LoyaltyLevel.GOLD       | 800        | 2              | LocalDate.now().minusDays(31) | false         | null         || false               | "Last purchase too old"          | 0.0                        | "gold level customer with outdated purchase"
//        "CUST-006" | LoyaltyLevel.GOLD       | 500        | 2              | LocalDate.now().minusDays(5)  | false         | "GOLD2024"   || true                | "Gold promo code"                | 10.0                       | "gold level customer with valid promoCode"
//        "CUST-007" | LoyaltyLevel.PLATINUM   | 3000       | 15             | LocalDate.now().minusDays(1)  | true          | null         || false               | "Customer is blacklisted"        | 0.0                        | "blacklisted customer with platinum level"
//    }
//
//    def "Customer with null lastPurchaseDate should fail indicating missing validation"() {
//        given: "A customer with null lastPurchaseDate"
//        def customer = new Customer(
//                "NULL-DATE-CUST",
//                LoyaltyLevel.GOLD,
//                1000,
//                5,
//                null,
//                false,
//                null
//        )
//
//        when: "Calling checkDiscount with null date"
//        checker.checkDiscount(customer)
//
//        then: "Test fails as null lastPurchaseDate is unhandled in logic"
//        fail("Last purchase date cannot be null. Include validation in the logic.")
//    }
//
//    def "Customer with null loyaltyLevel should default to no discount"() {
//        given: "A customer with null loyalty level"
//        def customer = new Customer(
//                "NULL-LOYALTY-CUST",
//                null,
//                500,
//                1,
//                LocalDate.now().minusDays(5),
//                false,
//                null
//        )
//
//        when: "Calling checkDiscount with null loyaltyLevel"
//        def decision = checker.checkDiscount(customer)
//
//        then: "Customer gets default no discount reasoning"
//        !decision.eligible
//        decision.reason == "No discount rules matched"
//        decision.discountPercentage == 0.0
//    }
//
//    def "Gold customer with valid and invalid promoCode should have expected results"() {
//        given: "Two customers with different promoCodes"
//        def validPromoCustomer = new Customer(
//                "VALID-GOLD-PROMO",
//                LoyaltyLevel.GOLD,
//                300,
//                2,
//                LocalDate.now(),
//                false,
//                "GOLD2024"
//        )
//        def invalidPromoCustomer = new Customer(
//                "INVALID-GOLD-PROMO",
//                LoyaltyLevel.GOLD,
//                300,
//                2,
//                LocalDate.now(),
//                false,
//                "gold2024" // invalid due to case-sensitivity
//        )
//
//        when: "Calling checkDiscount for each customer"
//        def validPromoDecision = checker.checkDiscount(validPromoCustomer)
//        def invalidPromoDecision = checker.checkDiscount(invalidPromoCustomer)
//
//        then: "Valid promoCode qualifies for 10% while invalid does not"
//        validPromoDecision.isEligible
//        validPromoDecision.reason == "Gold promo code"
//        validPromoDecision.discountPercentage == 10.0
//
//        !invalidPromoDecision.isEligible
//        invalidPromoDecision.reason == "No discount rules matched"
//        invalidPromoDecision.discountPercentage == 0.0
//    }
//
//    def "PLATINUM customer should always get 20% regardless of promo code"() {
//        given: "A platinum customer with promoCode"
//        def customer = new Customer(
//                "PLATINUM-WITH-PROMO",
//                LoyaltyLevel.PLATINUM,
//                0,
//                0,
//                LocalDate.now().minusDays(10),
//                false,
//                "GOLD2024"
//        )
//
//        when: "Calling checkDiscount"
//        def decision = checker.checkDiscount(customer)
//
//        then: "PLATINUM always results in a 20% discount regardless of promoCode"
//        decision.isEligible
//        decision.reason == "Platinum customer"
//        decision.discountPercentage == 20.0
//    }
//
//    def "GOLD customer with exactly 5 orders does not qualify for discount"() {
//        given: "A GOLD customer with exactly 5 orders"
//        def customer = new Customer(
//                "EDGE-GOLD-ORDERS",
//                LoyaltyLevel.GOLD,
//                1200.0,
//                5, // exactly at the limit
//                LocalDate.now(),
//                false,
//                null
//        )
//
//        when: "Checking discount eligibility"
//        def decision = checker.checkDiscount(customer)
//
//        then: "The customer does not qualify for a discount due to insufficient orders"
//        !decision.isEligible
//        decision.reason == "No discount rules matched"
//        decision.discountPercentage == 0.0
//    }
//
//    def "SILVER customer with exactly 500 totalSpent does not qualify for discount"() {
//        given: "A SILVER customer with exactly 500 total spent"
//        def customer = new Customer(
//                "EDGE-SILVER-TOTALSPENT",
//                LoyaltyLevel.SILVER,
//                500.0, // exactly at the boundary
//                2,
//                LocalDate.now(),
//                false,
//                null
//        )
//
//        when: "Checking discount eligibility"
//        def decision = checker.checkDiscount(customer)
//
//        then: "The customer does not qualify for a discount"
//        !decision.isEligible
//        decision.reason == "No discount rules matched"
//        decision.discountPercentage == 0.0
//    }
//
//    def "Null customer object should trigger validation failure"() {
//        when: "Calling checkDiscount with null customer"
//        checker.checkDiscount(null)
//
//        then: "An IllegalArgumentException should be thrown"
//        def e = thrown(IllegalArgumentException)
//        e.message == "Customer cannot be null"
//
//        /* TODO: Aktualny kod nie obsługuje przypadku, w którym obiekt Customer jest null.
//           Powinna zostać dodana walidacja w metodzie checkDiscount:
//           if (customer == null) throw new IllegalArgumentException("Customer cannot be null");
//         */
//    }
//
//    def "Outdated promoCode is not eligible for a discount"() {
//        given: "A GOLD customer with an outdated promoCode"
//        def customer = new Customer(
//                "OUTDATED-PROMO",
//                LoyaltyLevel.GOLD,
//                800.0,
//                4,
//                LocalDate.now(),
//                false,
//                "GOLD2023" // promoCode that is no longer valid
//        )
//
//        when: "Checking discount eligibility"
//        def decision = checker.checkDiscount(customer)
//
//        then: "The outdated promoCode does not qualify for a discount"
//        !decision.isEligible
//        decision.reason == "No discount rules matched"
//        decision.discountPercentage == 0.0
//
//        /* TODO: Aktualny kod zakłada tylko dokładne dopasowanie kodu "GOLD2024".
//           Można rozważyć dodanie logiki sprawdzającej daty ważności kodów promocyjnych.
//         */
//    }
//
//    def "PromoCode with whitespace or special characters is not valid"() {
//        given: "A GOLD customer with a promoCode containing extra characters"
//        def customer = new Customer(
//                "INVALID-PROMO",
//                LoyaltyLevel.GOLD,
//                800.0,
//                4,
//                LocalDate.now(),
//                false,
//                invalidCode
//        )
//
//        when: "Checking discount eligibility"
//        def decision = checker.checkDiscount(customer)
//
//        then: "The promoCode with invalid formatting should not qualify for a discount"
//        !decision.isEligible
//        decision.reason == "No discount rules matched"
//        decision.discountPercentage == 0.0
//
//        where:
//        invalidCode << [
//                " GOLD2024",  // leading space
//                "GOLD2024 ",  // trailing space
//                "GOLD2024!",  // special character
//                "gold2024"    // lowercase
//        ]
//
//        /* TODO: PromoCode validation is strict and exact.
//           Suggested improvement: add logic to trim whitespaces and allow case-insensitive validation.
//         */
//    }
//
//}