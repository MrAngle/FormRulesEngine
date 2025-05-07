package com.example.initproject

import com.fasterxml.jackson.databind.ObjectMapper
import formruleengine.FormDefinition
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

    def printJson(def definition) {
        def mapper = new ObjectMapper()
        def json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(definition)
        println("Wynikowy JSON:\n$json")
        return true
    }
}
