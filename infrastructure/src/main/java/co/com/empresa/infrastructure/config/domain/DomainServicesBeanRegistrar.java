package co.com.empresa.infrastructure.config.domain;

import co.com.empresa.domain.common.DomainService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * Automatically registers all classes annotated with {@link DomainService} as Spring beans.
 * <p>
 * This eliminates the need to manually declare a {@code @Bean} method for each domain service
 * in a configuration class. Any new domain service annotated with {@code @DomainService} is
 * picked up automatically at application startup.
 * <p>
 * The scanned base package is {@code co.com.empresa.domain}, which keeps the domain layer
 * free of Spring stereotypes while still allowing Spring to manage the lifecycle of domain services.
 *
 * <h2>Usage</h2>
 * <p>Simply annotate a domain service class and declare its constructor dependencies:
 * <pre>{@code
 * @DomainService
 * public class TypeCategoryDomainService {
 *     public TypeCategoryDomainService(ITypeCategoryRepository repo, ITypeRepository typeRepo) { ... }
 * }
 * }</pre>
 * <p>Spring will resolve constructor arguments from the application context automatically
 * (constructor injection via autowiring mode {@code AUTOWIRE_CONSTRUCTOR}).
 */
@Configuration
public class DomainServicesBeanRegistrar implements BeanDefinitionRegistryPostProcessor {

    private static final String DOMAIN_BASE_PACKAGE = "co.com.empresa.domain";

    /**
     * Scans the domain base package for classes annotated with {@link DomainService}
     * and registers each one as a Spring bean with constructor autowiring.
     *
     * @param registry the bean definition registry to register beans into
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(DomainService.class));

        Set<BeanDefinition> candidates = scanner.findCandidateComponents(DOMAIN_BASE_PACKAGE);

        for (BeanDefinition candidate : candidates) {
            RootBeanDefinition beanDefinition = new RootBeanDefinition();
            beanDefinition.setBeanClassName(candidate.getBeanClassName());
            beanDefinition.setAutowireMode(RootBeanDefinition.AUTOWIRE_CONSTRUCTOR);
            beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);

            String beanName = resolveBeanName(candidate.getBeanClassName());
            registry.registerBeanDefinition(beanName, beanDefinition);
        }
    }

    /**
     * Derives the bean name from the fully qualified class name (simple name, first letter lowercased).
     *
     * @param fqClassName the fully qualified class name
     * @return the derived bean name
     */
    private String resolveBeanName(String fqClassName) {
        String simpleName = fqClassName.substring(fqClassName.lastIndexOf('.') + 1);
        return Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
    }
}
