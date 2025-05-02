package com.example.initproject

import spock.lang.Specification

class InitProjectApplicationTests extends Specification {

    def "test context loads"() {
        when:
        def context = new InitProjectApplication()

        then:
        context != null
    }

}
