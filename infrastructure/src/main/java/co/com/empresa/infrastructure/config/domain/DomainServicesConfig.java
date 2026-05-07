package co.com.empresa.infrastructure.config.domain;

import co.com.empresa.domain.typecategory.ITypeCategoryRepository;
import co.com.empresa.domain.typecategory.TypeCategoryDomainService;
import co.com.empresa.domain.type.ITypeRepository;
import co.com.empresa.domain.type.TypeDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainServicesConfig {

    @Bean
    public TypeCategoryDomainService typeCategoryDomainService(ITypeCategoryRepository categoryRepository,
                                                               ITypeRepository typeRepository) {
        return new TypeCategoryDomainService(categoryRepository, typeRepository);
    }

    @Bean
    public TypeDomainService typeDomainService(ITypeRepository typeRepository,
                                               ITypeCategoryRepository categoryRepository) {
        return new TypeDomainService(typeRepository, categoryRepository);
    }
}
