package co.com.empresa.domain.common;

import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Spring stereotype for Domain Services in the Hexagonal Architecture.
 * <p>
 * Domain Services encapsulate business rules that do not naturally belong to
 * a single Entity or Value Object. They are annotated with {@code @DomainService}
 * instead of {@code @Service} to preserve domain semantics while still being
 * auto-detected by Spring component scanning.
 * <p>
 * This annotation is meta-annotated with {@code @Component}, so Spring automatically
 * registers these classes as singleton beans. Constructor injection is used for
 * all dependencies.
 *
 * <pre>{@code
 * @DomainService
 * public class TypeCategoryDomainService {
 *     public TypeCategoryDomainService(ITypeCategoryRepository repo, ITypeRepository typeRepo) { ... }
 * }
 * }</pre>
 *
 * @see org.springframework.stereotype.Component
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface DomainService {
}
