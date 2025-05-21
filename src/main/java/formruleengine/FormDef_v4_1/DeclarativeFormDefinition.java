//package formruleengine.FormDef_v4_1;
//
//class DeclarativeFormDefinition {
//
//    void getRules() {
//        control("userIdentifier")
//        .group("userIdentification")
//                .label("Identyfikacja użytkownika")
//                .controls(
//                        control("identityMethod")
//                                .label("Wybierz metodę identyfikacji")
//                                .type("string")
//                                .options(
//                                        option("PESEL/NIP/REGON", "PESEL/NIP/REGON"),
//                                        option("Data urodzenia", "Data urodzenia")
//                                )
//                                .defaultValue("PESEL/NIP/REGON"),
//
//                        control("birthDate")
//                                .label("Data urodzenia")
//                                .type("string")
//                                .props(
//                                        prop("visible")
//                                                .when(
//                                                        condition("identityMethod", "EQUAL", "Data urodzenia")
//                                                )
//                                                .then(true)
//                                                .otherwise(false),
//                                        prop("required")
//                                                .when(
//                                                        condition("identityMethod", "EQUAL", "Data urodzenia")
//                                                )
//                                                .then(true)
//                                                .otherwise(false)
//                                )
//                                .validators(
//                                        validator("regex")
//                                                .expression("^\\d{4}-\\d{2}-\\d{2}$")
//                                ),
//
//                        control("nationalId")
//                                .label("PESEL / NIP / REGON")
//                                .type("string")
//                                .props(
//                                        prop("visible")
//                                                .when(
//                                                        condition("identityMethod", "EQUAL", "PESEL/NIP/REGON")
//                                                )
//                                                .then(true)
//                                                .otherwise(false),
//                                        prop("required")
//                                                .when(
//                                                        condition("identityMethod", "EQUAL", "PESEL/NIP/REGON")
//                                                )
//                                                .then(true)
//                                                .otherwise(false)
//                                )
//                                .validators(
//                                        validator("regex")
//                                                .expression("^(\\d{11}|\\d{10}|\\d{9})$")
//                                ),
//
//                        control("vinNumber")
//                                .label("Numer VIN")
//                                .type("string")
//                                .props(
//                                        prop("required")
//                                                .when(
//                                                        condition("registrationNumber", "EQUAL", ""),
//                                                        condition("noVehicle", "EQUAL", false)
//                                                )
//                                                .then(true)
//                                                .otherwise(false)
//                                )
//                                .validators(
//                                        validator("regex")
//                                                .expression("^[A-HJ-NPR-Z0-9]{17}$")
//                                ),
//
//                        control("registrationNumber")
//                                .label("Numer rejestracyjny")
//                                .type("string")
//                                .props(
//                                        prop("required")
//                                                .when(
//                                                        condition("vinNumber", "EQUAL", ""),
//                                                        condition("noVehicle", "EQUAL", false)
//                                                )
//                                                .then(true)
//                                                .otherwise(false)
//                                )
//                                .validators(
//                                        validator("regex")
//                                                .expression("^[A-Z0-9]{5,10}$")
//                                )
//                )
//                .endGroup()
//
//    }
//}
