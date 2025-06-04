package apiv2.openapicustomizer;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpMethod;

@UtilityClass
class OpenApiMapper {
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
