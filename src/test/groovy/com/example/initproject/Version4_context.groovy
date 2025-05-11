import formruleengine.FormDef_v4.DomainContextService
import formruleengine.FormDef_v4.FormSubmissionDto
import formruleengine.FormDef_v4.FormValidator
import formruleengine.FormDef_v4.domain.ClaimContext
import formruleengine.FormDef_v4.domain.ClaimDataService
import formruleengine.JsonFormDefinitionProvider
import spock.lang.Specification

class Version4_context extends Specification {

    def "should enrich formSchema with default values from domain"() {
        given:
        def schema = loadSchema()
        def domainService = new DomainContextService(new ClaimDataService())

        when:
        domainService.enrichSchemaWithContext(schema)

        then:
        with(schema.getSchema()) {
            get("nationalId").getDefaultValue() == "92010112345"
            get("birthDate").getDefaultValue() == "1990-01-01"
            get("vinNumber").getDefaultValue() == "WBA3A9C59EF586739"
        }
    }

    def "should not enrich schema when domain context has no values"() {
        given:
        def schema = loadSchema()
        def mockClaimDataService = Mock(ClaimDataService)
        mockClaimDataService.loadClaimContext() >> new ClaimContext()  // wszystko null/puste

        def domainService = new DomainContextService(mockClaimDataService)

        when:
        domainService.enrichSchemaWithContext(schema)

        then:
        with(schema.getSchema()) {
            get("identityMethod").getDefaultValue() == "PESEL/NIP/REGON"
            get("nationalId").getDefaultValue() == ""
            get("birthDate").getDefaultValue() == ""
            get("vinNumber").getDefaultValue() == ""
        }
    }

    def "should enrich schema with birthDate only and set identityMethod via prefillContextRules"() {
        given:
        def schema = loadSchema()

        def mockClaimDataService = Mock(ClaimDataService)
        mockClaimDataService.loadClaimContext() >> {
            def ctx = new ClaimContext()
            ctx.setBirthDate("1990-05-10")
            return ctx
        }

        def domainService = new DomainContextService(mockClaimDataService)

        when:
        domainService.enrichSchemaWithContext(schema)

        then:
        with(schema.getSchema()) {
            get("birthDate").getDefaultValue() == "1990-05-10"
            get("identityMethod").getDefaultValue() == "Data urodzenia"
            get("nationalId").getDefaultValue() == ""  // nie ustawiony
        }
    }


    def loadSchema() {
        def formDefinition = new JsonFormDefinitionProvider()
        return formDefinition.getFormDefinition_v4("Step1_v4_reneval.json")
    }
}


