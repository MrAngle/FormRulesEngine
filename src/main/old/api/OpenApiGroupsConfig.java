package api;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiGroupsConfig {

    @Bean
    public GroupedOpenApi renewalGroup() {
        return GroupedOpenApi.builder()
                .group("Renewal Phases")
                .pathsToMatch("/old/api/v1/renewal/**")
                .build();
    }

    @Bean
    public GroupedOpenApi newBusinessGroup() {
        return GroupedOpenApi.builder()
                .group("New Business Phases")
                .pathsToMatch("/old/api/v1/new-business/**")
                .build();
    }
//
//    @Bean
//    public GroupedOpenApi aftersalesGroup() {
//        return GroupedOpenApi.builder()
//                .group("Aftersales Phases")
//                .pathsToMatch("/api/v1/aftersales/**")
//                .build();
//    }
}
