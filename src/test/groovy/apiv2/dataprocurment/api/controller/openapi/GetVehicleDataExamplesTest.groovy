package apiv2.dataprocurment.api.controller.openapi

import apiv2.dataprocurment.api.dto.request.UpdateVehicleDataRequest
import apiv2.sales.api.controller.openapi.PostInitSalesExamples
import apiv2.sales.api.dto.request.InitSalesRequest
import jakarta.validation.Validation
import jakarta.validation.Validator
import spock.lang.Specification
import spock.lang.Unroll

//@SpringBootTest
class GetVehicleDataExamplesTest extends Specification {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator()

    static GetVehicleDataExamples examples = new GetVehicleDataExamples()
    static PostInitSalesExamples postInitSalesExamples = new PostInitSalesExamples()

    @Unroll
    def "GetVehicleDataExamples should validate example request: #example.summary()"() {
        expect:
        validator.validate((UpdateVehicleDataRequest) example.exampleModel()).isEmpty()

        where:
        example << examples.getExampleRequestBodies()
    }

    @Unroll
    def "PostInitSalesExamples should validate example request: #example.summary()"() {
        expect:
        def violations = validator.validate((InitSalesRequest) example.exampleModel())
        if (!violations.isEmpty()) {
            println "❌ ${example.summary()}: " +
                    violations.collect { "${it.propertyPath}=${it.invalidValue} (${it.message})" }.join(", ")
        }
        violations.isEmpty()

        where:
        example << postInitSalesExamples.getExampleRequestBodies()
    }



    @Unroll
    def "should fail validation for invalid request: #desc"() {
        when:
        def violations = validator.validate(invalidRequest)

        then:
        !violations.isEmpty()

        where:
        desc                          | invalidRequest
        "missing VIN and registration" | UpdateVehicleDataRequest.builder().build()
        "invalid VIN length"           | UpdateVehicleDataRequest.builder().vin("1").registrationNumber("XYZ123").build()
        "missing registrationNumber"   | UpdateVehicleDataRequest.builder().vin("1HGCM82633A004352").build()
        "null birthDate"               | UpdateVehicleDataRequest.builder().vin("1HGCM82633A004352").registrationNumber("XYZ123").birthDate(null).build()
    }
}
