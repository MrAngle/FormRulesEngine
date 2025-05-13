import com.fasterxml.jackson.databind.ObjectMapper
import formruleengine.FormDef_v4.DomainContextService
import formruleengine.FormDef_v4.FormSchema
import formruleengine.FormDef_v4.FormSubmissionDto
import formruleengine.FormDef_v4.FormValidator
import formruleengine.FormDef_v4.domain.ClaimContext
import formruleengine.FormDef_v4.domain.ClaimDataService
import formruleengine.FormDef_v4.dsl.DslToJavaMapper
import formruleengine.JsonFormDefinitionProvider
import spock.lang.Specification


class Version4_dsl extends Specification {

    def "should parse rulesDsl and convert to ActionRule objects"() {
        given:
        def mapper = new ObjectMapper()
        def json = getClass().getResourceAsStream("/Step1_v4_DSL.json")
        def schema = mapper.readValue(json, FormSchema)

        when: "DSL is parsed into structured action rules"
        def rules = new DslToJavaMapper().parseDsl(schema.rulesDsl)
        schema.setActions(rules)

        then: "We get expected number of rules"
        rules.size() == 5

        and: "First rule is about identityMethod == 'Data urodzenia'"
        rules[0].when instanceof FormSchema.SimpleCondition
        with(rules[0]) {
            when.field == "identityMethod"
            when.operator == "=="
            when.value == "Data urodzenia"
            actions.size() == 2
            actions[0].target == "birthDate"
            actions[0].properties.visible == true
        }

        and: "Fourth rule uses a GroupCondition (AND)"
        rules[3].when instanceof FormSchema.GroupCondition
        ((FormSchema.GroupCondition)rules[3].when).operator == "AND"
    }


    def "should parse rules from rulesV4.dsl into ActionRule objects"() {
        given: "DSL string loaded from resource file"
        def dslText = new File(getClass().getResource("/rulesV4.dsl").toURI()).text
        def schema = loadSchema()

        when: "We parse DSL into ActionRules"
        def rules = new DslToJavaMapper().parseDsl(dslText)

        then: "Expected number of rules is parsed"
        rules.size() == 5

        and: "First rule has correct structure"
//        with(rules[0]) {
//            when instanceof FormSchema.SimpleCondition
//            when.field == "identityMethod"
//            when.operator == "=="
//            when.value == "Data urodzenia"
//            actions.size() == 4
//            actions*.target.contains("birthDate")
//            actions*.target.contains("nationalId")
//        }

        and: "Fourth rule is a GroupCondition (AND)"
        rules[3].when instanceof FormSchema.GroupCondition
        ((FormSchema.GroupCondition)rules[3].when).operator == "AND"

        def dslRules = getJson(rules)
        def jsonRules = getJson(schema.getActions())

        dslRules == jsonRules
    }

    def getJson(def definition) {
        def mapper = new ObjectMapper()
        def json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(definition)
//        println("Wynikowy JSON:\n$json")
        return json
    }


    def loadSchema() {
        def formDefinition = new JsonFormDefinitionProvider()
        return formDefinition.getFormDefinition_v4("Step1_v4_DSL.json")
    }
}


