package apiv2.openapicustomizer;

import org.springframework.http.HttpMethod;

import java.util.List;

public interface OpenApiExampleSet {
//    List<OpenApiExampleEntry> getEntries();

    String getPath();
    HttpMethod getMethodHttp();
    List<OpenApiExampleEntry> getExampleRequestBodies();
    List<OpenApiExampleEntry> getExampleResponseBodies();

//    default void applyToOpenApi(OpenAPI openApi, ObjectMapper objectMapper) {
//        for (OpenApiExampleEntry entry : getEntries()) {
//            PathItem pathItem = openApi.getPaths().get(entry.path());
//            if (pathItem == null) continue;
//
//            Operation operation = OpenApiMapper.getOperation(pathItem, entry.method());
//
//            switch (entry.type()) {
//                case REQUEST -> {
//                    RequestBody requestBody = operation.getRequestBody();
//                    if (requestBody != null) {
//                        MediaType mediaType = requestBody.getContent().get("application/json");
//                        if (mediaType != null) {
//                            mediaType.addExamples(entry.id(), createExample(entry, objectMapper));
//                        }
//                    }
//                }
//                case RESPONSE -> {
//                    String statusCode = entry.responseStatusCode();
//                    ApiResponse apiResponse = operation.getResponses().get(statusCode);
//
//                    if (apiResponse != null) {
//                        MediaType mediaType = Optional.ofNullable(apiResponse.getContent())
//                                .map(content -> content.get("application/json"))
//                                .orElse(null);
//
//                        if (mediaType != null) {
//                            mediaType.addExamples(entry.id(), createExample(entry, objectMapper));
//                        }
//                    }
//                }
//            }
//
//
//        }
//    }
//
//    default Example createExample(OpenApiExampleEntry entry, ObjectMapper objectMapper) {
//        try {
//            return new Example()
//                    .summary(entry.summary())
//                    .description(entry.description())
//                    .value(objectMapper.writeValueAsString(entry.exampleModel()));
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Failed to serialize example " + entry.id(), e);
//        }
//    }

//
//            if (operation == null || operation.getRequestBody() == null) continue;
//
//            Content content = operation.getRequestBody().getContent();
//            MediaType mediaType = content.get("application/json");
//            if (mediaType == null) continue;
//
//            try {
//                Example example = new Example()
//                        .summary(entry.summary())
//                        .description(entry.description())
//                        .value(objectMapper.writeValueAsString(entry.exampleModel()));
//
//                mediaType.addExamples(entry.id(), example);
//            } catch (JsonProcessingException e) {
//                // log error
//            }
//    }

}
