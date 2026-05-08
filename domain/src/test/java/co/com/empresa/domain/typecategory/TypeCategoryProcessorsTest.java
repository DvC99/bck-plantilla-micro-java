package co.com.empresa.domain.typecategory;

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
@DisplayName("TypeCategory Processors")
class TypeCategoryProcessorsTest {

    @Mock private ITypeCategoryRepository categoryRepository;
    @Mock private ITypeCategoryService iTypeCategoryService;

    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
    }

    private TypeCategory categoriaValida() {
        TypeCategory c = easyRandom.nextObject(TypeCategory.class);
        c.setName("Test-" + easyRandom.nextObject(String.class));
        c.setCode("CODE-" + easyRandom.nextObject(String.class));
        c.setActive(true);
        return c;
    }

    @Nested @DisplayName("TypeCategoryCreateProcessor")
    class CreateProcessor {
        @Test @DisplayName("debe crear y retornar categoria persistida")
        void ejecutar_crea() {
            // Arrange
            var processor = new TypeCategoryCreateProcessor(categoryRepository, iTypeCategoryService);
            var c = categoriaValida();
            when(categoryRepository.save(c)).thenReturn(c);
            // Act
            var r = processor.execute(new TypeCategoryCreateCommand(c));
            // Assert
            assertEquals(c, r);
            verify(iTypeCategoryService).validateUniqueness(c);
            verify(categoryRepository).save(c);
        }

        @Test @DisplayName("debe retornar null cuando el contexto es null")
        void ejecutar_contextoNulo_retornaNull() {
            // Arrange
            var processor = new TypeCategoryCreateProcessor(categoryRepository, iTypeCategoryService);
            // Act
            var r = processor.execute(new TypeCategoryCreateCommand(null));
            // Assert
            assertNull(r);
        }

        @Test @DisplayName("debe lanzar cuando el nombre esta vacio")
        void ejecutar_nombreMalo_lanza() {
            // Arrange
            var processor = new TypeCategoryCreateProcessor(categoryRepository, iTypeCategoryService);
            var c = easyRandom.nextObject(TypeCategory.class);
            c.setName("");
            // Act & Assert
            assertThrows(DomainException.class, () -> processor.execute(new TypeCategoryCreateCommand(c)));
        }
    }

    @Nested @DisplayName("TypeCategoryUpdateProcessor")
    class UpdateProcessor {
        @Test @DisplayName("debe actualizar y retornar categoria")
        void ejecutar_actualiza() {
            // Arrange
            var processor = new TypeCategoryUpdateProcessor(categoryRepository, iTypeCategoryService);
            var c = categoriaValida();
            when(categoryRepository.update(c)).thenReturn(c);
            // Act
            var r = processor.execute(new TypeCategoryUpdateCommand(c));
            // Assert
            assertEquals(c, r);
            verify(categoryRepository).update(c);
        }

        @Test @DisplayName("debe retornar null cuando el contexto es null")
        void ejecutar_contextoNulo_retornaNull() {
            // Arrange
            var processor = new TypeCategoryUpdateProcessor(categoryRepository, iTypeCategoryService);
            // Act
            var r = processor.execute(new TypeCategoryUpdateCommand(null));
            // Assert
            assertNull(r);
        }

        @Test @DisplayName("debe retornar null cuando el id es null")
        void ejecutar_idNulo_retornaNull() {
            // Arrange
            var processor = new TypeCategoryUpdateProcessor(categoryRepository, iTypeCategoryService);
            var c = TypeCategory.builder().build();
            // Act
            var r = processor.execute(new TypeCategoryUpdateCommand(c));
            // Assert
            assertNull(r);
        }
    }

    @Nested @DisplayName("TypeCategoryDeleteProcessor")
    class DeleteProcessor {
        @Test @DisplayName("debe eliminar y retornar categoria")
        void ejecutar_elimina() {
            // Arrange
            var processor = new TypeCategoryDeleteProcessor(categoryRepository, iTypeCategoryService);
            var c = categoriaValida();
            // Act
            var r = processor.execute(new TypeCategoryDeleteCommand(c));
            // Assert
            assertEquals(c, r);
            verify(categoryRepository).delete(c.getId());
        }

        @Test @DisplayName("debe retornar null cuando el contexto es null")
        void ejecutar_contextoNulo_retornaNull() {
            // Arrange
            var processor = new TypeCategoryDeleteProcessor(categoryRepository, iTypeCategoryService);
            // Act
            var r = processor.execute(new TypeCategoryDeleteCommand(null));
            // Assert
            assertNull(r);
        }

        @Test @DisplayName("debe retornar null cuando el id es null")
        void ejecutar_idNulo_retornaNull() {
            // Arrange
            var processor = new TypeCategoryDeleteProcessor(categoryRepository, iTypeCategoryService);
            var c = TypeCategory.builder().build();
            // Act
            var r = processor.execute(new TypeCategoryDeleteCommand(c));
            // Assert
            assertNull(r);
        }
    }

    @Nested @DisplayName("TypeCategoryGetByIdProcessor")
    class GetByIdProcessor {
        @Test @DisplayName("debe retornar categoria cuando existe")
        void ejecutar_existe_retorna() {
            // Arrange
            var processor = new TypeCategoryGetByIdProcessor(categoryRepository);
            var c = categoriaValida();
            when(categoryRepository.findById(c.getId())).thenReturn(Optional.of(c));
            // Act
            var r = processor.execute(new GetTypeCategoryByIdQuery(c.getId()));
            // Assert
            assertEquals(c, r);
        }

        @Test @DisplayName("debe lanzar cuando no existe")
        void ejecutar_noExiste_lanza() {
            // Arrange
            var processor = new TypeCategoryGetByIdProcessor(categoryRepository);
            Long id = Math.abs(easyRandom.nextLong()) + 1L;
            when(categoryRepository.findById(id)).thenReturn(Optional.empty());
            // Act & Assert
            assertThrows(DomainException.class, () -> processor.execute(new GetTypeCategoryByIdQuery(id)));
        }

        @Test @DisplayName("debe retornar null cuando el contexto es null")
        void ejecutar_contextoNulo_retornaNull() {
            // Arrange
            var processor = new TypeCategoryGetByIdProcessor(categoryRepository);
            // Act
            var r = processor.execute(null);
            // Assert
            assertNull(r);
        }

        @Test @DisplayName("debe retornar null cuando el id es null")
        void ejecutar_idNulo_retornaNull() {
            // Arrange
            var processor = new TypeCategoryGetByIdProcessor(categoryRepository);
            // Act
            var r = processor.execute(new GetTypeCategoryByIdQuery(null));
            // Assert
            assertNull(r);
        }
    }

    @Nested @DisplayName("TypeCategoryGetAllProcessor")
    class GetAllProcessor {
        @Test @DisplayName("debe retornar lista desde el servicio")
        void ejecutar_retornaLista() {
            // Arrange
            var processor = new TypeCategoryGetAllProcessor(iTypeCategoryService);
            var c = categoriaValida();
            when(iTypeCategoryService.getComboSencillo(c)).thenReturn(List.of(c));
            // Act
            var r = processor.execute(new GetAllTypeCategoriesQuery(c));
            // Assert
            assertEquals(1, r.size());
        }
    }

    @Nested @DisplayName("TypeCategoryGetAllPaginadoProcessor")
    class GetAllPaginadoProcessor {
        @Mock private IPageableResult<TypeCategory> pageableResult;

        @Test @DisplayName("debe retornar pagina desde el servicio")
        void ejecutar_retornaPagina() {
            // Arrange
            var processor = new TypeCategoryGetAllPaginadoProcessor(iTypeCategoryService);
            var c = categoriaValida();
            PageContext<TypeCategory> pc = PageContext.<TypeCategory>builder()
                    .data(c).pageNumber(0).pageSize(10).sortBy("id").sortDir("asc").build();
            when(pageableResult.getTotalElements()).thenReturn(1L);
            when(pageableResult.getPageNumber()).thenReturn(0);
            when(pageableResult.getPageSize()).thenReturn(10);
            when(pageableResult.getContent()).thenReturn(List.of(c));
            when(iTypeCategoryService.getComboGrande(eq(c), any())).thenReturn(pageableResult);
            // Act
            var r = processor.execute(new GetAllTypeCategoriesPaginadoQuery(pc));
            // Assert
            assertNotNull(r);
            assertEquals(1, r.getTotalElements());
        }
    }
}
