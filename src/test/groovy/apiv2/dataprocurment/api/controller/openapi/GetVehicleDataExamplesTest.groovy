package apiv2.dataprocurment.api.controller.openapi

import apiv2.dataprocurment.api.dto.request.UpdateVehicleDataRequest
import jakarta.validation.Validation
import jakarta.validation.Validator
import spock.lang.Specification
import spock.lang.Unroll

//@SpringBootTest
class GetVehicleDataExamplesTest extends Specification {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator()

    static GetVehicleDataExamples examples = new GetVehicleDataExamples()

    @Unroll
    def "should validate example request: #example.summary()"() {
        expect:
        validator.validate((UpdateVehicleDataRequest) example.exampleModel()).isEmpty()

        where:
        example << examples.getExampleRequestBodies()
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
