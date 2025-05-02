package com.example.initproject

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
        def definition = formDefinition.getFormDefinition()

        then:
        definition != null
    }
}
