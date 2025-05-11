package com.example.initproject

import formruleengine.FormDef_v4.FormSubmissionDto
import formruleengine.FormDef_v4.FormValidator
import formruleengine.JsonFormDefinitionProvider
import spock.lang.Specification

class Version4 extends Specification {

    def "valid submission with PESEL and VIN, noVehicle = false"() {
        given:
        def schema = loadSchema()
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
        def schema = loadSchema()
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
        def schema = loadSchema()
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
        def schema = loadSchema()
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
        def schema = loadSchema()
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
        def schema = loadSchema()
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
        def schema = loadSchema()
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
        def schema = loadSchema()
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

//    def "should pass when both VIN and registration number are provided2"() {
//        given:
//        def schema = loadSchema()
//        def submission = new FormSubmissionDto(
//                "FORM_ID_IDENTYFIKACJA", "1.0.0",
//                Map.of(
//                        "identityMethod", "PESEL/NIP/REGON",
//                        "nationalId", "92010112345",
//                        "birthDate", "",
//                        "noVehicle", true,
//                        "vinNumber", "",       // filled
//                        "registrationNumber", ""         // also filled
//                )
//        )
//
//        expect:
//        FormValidator.validateSubmission(schema, submission)
//    }


    def loadSchema() {
        def formDefinition = new JsonFormDefinitionProvider()
        return formDefinition.getFormDefinition_v4("Step1_v4.json")
    }
}
