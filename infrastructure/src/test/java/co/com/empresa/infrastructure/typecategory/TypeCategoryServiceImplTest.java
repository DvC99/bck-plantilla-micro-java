package co.com.empresa.infrastructure.typecategory;

import co.com.empresa.domain.typecategory.ITypeCategoryRepository;
import co.com.empresa.domain.typecategory.TypeCategory;
import co.com.empresa.domain.typecategory.TypeCategoryDomainService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TypeCategoryServiceImpl")
class TypeCategoryServiceImplTest {

    @Mock private ITypeCategoryRepository categoryRepository;
    @Mock private TypeCategoryDomainService typeCategoryDomainService;
    @InjectMocks private TypeCategoryServiceImpl service;

    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
    }

    private TypeCategory categoriaValida() {
        TypeCategory c = easyRandom.nextObject(TypeCategory.class);
        c.setName(easyRandom.nextObject(String.class));
        c.setCode(easyRandom.nextObject(String.class));
        c.setActive(true);
        return c;
    }

    @Nested @DisplayName("save")
    class Save {
        @Test @DisplayName("debe delegar en el repositorio")
        void save_delega() {
            // Arrange
            var c = categoriaValida();
            when(categoryRepository.save(c)).thenReturn(c);
            // Act
            var r = service.save(c);
            // Assert
            assertEquals(c, r);
        }
    }

    @Nested @DisplayName("getElement")
    class GetElement {
        @Test @DisplayName("debe retornar categoria cuando existe")
        void getElement_existe_retorna() {
            // Arrange
            var c = categoriaValida();
            when(categoryRepository.findById(c.getId())).thenReturn(Optional.of(c));
            // Act
            var r = service.getElement(c.getId());
            // Assert
            assertEquals(c, r);
        }
        @Test @DisplayName("debe retornar null cuando no existe")
        void getElement_noExiste_retornaNull() {
            // Arrange
            Long id = easyRandom.nextLong();
            when(categoryRepository.findById(id)).thenReturn(Optional.empty());
            // Act
            var r = service.getElement(id);
            // Assert
            assertNull(r);
        }
    }

    @Nested @DisplayName("existById")
    class ExistById {
        @Test @DisplayName("debe delegar en el repositorio")
        void existById_delega() {
            // Arrange
            Long id = easyRandom.nextLong();
            when(categoryRepository.existsById(id)).thenReturn(true);
            // Act
            boolean r = service.existById(id);
            // Assert
            assertTrue(r);
        }
    }

    @Nested @DisplayName("update")
    class Update {
        @Test @DisplayName("debe delegar en repository.update")
        void update_delega() {
            // Arrange
            var c = categoriaValida();
            when(categoryRepository.update(c)).thenReturn(c);
            // Act
            var r = service.update(c);
            // Assert
            assertEquals(c, r);
        }
    }

    @Nested @DisplayName("saveAll")
    class SaveAll {
        @Test @DisplayName("debe delegar en el repositorio")
        void saveAll_delega() {
            // Arrange
            var lista = easyRandom.objects(TypeCategory.class, 2)
                    .peek(tc -> { tc.setName("n"); tc.setCode("c"); tc.setActive(true); })
                    .toList();
            when(categoryRepository.saveAll(lista)).thenReturn(lista);
            // Act
            var r = service.saveAll(lista);
            // Assert
            assertEquals(lista, r);
        }
    }

    @Nested @DisplayName("delete")
    class Delete {
        @Test @DisplayName("debe retornar el modelo (implementacion base)")
        void delete_retornaModelo() {
            // Arrange
            var c = categoriaValida();
            // Act
            var r = service.delete(c);
            // Assert
            assertEquals(c, r);
            verifyNoInteractions(categoryRepository);
        }
    }

    @Nested @DisplayName("getNextId")
    class GetNextId {
        @Test @DisplayName("debe retornar 0L (implementacion base)")
        void getNextId_retornaCero() {
            // Act & Assert
            assertEquals(0L, service.getNextId());
        }
    }

    @Nested @DisplayName("validateUniqueness")
    class ValidateUniqueness {
        @Test @DisplayName("debe delegar en el domainService")
        void validateUniqueness_delega() {
            // Arrange
            var c = categoriaValida();
            // Act
            service.validateUniqueness(c);
            // Assert
            verify(typeCategoryDomainService).validateUniqueness(c);
        }
    }

    @Nested @DisplayName("validateNoDependencies")
    class ValidateNoDependencies {
        @Test @DisplayName("debe delegar en el domainService")
        void validateNoDependencies_delega() {
            // Arrange
            var c = categoriaValida();
            // Act
            service.validateNoDependencies(c);
            // Assert
            verify(typeCategoryDomainService).validateNoDependencies(c);
        }
    }
}
