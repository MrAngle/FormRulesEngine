package apiv2.archunit

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.ArchRule
import jakarta.validation.ConstraintValidator
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*

class ConstraintValidatorArchitectureSpec extends Specification {

//    JavaClasses importedClasses = new ClassFileImporter().importPackages("apiv2") // ← dostosuj do swojego root package

    JavaClasses importedClasses = new ClassFileImporter()
            .importPackages("apiv2..api.validation.impl")

    def "ConstraintValidators should not depend on @Service or @Component beans"() {
        given:
        JavaClasses importedClasses = new ClassFileImporter()
                .importPackages("apiv2")

        ArchRule rule = noClasses()
                .that().implement(ConstraintValidator)
                .should().dependOnClassesThat().areAnnotatedWith(Service)
                .orShould().dependOnClassesThat().areAnnotatedWith(Component)

        expect:
        rule.check(importedClasses)
    }

    def "ConstraintValidators should not have fields annotated with @Autowired"() {
        given:
        ArchRule rule = fields()
                .that().areDeclaredInClassesThat().implement(ConstraintValidator)
                .should().notBeAnnotatedWith(Autowired)

        expect:
        rule.check(importedClasses)
    }

    def "ConstraintValidators should not throw exceptions because of missing default constructors"() {
        given:
        ArchRule rule = classes()
                .that().implement(ConstraintValidator)
                .should().haveOnlyConstructorsThat().areDeclared()

        expect:
        rule.check(importedClasses)
    }
}
