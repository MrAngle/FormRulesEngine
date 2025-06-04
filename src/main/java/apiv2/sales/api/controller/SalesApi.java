//package apiv2.sales.api.controller;
//
//import apiv2.sales.api.dto.request.InitSalesRequest;
//import apiv2.sales.api.dto.response.InitSalesResponse;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.ExampleObject;
//import io.swagger.v3.oas.annotations.media.Schema;
//import jakarta.validation.Valid;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import static apiv2.sales.api.controller.SalesApi.SALES_API_PATH;
//
//@RestController
//@RequestMapping(SALES_API_PATH)
//public interface SalesApi {
//
//    String SALES_API_PATH = "/sales-du";
//
//
//        @io.swagger.v3.oas.annotations.parameters.RequestBody(
//            description = "Przykładowe przypadki InitSalesRequest",
//            content = @Content(
//                    mediaType = MediaType.APPLICATION_JSON_VALUE,
//                    schema = @Schema(implementation = InitSalesRequest.class),
//                    examples = {
//                            @ExampleObject(
//                                    name = "Z datą urodzenia",
//                                    summary = "Użytkownik podaje tylko datę urodzenia",
//                                    value = """
//                    {
//                      "businessFlow": "NEW",
//                      "birthDate": "02-06-1990"
//                    }
//                    """
//                            ),
//                            @ExampleObject(
//                                    name = "Z numerem REGON",
//                                    summary = "Użytkownik podaje numer REGON",
//                                    value = """
//                    {
//                      "businessFlow": "NEW",
//                      "identificationNumber": "123456785"
//                    }
//                    """
//                            )
//                    }
//            )
//    )
//    @PostMapping
//    @Operation(summary = "Init sales process")
//    ResponseEntity<InitSalesResponse> initSale(@Valid @RequestBody InitSalesRequest request);
//}
