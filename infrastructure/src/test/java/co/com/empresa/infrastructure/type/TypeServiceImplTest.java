package co.com.empresa.infrastructure.type;

import co.com.empresa.domain.type.ITypeRepository;
import co.com.empresa.domain.type.Type;
import co.com.empresa.domain.type.TypeDomainService;
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
@DisplayName("TypeServiceImpl")
class TypeServiceImplTest {

    @Mock private ITypeRepository typeRepository;
    @Mock private TypeDomainService typeDomainService;
    @InjectMocks private TypeServiceImpl service;

    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
    }

    private Type tipoValido() {
        Type t = easyRandom.nextObject(Type.class);
        t.setTypeCategoryId(easyRandom.nextLong(1L, 999L));
        t.setName(easyRandom.nextObject(String.class));
        t.setCode(easyRandom.nextObject(String.class));
        t.setActive(true);
        return t;
    }

    @Nested @DisplayName("save")
    class Save {
        @Test @DisplayName("debe delegar en el repositorio")
        void save_delega() {
            // Arrange
            Type t = tipoValido();
            when(typeRepository.save(t)).thenReturn(t);
            // Act
            Type r = service.save(t);
            // Assert
            assertEquals(t, r);
        }
    }

    @Nested @DisplayName("getElement")
    class GetElement {
        @Test @DisplayName("debe retornar tipo cuando existe")
        void getElement_existe_retorna() {
            // Arrange
            Type t = tipoValido();
            when(typeRepository.findById(t.getId())).thenReturn(Optional.of(t));
            // Act
            Type r = service.getElement(t.getId());
            // Assert
            assertEquals(t, r);
        }
        @Test @DisplayName("debe retornar null cuando no existe")
        void getElement_noExiste_retornaNull() {
            // Arrange
            Long id = easyRandom.nextLong();
            when(typeRepository.findById(id)).thenReturn(Optional.empty());
            // Act
            Type r = service.getElement(id);
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
            when(typeRepository.existsById(id)).thenReturn(true);
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
            Type t = tipoValido();
            when(typeRepository.update(t)).thenReturn(t);
            // Act
            Type r = service.update(t);
            // Assert
            assertEquals(t, r);
        }
    }

    @Nested @DisplayName("saveAll")
    class SaveAll {
        @Test @DisplayName("debe delegar en el repositorio")
        void saveAll_delega() {
            // Arrange
            List<Type> lista = easyRandom.objects(Type.class, 2)
                    .peek(t -> { t.setName("n"); t.setCode("c"); t.setTypeCategoryId(1L); t.setActive(true); })
                    .toList();
            when(typeRepository.saveAll(lista)).thenReturn(lista);
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
            Type t = tipoValido();
            // Act
            Type r = service.delete(t);
            // Assert
            assertEquals(t, r);
            verifyNoInteractions(typeRepository);
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
            Type t = tipoValido();
            // Act
            service.validateUniqueness(t);
            // Assert
            verify(typeDomainService).validateUniqueness(t);
        }
    }

    @Nested @DisplayName("validateCategoryExists")
    class ValidateCategoryExists {
        @Test @DisplayName("debe delegar en el domainService")
        void validateCategoryExists_delega() {
            // Arrange
            Long catId = easyRandom.nextLong();
            // Act
            service.validateCategoryExists(catId);
            // Assert
            verify(typeDomainService).validateCategoryExists(catId);
        }
    }
}
