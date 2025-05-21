package com.example.initproject.prezentacja.data.example3

import prezentacja.data.example3.DiscountEligibilityChecker3
import prezentacja.data.example3.DiscountEligibilityChecker3.Customer
import prezentacja.data.example3.DiscountEligibilityChecker3.LoyaltyLevel
import spock.lang.Specification

import java.time.LocalDate

import static org.assertj.core.api.Assertions.*

class DiscountEligibilitySpec extends Specification {


    def checker = new DiscountEligibilityChecker3()

    def "Client on blacklist is not eligible for discount"() {
        given:
        def customer = new Customer("001", LoyaltyLevel.PLATINUM, 5000, 10, LocalDate.now(), true, null)

        when:
        def result = checker.checkDiscount(customer)

        then:
        !result.isEligible()
        result.discountPercentage == 0
        result.reason.contains("blacklisted")
    }

    def "Client with no purchase in over 30 days is not eligible for discount"() {
        given:
        def customer = new Customer("002", LoyaltyLevel.GOLD, 1000, 5, LocalDate.now().minusDays(31), false, null)

        when:
        def result = checker.checkDiscount(customer)

        then:
        !result.isEligible()
        result.discountPercentage == 0
        result.reason.contains("Last purchase too old")
    }

    def "PLATINUM customer always gets 20% discount"() {
        given:
        def customer = new Customer("003", LoyaltyLevel.PLATINUM, 100, 1, LocalDate.now(), false, null)

        when:
        def result = checker.checkDiscount(customer)

        then:
        result.isEligible()
        result.discountPercentage == 20
    }

    def "GOLD customer with high spend and many orders gets 15% discount"() {
        given:
        def customer = new Customer("004", LoyaltyLevel.GOLD, 1200, 6, LocalDate.now(), false, null)

        when:
        def result = checker.checkDiscount(customer)

        then:
        result.isEligible()
        result.discountPercentage == 15
    }

    def "GOLD customer with valid promo code gets 10% discount"() {
        given:
        def customer = new Customer("005", LoyaltyLevel.GOLD, 50, 1, LocalDate.now(), false, "GOLD2024")

        when:
        def result = checker.checkDiscount(customer)

        then:
        result.isEligible()
        result.discountPercentage == 10
    }

    def "GOLD customer with no conditions met gets 0% discount"() {
        given:
        def customer = new Customer("006", LoyaltyLevel.GOLD, 800, 3, LocalDate.now(), false, null)

        when:
        def result = checker.checkDiscount(customer)

        then:
        !result.isEligible()
        result.discountPercentage == 0
    }

    def "SILVER customer with spending > 500 gets 5% discount"() {
        given:
        def customer = new Customer("007", LoyaltyLevel.SILVER, 501, 1, LocalDate.now(), false, null)

        when:
        def result = checker.checkDiscount(customer)

        then:
        result.isEligible()
        result.discountPercentage == 5
    }

    def "SILVER customer with spending <= 500 gets 0% discount"() {
        given:
        def customer = new Customer("008", LoyaltyLevel.SILVER, 500, 1, LocalDate.now(), false, null)

        when:
        def result = checker.checkDiscount(customer)

        then:
        !result.isEligible()
        result.discountPercentage == 0
    }

    def "Customer with loyalty level NONE gets no discount"() {
        given:
        def customer = new Customer("009", LoyaltyLevel.NONE, 300, 1, LocalDate.now(), false, null)

        when:
        def result = checker.checkDiscount(customer)

        then:
        !result.isEligible()
        result.discountPercentage == 0
    }

    def "Fail if null passed as customer"() {
        when:
        checker.checkDiscount(null)

        then:
        fail("This case is currently not handled.")
    }
}
