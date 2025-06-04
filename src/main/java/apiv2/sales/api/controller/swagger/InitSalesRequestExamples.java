package apiv2.sales.api.controller.swagger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.examples.Example;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

import java.time.LocalDate;
import java.util.function.Supplier;

import static apiv2.sales.api.controller.API_PATHS.API_PATH_SALES;

//public class InitSalesRequestExamples {
//
//    public static InitSalesRequest validExample() {
//        return InitSalesRequest.builder()
//                .vin("123")
//                .registrationNumber("XYZ")
//                .build();
//    }
//
//    public static InitSalesRequest missingVin() {
//        return InitSalesRequest.builder()
//                .registrationNumber("XYZ")
//                .birthDate(LocalDate.now())
//                .build();
//    }
//
//    // Możesz też dodać więcej wariantów, np. invalid PESEL, brak daty itd.
//}

@Getter
@RequiredArgsConstructor
public enum InitSalesRequestExamples {

    VALID(
            API_PATH_SALES,
            HttpMethod.POST,
            "validExample",
            "Poprawny przypadek",
            "Zawiera VIN i numer rejestracyjny",
            () -> apiv2.sales.api.dto.request.InitSalesRequest.builder()
                    .vin("123")
                    .registrationNumber("XYZ")
                    .birthDate(LocalDate.of(1990, 1, 1))
                    .build()
    ),

    OTHER_VIN(
            API_PATH_SALES,
            HttpMethod.POST,
            "missingVin",
            "Brak VIN",
            "Rejestracja podana, ale VIN brak",
            () -> apiv2.sales.api.dto.request.InitSalesRequest.builder()
                    .registrationNumber("XYZ")
                    .birthDate(LocalDate.now())
                    .build()
    );

    private final String path;
    private final HttpMethod method;
    private final String id;
    private final String summary;
    private final String description;
    private final Supplier<Object> supplier;

    public Object getExampleModel() {
        return supplier.get();
    }

    public Example toOpenApiExample(ObjectMapper mapper) {
        try {
            return new Example()
                    .summary(summary)
                    .description(description)
                    .value(mapper.writeValueAsString(getExampleModel()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not serialize example " + id, e);
        }
    }
}

