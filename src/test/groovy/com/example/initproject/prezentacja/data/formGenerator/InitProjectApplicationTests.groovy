package com.example.initproject.prezentacja.data.formGenerator

import com.example.initproject.InitProjectApplication
import com.fasterxml.jackson.databind.ObjectMapper
import formruleengine.FormDef_v3.FormDefinition_v3
import formruleengine.FormDef_v3.predicate.PredicateContext
import formruleengine.FormDef_v3.predicate.RuleEvaluator
import formruleengine.FormDef_v3.formgeneratordomain.FormGeneratorDomain
import formruleengine.JsonFormDefinitionProvider
import spock.lang.Specification

class InitProjectApplicationTests extends Specification {

    def "test context loads"() {
        when:
        def context = new InitProjectApplication()

        then:
        context != null
    }

    def "test engine"() {
        when:
        def formDefinition = new JsonFormDefinitionProvider()
        def filePath = "modelActionEngine.json";

        def definition = formDefinition.getFormDefinition(filePath)


        then:
        definition != null
        and:
        printJson(definition) == true
    }

    // Pole "czy miałeś szkodę w ciągu ostatnich 6 miesięcy" jest widoczne tylko,
    // jeśli "czy miałeś szkodę w ciągu ostatnich 12 miesięcy" jest na true.
    // To samo pole jest widoczne w ogóle tylko wtedy, kiedy UFG nie działa
    // (ale w formularzu nie mamy pola "UFGDziała true/false", więc nie możemy
    // użyć do tego zależności dynamicznej).

    // Jesli ktos wybrał motocykl lub przyczepe to pole o strone kierownicy powinno
    // nie byc widoczne
    def "test engine with child"() {
        when:
        def formDefinition = new JsonFormDefinitionProvider()
        def filePath =
                "childForm.json";

        def definition = formDefinition.getFormDefinition(filePath)

        then:
        definition != null
        and:
        printJson(definition) == true
    }


    def "test engine with child FormDefinition_v2"() {
        when:
        def formDefinition = new JsonFormDefinitionProvider()
        def filePath =
                "szkoda.json";

        def definition = formDefinition.getFormDefinition_v2(filePath)

        then:
        definition != null
        and:
        printJson(definition) == true
    }

    def "test engine with child FormDefinition_v3"() {
        when:
        def formDefinition = new JsonFormDefinitionProvider()
        def filePath =
                "Szkoda_v3.json";

        def definition = formDefinition.getFormDefinition_v3(filePath)

        then:
        definition != null
        and:
        printJson(definition) == true
    }

    def "should evaluate rules in form definition"() {
        given:
        def formDefinition = new JsonFormDefinitionProvider()
        def definition = formDefinition.getFormDefinition_v3("Szkoda_v3.json")

        and: "prepare field values and metadata"
        def fieldValues = [
                "owner.hasClaimIn12Months": true,
                "vehicle.vehicleType"     : "SAMOCHÓD",
                "vehicle.isFromUK"        : false
        ]
        def metadata = [
                "ufgAvailable": false,
                "userType"    : "NEW"
        ]
        def sessionData = [
                "ufgAvailable": false,
                "userType"    : "EXISTING"
        ]
        def context = new PredicateContext(metadata, fieldValues, sessionData)
//        def evaluator = new RuleEvaluator()

        when: "rules are evaluated"
        def results = []
        for (rule in definition.rules) {
            if (RuleEvaluator.evaluateCondition(rule.condition, context)) {
                results << rule.action
            }
        }

        List<FormDefinition_v3.Rule> rules = definition.getRules();
//        PredicateContext context = new PredicateContext(metadataMap, fieldValuesMap);

        List<FormDefinition_v3.Rule> prunedRules = RuleEvaluator.pruneRulesByPredicates(rules, context);

// możesz teraz nadpisać:
        definition.setRules(prunedRules)

//        ClaimForm claimForm = DomainService.getClaimForm()
//        def prefillRules = FormGeneratorDomain.generatePrefillRules(claimForm) // <- metoda powyżej

        FormGeneratorDomain.enrichWithPrefillRules(definition)

        then:
//        results.any { it.field == "owner.hasClaimIn6Months" && it.property == "visible" && it.value == true }
        printJson(definition) == true
    }

    def printJson(def definition) {
        def mapper = new ObjectMapper()
        def json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(definition)
        println("Wynikowy JSON:\n$json")
        return true
    }
}
