import com.fasterxml.jackson.databind.ObjectMapper
import formruleengine.FormDef_v4.DomainContextService
import formruleengine.FormDef_v4.FormSchema
import formruleengine.FormDef_v4.FormSubmissionDto
import formruleengine.FormDef_v4.FormValidator
import formruleengine.FormDef_v4.domain.ClaimContext
import formruleengine.FormDef_v4.domain.ClaimDataService
import formruleengine.FormDef_v4.dsl.DslMacroExpander
import formruleengine.FormDef_v4.dsl.DslToJavaMapper
import formruleengine.JsonFormDefinitionProvider
import spock.lang.Specification


class Version4_dsl extends Specification {


    def "should parse rules from rulesV4.dsl into ActionRule objects"() {
        given: "DSL string loaded from resource file"
//        def dslText = new File(getClass().getResource("/rulesV4.dsl").toURI()).text
        def schema = loadSchema()

        String rawDsl =  new File(getClass().getResource("/rulesV4.dsl").toURI()).text
        DslMacroExpander expander = new DslMacroExpander()
        String expandedDsl = expander.expand(rawDsl)

        when: "We parse DSL into ActionRules"
        def rules = new DslToJavaMapper().parseDsl(expandedDsl)

        then: "Expected number of rules is parsed"
        rules.size() == 6

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

    private FormSchema getSchemaWithDslRules() {
        def schema = loadSchema()

        String rawDsl = new File(getClass().getResource("/rulesV4.dsl").toURI()).text
        DslMacroExpander expander = new DslMacroExpander()
        String expandedDsl = expander.expand(rawDsl)
        def rules = new DslToJavaMapper().parseDsl(expandedDsl)
        schema.setActions(rules)
        return schema
    }


    def "valid submission with PESEL and VIN, noVehicle = false"() {
        given:
        def schema = getSchemaWithDslRules()
        def submission = new FormSubmissionDto(
                "FORM_ID_IDENTYFIKACJA", "1.0.0",
                Map.of(
                        "identityMethod", "PESEL/NIP/REGON",
                        "nationalId", "92010112345", // valid PESEL format
                        "birthDate", "",
                        "noVehicle", false,
                        "vinNumber", "WBA3A9C59EF586739",
                        "registrationNumber", ""
                )
        )

        expect:
        FormValidator.validateSubmission(schema, submission)
    }

    def "invalid PESEL format should fail"() {
        given:
        def schema = getSchemaWithDslRules()
        def submission = new FormSubmissionDto(
                "FORM_ID_IDENTYFIKACJA", "1.0.0",
                Map.of(
                        "identityMethod", "PESEL/NIP/REGON",
                        "nationalId", "XYZ123", // invalid format
                        "birthDate", "",
                        "noVehicle", false,
                        "vinNumber", "WBA3A9C59EF586739",
                        "registrationNumber", ""
                )
        )

        expect:
        !FormValidator.validateSubmission(schema, submission)
    }

    def "noVehicle = true, VIN and REG empty should pass"() {
        given:
        def schema = getSchemaWithDslRules()
        def submission = new FormSubmissionDto(
                "FORM_ID_IDENTYFIKACJA", "1.0.0",
                Map.of(
                        "identityMethod", "PESEL/NIP/REGON",
                        "nationalId", "92010112345",
                        "birthDate", "",
                        "noVehicle", true,
                        "vinNumber", "",
                        "registrationNumber", ""
                )
        )

        expect:
        FormValidator.validateSubmission(schema, submission)
    }

    def "noVehicle = false, VIN and REG empty should fail"() {
        given:
        def schema = getSchemaWithDslRules()
        def submission = new FormSubmissionDto(
                "FORM_ID_IDENTYFIKACJA", "1.0.0",
                Map.of(
                        "identityMethod", "PESEL/NIP/REGON",
                        "nationalId", "92010112345",
                        "birthDate", "",
                        "noVehicle", false,
                        "vinNumber", "",
                        "registrationNumber", ""
                )
        )

        expect:
        !FormValidator.validateSubmission(schema, submission)
    }


    def "noVehicle = true, VIN provided should pass"() {
        given:
        def schema = getSchemaWithDslRules()
        def submission = new FormSubmissionDto(
                "FORM_ID_IDENTYFIKACJA", "1.0.0",
                Map.of(
                        "identityMethod", "PESEL/NIP/REGON",
                        "nationalId", "92010112345",
                        "birthDate", "",
                        "noVehicle", true,
                        "vinNumber", "WBA3A9C59EF586739",
                        "registrationNumber", ""
                )
        )

        expect:
        FormValidator.validateSubmission(schema, submission)
    }

    def "should fail when identityMethod is PESEL but nationalId is missing"() {
        given:
        def schema = getSchemaWithDslRules()
        def submission = new FormSubmissionDto(
                "FORM_ID_IDENTYFIKACJA", "1.0.0",
                Map.of(
                        "identityMethod", "PESEL/NIP/REGON",
                        "nationalId", "",  // <-- missing
                        "birthDate", "",
                        "noVehicle", false,
                        "vinNumber", "WBA3A9C59EF586739",
                        "registrationNumber", ""
                )
        )

        expect:
        !FormValidator.validateSubmission(schema, submission)
    }

    def "should fail when identityMethod is birthDate but date is missing"() {
        given:
        def schema = getSchemaWithDslRules()
        def submission = new FormSubmissionDto(
                "FORM_ID_IDENTYFIKACJA", "1.0.0",
                Map.of(
                        "identityMethod", "Data urodzenia",  // <--
                        "nationalId", "92010112345",         // irrelevant
                        "birthDate", "",                     // <-- missing
                        "noVehicle", false,
                        "vinNumber", "WBA3A9C59EF586739",
                        "registrationNumber", ""
                )
        )

        expect:
        !FormValidator.validateSubmission(schema, submission)
    }

    def "should pass when both VIN and registration number are provided"() {
        given:
        def schema = getSchemaWithDslRules()
        def submission = new FormSubmissionDto(
                "FORM_ID_IDENTYFIKACJA", "1.0.0",
                Map.of(
                        "identityMethod", "PESEL/NIP/REGON",
                        "nationalId", "92010112345",
                        "birthDate", "",
                        "noVehicle", false,
                        "vinNumber", "WBA3A9C59EF586739",       // filled
                        "registrationNumber", "WX12345"         // also filled
                )
        )

        expect:
        FormValidator.validateSubmission(schema, submission)
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


