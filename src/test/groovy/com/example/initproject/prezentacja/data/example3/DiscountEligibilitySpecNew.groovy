package com.example.initproject.loyalty

import prezentacja.data.example3.DiscountEligibilityChecker3
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class DiscountEligibilityChecker3Spec extends Specification {

    def checker = new DiscountEligibilityChecker3()

    @Unroll
    def "should evaluate discount eligibility for #description"() {
        given:
        def purchaseDate = daysAgo != null ? LocalDate.now().minusDays(daysAgo) : null
        def loyaltyLevel = level != null ? DiscountEligibilityChecker3.LoyaltyLevel.valueOf(level) : null

        def customer = new DiscountEligibilityChecker3.Customer(
                "CUST",
                loyaltyLevel,
                totalSpent,
                numberOfOrders,
                purchaseDate,
                isBlacklisted,
                promoCode
        )

        when:
        def result = checker.checkDiscount(customer)

        then:
        result.isEligible() == expectedEligible
        result.getReason() == expectedReason
        result.getDiscountPercentage() == expectedDiscount as int

        where:
        description                                  | level      | totalSpent | numberOfOrders | daysAgo | isBlacklisted | promoCode   || expectedEligible | expectedReason                 | expectedDiscount
        "Czarna lista"                               | "PLATINUM" | 2000       | 10             | 5       | true          | null        || false             | "Customer is blacklisted"      | 0.0
        "Brak aktywności > 30 dni"                   | "GOLD"     | 1500       | 6              | 31      | false         | null        || false             | "Last purchase too old"        | 0.0
        "PLATINUM – kwalifikuje się"                 | "PLATINUM" | 2000       | 10             | 5       | false         | null        || true              | "Platinum customer"            | 20
        "GOLD – wydane >1000 i >5 zamówień"          | "GOLD"     | 1200       | 6              | 5       | false         | null        || true              | "Gold loyalty + good history"  | 15
        "GOLD – kod GOLD2024"                        | "GOLD"     | 500        | 2              | 5       | false         | "GOLD2024"  || true              | "Gold promo code"              | 10
        "SILVER – wydane >500"                       | "SILVER"   | 600        | 1              | 5       | false         | null        || true              | "Silver loyalty + good spending"| 5
        "GOLD – nie spełnia progów i brak kodu"      | "GOLD"     | 800        | 2              | 5       | false         | null        || false             | "No discount rules matched"    | 0.0
        "NONE – brak kwalifikacji"                   | "NONE"     | 0          | 0              | 5       | false         | null        || false             | "No discount rules matched"    | 0.0
        "Zakup równo 30 dni temu"                    | "GOLD"     | 1500       | 6              | 30      | false         | null        || true              | "Gold loyalty + good history"  | 15
        "GOLD – dokładnie 1000 zł i 5 zamówień"      | "GOLD"     | 1000       | 5              | 5       | false         | null        || false             | "No discount rules matched"    | 0.0
        "SILVER – dokładnie 500 zł"                  | "SILVER"   | 500        | 2              | 5       | false         | null        || false             | "No discount rules matched"    | 0.0
        "GOLD – spełnia warunki i ma kod"            | "GOLD"     | 1500       | 6              | 5       | false         | "GOLD2024"  || true              | "Gold loyalty + good history"  | 15
        "Kod promocyjny tylko dla GOLD"              | "SILVER"   | 2000       | 10             | 5       | false         | "GOLD2024"  || false             | "No discount rules matched"    | 0.0
        "PromoCode = null – bez wyjątku"             | "GOLD"     | 1500       | 6              | 5       | false         | null        || true              | "Gold loyalty + good history"  | 15
        "PLATINUM + czarna lista – odrzucony"        | "PLATINUM" | 2000       | 10             | 5       | true          | null        || false             | "Customer is blacklisted"      | 0.0
        "Kod + brak aktywności – odrzucony"          | "GOLD"     | 1200       | 6              | 31      | false         | "GOLD2024"  || false             | "Last purchase too old"        | 0.0
        "Brak daty zakupu – przypadek nieobsłużony"   | "GOLD"     | 1000       | 5              | null    | false         | null        || false             | "Last purchase too old"        | 0.0
        "Brak poziomu lojalności"                | null       | 1000       | 5              | 5       | false         | null        || false             | "No discount rules matched"    | 0.0
    }
}
