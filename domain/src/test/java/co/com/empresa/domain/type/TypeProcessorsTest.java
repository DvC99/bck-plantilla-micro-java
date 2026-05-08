package co.com.empresa.domain.type;

import co.com.empresa.commons.dto.pageable.PageContext;
import co.com.empresa.commons.exception.DomainException;
import co.com.empresa.commons.services.pageable.IPageableResult;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Type Processors")
class TypeProcessorsTest {

    @Mock private ITypeRepository typeRepository;
    @Mock private ITypeService iTypeService;

    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
    }

    private Type tipoValido() {
        Type t = easyRandom.nextObject(Type.class);
        t.setTypeCategoryId(10L);
        t.setName("Test-" + easyRandom.nextObject(String.class));
        t.setCode("CODE-" + easyRandom.nextObject(String.class));
        t.setActive(true);
        return t;
    }

    @Nested @DisplayName("TypeCreateProcessor")
    class CreateProcessor {
        @Test @DisplayName("debe crear tipo exitosamente")
        void ejecutar_crea() {
            // Arrange
            var processor = new TypeCreateProcessor(typeRepository, iTypeService);
            Type t = tipoValido();
            when(typeRepository.save(t)).thenReturn(t);
            // Act
            Type r = processor.execute(new TypeCreateCommand(t));
            // Assert
            assertEquals(t, r);
            verify(iTypeService).validateCategoryExists(10L);
            verify(iTypeService).validateUniqueness(t);
            verify(typeRepository).save(t);
        }

        @Test @DisplayName("debe retornar null cuando el contexto es null")
        void ejecutar_contextoNulo_retornaNull() {
            // Arrange
            var processor = new TypeCreateProcessor(typeRepository, iTypeService);
            // Act
            Type r = processor.execute(new TypeCreateCommand(null));
            // Assert
            assertNull(r);
        }

        @Test @DisplayName("debe lanzar DomainException cuando la validacion falla")
        void ejecutar_nombreNulo_lanza() {
            // Arrange
            var processor = new TypeCreateProcessor(typeRepository, iTypeService);
            Type t = easyRandom.nextObject(Type.class);
            t.setTypeCategoryId(10L);
            t.setName(null);
            // Act & Assert
            assertThrows(DomainException.class, () -> processor.execute(new TypeCreateCommand(t)));
        }
    }

    @Nested @DisplayName("TypeUpdateProcessor")
    class UpdateProcessor {
        @Test @DisplayName("debe actualizar tipo exitosamente")
        void ejecutar_actualiza() {
            // Arrange
            var processor = new TypeUpdateProcessor(typeRepository, iTypeService);
            Type t = tipoValido();
            when(typeRepository.update(t)).thenReturn(t);
            // Act
            Type r = processor.execute(new TypeUpdateCommand(t));
            // Assert
            assertEquals(t, r);
            verify(typeRepository).update(t);
        }

        @Test @DisplayName("debe retornar null cuando el contexto es null")
        void ejecutar_contextoNulo_retornaNull() {
            // Arrange
            var processor = new TypeUpdateProcessor(typeRepository, iTypeService);
            // Act
            Type r = processor.execute(new TypeUpdateCommand(null));
            // Assert
            assertNull(r);
        }

        @Test @DisplayName("debe retornar null cuando el id es null")
        void ejecutar_idNulo_retornaNull() {
            // Arrange
            var processor = new TypeUpdateProcessor(typeRepository, iTypeService);
            Type t = Type.builder().name("N").code("C").build();
            // Act
            Type r = processor.execute(new TypeUpdateCommand(t));
            // Assert
            assertNull(r);
        }
    }

    @Nested @DisplayName("TypeDeleteProcessor")
    class DeleteProcessor {
        @Test @DisplayName("debe eliminar tipo y retornarlo")
        void ejecutar_elimina() {
            // Arrange
            var processor = new TypeDeleteProcessor(typeRepository);
            Type t = tipoValido();
            // Act
            Type r = processor.execute(new TypeDeleteCommand(t));
            // Assert
            assertEquals(t, r);
            verify(typeRepository).delete(t.getId());
        }

        @Test @DisplayName("debe retornar null cuando el contexto es null")
        void ejecutar_contextoNulo_retornaNull() {
            // Arrange
            var processor = new TypeDeleteProcessor(typeRepository);
            // Act
            Type r = processor.execute(new TypeDeleteCommand(null));
            // Assert
            assertNull(r);
        }

        @Test @DisplayName("debe retornar null cuando el id es null")
        void ejecutar_idNulo_retornaNull() {
            // Arrange
            var processor = new TypeDeleteProcessor(typeRepository);
            Type t = Type.builder().build();
            // Act
            Type r = processor.execute(new TypeDeleteCommand(t));
            // Assert
            assertNull(r);
        }
    }

    @Nested @DisplayName("TypeGetByIdProcessor")
    class GetByIdProcessor {
        @Test @DisplayName("debe retornar tipo cuando existe")
        void ejecutar_existe_retorna() {
            // Arrange
            var processor = new TypeGetByIdProcessor(typeRepository);
            Type t = tipoValido();
            when(typeRepository.findById(t.getId())).thenReturn(Optional.of(t));
            // Act
            Type r = processor.execute(new GetTypeByIdQuery(t.getId()));
            // Assert
            assertEquals(t, r);
        }

        @Test @DisplayName("debe lanzar DomainException cuando no existe")
        void ejecutar_noExiste_lanza() {
            // Arrange
            var processor = new TypeGetByIdProcessor(typeRepository);
            Long id = Math.abs(easyRandom.nextLong()) + 1L;
            when(typeRepository.findById(id)).thenReturn(Optional.empty());
            // Act & Assert
            assertThrows(DomainException.class, () -> processor.execute(new GetTypeByIdQuery(id)));
        }

        @Test @DisplayName("debe retornar null cuando el contexto es null")
        void ejecutar_contextoNulo_retornaNull() {
            // Arrange
            var processor = new TypeGetByIdProcessor(typeRepository);
            // Act
            Type r = processor.execute(null);
            // Assert
            assertNull(r);
        }

        @Test @DisplayName("debe retornar null cuando el id es null")
        void ejecutar_idNulo_retornaNull() {
            // Arrange
            var processor = new TypeGetByIdProcessor(typeRepository);
            // Act
            Type r = processor.execute(new GetTypeByIdQuery(null));
            // Assert
            assertNull(r);
        }
    }

    @Nested @DisplayName("TypeGetComboProcessor")
    class GetComboProcessor {
        @Test @DisplayName("debe retornar lista desde el servicio")
        void ejecutar_retornaLista() {
            // Arrange
            var processor = new TypeGetComboProcessor(iTypeService);
            Type f = tipoValido();
            when(iTypeService.getComboSencillo(f)).thenReturn(List.of(f));
            // Act
            List<Type> r = processor.execute(new GetTypesByTypeCategoryQuery(f));
            // Assert
            assertEquals(1, r.size());
        }
    }

    @Nested @DisplayName("TypeGetComboPaginadoProcessor")
    class GetComboPaginadoProcessor {
        @Mock private IPageableResult<Type> pageableResult;

        @Test @DisplayName("debe retornar pagina desde el servicio")
        void ejecutar_retornaPagina() {
            // Arrange
            var processor = new TypeGetComboPaginadoProcessor(iTypeService);
            Type f = tipoValido();
            PageContext<Type> pc = PageContext.<Type>builder()
                    .data(f).pageNumber(0).pageSize(10).sortBy("id").sortDir("asc").build();
            when(pageableResult.getTotalElements()).thenReturn(1L);
            when(pageableResult.getPageNumber()).thenReturn(0);
            when(pageableResult.getPageSize()).thenReturn(10);
            when(pageableResult.getContent()).thenReturn(List.of(f));
            when(iTypeService.getComboGrande(eq(f), any())).thenReturn(pageableResult);
            // Act
            var r = processor.execute(new GetTypesByTypeCategoryPaginadoQuery(pc));
            // Assert
            assertNotNull(r);
            assertEquals(1, r.getTotalElements());
        }
    }
}
