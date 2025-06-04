package apiv2.sales.api.controller.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SalesExamplesCustomizer implements OpenApiCustomizer {

    private final ObjectMapper objectMapper;

//    @Override
//    public void customise(OpenAPI openApi) {
//        Map<String, Example> examples = new LinkedHashMap<>();
//
//        examples.put("Z datą urodzenia", new Example()
//                .summary("Przypadek z birthDate")
//                .value(Map.of(
//                        "businessFlow", "NEW",
//                        "birthDate", "02-06-1990"
//                )));
//
//        examples.put("Z REGON", new Example()
//                .summary("Przypadek z REGON")
//                .value(Map.of(
//                        "businessFlow", "NEW2",
//                        "identificationNumber", "123456785"
//                )));
//
//        MediaType mediaType = new MediaType()
//                .schema(new Schema<>().$ref("#/components/schemas/InitSalesRequest"))
//                .examples(examples);
//
//        Content content = new Content().addMediaType("application/json", mediaType);
//
//        // Ustaw request body tylko jeśli endpoint istnieje
//        openApi.getPaths().get("/sales").getPost().getRequestBody().setContent(content);
//    }

//    @Override
//    public void customise(OpenAPI openApi) {
//        openApi.getPaths().forEach((path, pathItem) -> {
//            if ("/sales".equals(path)) {
//                Optional.ofNullable(pathItem.getPost()).ifPresent(postOperation -> {
//                    postOperation.getRequestBody().getContent().forEach((mediaType, mediaTypeObject) -> {
//                        try {
//                            String jsonExample = objectMapper.writeValueAsString(InitSalesRequest
//                                    .builder()
//                                    .registrationNumber("tess")
//                                    .vin("test")
//                                    .build());
//                            Example example = new Example()
//                                    .summary("Example InitSalesRequest")
//                                    .value(jsonExample);
//
//                            String jsonExample2 = objectMapper.writeValueAsString(InitSalesRequest
//                                    .builder()
//                                    .registrationNumber("tess2")
//                                    .vin("test")
//                                    .build());
//                            Example example2 = new Example()
//                                    .summary("Example InitSalesRequest2")
//                                    .value(jsonExample2);
//
//                            mediaTypeObject.addExamples("example1", example);
//                            mediaTypeObject.addExamples("example2", example2);
//                        } catch (JsonProcessingException e) {
//                            System.out.println("Could not serialize InitSalesRequest example: " + e.getMessage());
//                        }
//                    });
//                });
//            }
//        });
//    }

//    @Override
//    public void customise(OpenAPI openApi) {
//        Schema<?> schema = openApi.getComponents().getSchemas().get(InitSalesRequest.class.getName());
//
//        if (schema != null) {
//            try {
//                InitSalesRequest example = InitSalesRequest.builder()
//                        .vin("test")
//                        .registrationNumber("tess222")
//                        .build();
//
//                schema.setExample(objectMapper.convertValue(example, Map.class));
//
//            } catch (IllegalArgumentException e) {
//                System.out.println("Failed to set example for InitSalesRequest: " + e.getMessage());
//            }
//        }
//    }

    @Override
    public void customise(OpenAPI openApi) {
        for (InitSalesRequestExamples example : InitSalesRequestExamples.values()) {
            PathItem pathItem = openApi.getPaths().get(example.getPath());
            if (pathItem == null) continue;

            Operation operation = getOperation(pathItem, example.getMethod());
            if (operation == null || operation.getRequestBody() == null) continue;

            Content content = operation.getRequestBody().getContent();
            MediaType mediaType = content.get("application/json");
            if (mediaType == null) continue;

            mediaType.addExamples(example.getId(), example.toOpenApiExample(objectMapper));
        }


//        PathItem pathItem = openApi.getPaths().get("/sales");
//        if (pathItem == null || pathItem.getPost() == null) return;
//
//        Operation postOperation = pathItem.getPost();
//        RequestBody requestBody = postOperation.getRequestBody();
//        if (requestBody == null) return;
//
//        Content content = requestBody.getContent();
//        MediaType mediaTypeObject = content.get("application/json");
//        if (mediaTypeObject == null) return;
//
//        try {
//            mediaTypeObject.addExamples("validExample", new Example()
//                    .summary("Poprawny przypadek")
//                    .value(objectMapper.writeValueAsString(InitSalesRequestExamples.validExample())));
//
//            mediaTypeObject.addExamples("otherExample", new Example()
//                    .summary("Brak VIN")
//                    .value(objectMapper.writeValueAsString(InitSalesRequestExamples.missingVin())));
//
//        } catch (JsonProcessingException e) {
//            System.out.println("Błąd serializacji przykładu InitSalesRequest: " + e.getMessage());
//        }
    }

    public static Operation getOperation(PathItem pathItem, HttpMethod method) {
        if (method == HttpMethod.GET) return pathItem.getGet();
        else if (method == HttpMethod.POST) return pathItem.getPost();
        else if (method == HttpMethod.PUT) return pathItem.getPut();
        else if (method == HttpMethod.PATCH) return pathItem.getPatch();
        else if (method == HttpMethod.DELETE) return pathItem.getDelete();
        else if (method == HttpMethod.OPTIONS) return pathItem.getOptions();
        else if (method == HttpMethod.HEAD) return pathItem.getHead();
        else if (method == HttpMethod.TRACE) return pathItem.getTrace();
        else return null;
    }
}