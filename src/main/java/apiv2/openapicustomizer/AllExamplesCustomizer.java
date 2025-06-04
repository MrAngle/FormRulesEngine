package apiv2.openapicustomizer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class AllExamplesCustomizer implements OpenApiCustomizer {

    private final ObjectMapper objectMapper;
    private final List<OpenApiExampleSet> exampleSets;

    @Override
    public void customise(OpenAPI openApi) {
        if(exampleSets == null || exampleSets.isEmpty()) return;
        exampleSets.forEach(set -> applyToOpenApi(set, openApi, objectMapper));
    }

    void applyToOpenApi(OpenApiExampleSet exampleSet, OpenAPI openApi, ObjectMapper objectMapper) {
        PathItem pathItem = openApi.getPaths().get(exampleSet.getPath());
        if(pathItem == null) return;

        Operation operation = OpenApiMapper.getOperation(pathItem, exampleSet.getMethodHttp());
        if (operation == null) return;

        handleRequestBodies(exampleSet, objectMapper, operation);
        handleResponseBodies(exampleSet, objectMapper, operation);
    }

    private void handleRequestBodies(OpenApiExampleSet exampleSet, ObjectMapper objectMapper, Operation operation) {
        if(operation.getRequestBody() == null) return;
        for (OpenApiExampleEntry entry : exampleSet.getExampleRequestBodies()) {
            RequestBody requestBody = operation.getRequestBody();
            if (requestBody != null) {
                MediaType mediaType = requestBody.getContent().get("application/json");
                if (mediaType != null) {
                    mediaType.addExamples(entry.id(), createExample(entry, objectMapper));
                }
            }
        }
    }

    private void handleResponseBodies(OpenApiExampleSet exampleSet, ObjectMapper objectMapper, Operation operation) {
        if(operation.getResponses() == null) return;
        for (OpenApiExampleEntry entry : exampleSet.getExampleResponseBodies()) {
            String statusCode = entry.responseStatusCode();
            ApiResponse apiResponse = operation.getResponses().get(statusCode);

            if (apiResponse != null) {
                Optional.ofNullable(apiResponse.getContent())
                        .map(content -> content.get("application/json")).ifPresent(mediaType -> mediaType.addExamples(entry.id(), createExample(entry, objectMapper)));

            }
        }
    }

    Example createExample(OpenApiExampleEntry entry, ObjectMapper objectMapper) {
        try {
            return new Example()
                    .summary(entry.summary())
                    .description(entry.description())
                    .value(objectMapper.writeValueAsString(entry.exampleModel()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize example " + entry.id(), e);
        }
    }
}