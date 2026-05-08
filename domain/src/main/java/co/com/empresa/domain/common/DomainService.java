package co.com.empresa.domain.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as a Domain Service in the Hexagonal Architecture.
 * <p>
 * Domain Services encapsulate business rules that do not naturally belong to
 * a single Entity or Value Object. They are framework-agnostic by design;
 * this annotation is a semantic marker only and carries no Spring dependency.
 * <p>
 * The Infrastructure layer is responsible for registering classes annotated
 * with {@code @DomainService} as Spring beans via
 * {@code co.com.empresa.infrastructure.config.domain.DomainServicesBeanRegistrar}.
 *
 * <pre>{@code
 * @DomainService
 * public class TypeCategoryDomainService {
 *     public TypeCategoryDomainService(ITypeCategoryRepository repo, ITypeRepository typeRepo) { ... }
 * }
 * }</pre>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DomainService {
}
