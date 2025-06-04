
//
//import io.swagger.v3.core.converter.AnnotatedType;
//import io.swagger.v3.oas.models.media.Schema;
//import org.springdoc.core.customizers.PropertyCustomizer;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//
//
//@Component
//public class LocalDatePropertyCustomizer implements PropertyCustomizer {
//
//    @Override
//    public Schema<?> customize(Schema property, AnnotatedType type) {
//        if (type.getType() instanceof com.fasterxml.jackson.databind.JavaType javaType) {
//            Class<?> clazz = javaType.getRawClass();
//
//            if (LocalDate.class.equals(clazz)) {
//                property.setFormat("dd-MM-yyyy"); // zachowaj standardowy format
//                property.setPattern("^\\d{2}-\\d{2}-\\d{4}$"); // dd-MM-yyyy
//                property.setExample("02-06-2025");
//
//                // wymuś typ literalnego stringa
//                property.setExample(LocalDate.of(2024, 3, 22).toString());
//
//                property.setDescription("Data w formacie dd-MM-yyyy");
//            }
//        }
//        return property;
//    }
//}
