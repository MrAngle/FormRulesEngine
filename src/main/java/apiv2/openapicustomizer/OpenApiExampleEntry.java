package apiv2.openapicustomizer;

public interface OpenApiExampleEntry {
    default String id() {
        return (summary().toLowerCase() + "-" + description())
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
    }
    String summary();
    String description();
    Object exampleModel();

    default String responseStatusCode() {
        return "200";
    }
}