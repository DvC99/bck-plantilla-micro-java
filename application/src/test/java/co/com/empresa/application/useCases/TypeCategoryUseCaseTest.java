package co.com.empresa.application.useCases;

import co.com.empresa.application.typecategory.TypeCategoryApplicationMapper;
import co.com.empresa.application.typecategory.TypeCategoryFilterDto;
import co.com.empresa.application.typecategory.TypeCategoryRequestDto;
import co.com.empresa.application.typecategory.TypeCategoryResponseDto;
import co.com.empresa.application.typecategory.TypeCategoryUseCase;
import co.com.empresa.commons.dto.pageable.PageContext;
import co.com.empresa.commons.dto.request.PaginationRequest;
import co.com.empresa.domain.typecategory.*;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TypeCategoryUseCase")
class TypeCategoryUseCaseTest {

    @Mock private TypeCategoryCreateProcessor createProcessor;
    @Mock private TypeCategoryUpdateProcessor updateProcessor;
    @Mock private TypeCategoryDeleteProcessor deleteProcessor;
    @Mock private TypeCategoryGetByIdProcessor getByIdProcessor;
    @Mock private TypeCategoryGetAllProcessor getAllProcessor;
    @Mock private TypeCategoryGetAllPaginadoProcessor getAllPaginadoProcessor;
    @Mock private TypeCategoryApplicationMapper mapper;

    @InjectMocks private TypeCategoryUseCase useCase;

    private EasyRandom easyRandom;
    private TypeCategoryRequestDto requestDto;
    private TypeCategory category;
    private TypeCategoryResponseDto responseDto;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
        requestDto = new TypeCategoryRequestDto(null, "Name", "CODE", "Desc", true);
        category = TypeCategory.builder().id(1L).name("Name").code("CODE").description("Desc").active(true).build();
        responseDto = new TypeCategoryResponseDto(1L, "Name", "CODE", "Desc", true, "system", LocalDateTime.now(), null, null);
    }

    @Nested @DisplayName("create")
    class Create {
        @Test @DisplayName("debe crear categoria exitosamente")
        void create_exitoso() {
            // Arrange
            when(mapper.fromCreateDto(requestDto)).thenReturn(category);
            when(createProcessor.execute(any())).thenReturn(category);
            when(mapper.toResponseDto(any())).thenReturn(responseDto);
            // Act
            var r = useCase.create(requestDto);
            // Assert
            assertNotNull(r);
            assertEquals(responseDto, r);
            verify(createProcessor).execute(any());
        }
    }

    @Nested @DisplayName("update")
    class Update {
        @Test @DisplayName("debe actualizar categoria exitosamente")
        void update_exitoso() {
            // Arrange
            when(mapper.fromUpdateDto(requestDto)).thenReturn(category);
            when(updateProcessor.execute(any())).thenReturn(category);
            when(mapper.toResponseDto(any())).thenReturn(responseDto);
            // Act
            var r = useCase.update(requestDto);
            // Assert
            assertNotNull(r);
            assertEquals(responseDto, r);
            verify(updateProcessor).execute(any());
        }
    }

    @Nested @DisplayName("delete")
    class Delete {
        @Test @DisplayName("debe eliminar categoria exitosamente")
        void delete_exitoso() {
            // Arrange
            Long id = Math.abs(easyRandom.nextLong()) + 1L;
            when(deleteProcessor.execute(any())).thenReturn(category);
            when(mapper.toResponseDto(any())).thenReturn(responseDto);
            // Act
            var r = useCase.delete(id);
            // Assert
            assertNotNull(r);
            assertEquals(responseDto, r);
            verify(deleteProcessor).execute(any());
        }
    }

    @Nested @DisplayName("getById")
    class GetById {
        @Test @DisplayName("debe retornar categoria por id cuando existe")
        void getById_existe_retorna() {
            // Arrange
            Long id = Math.abs(easyRandom.nextLong()) + 1L;
            when(getByIdProcessor.execute(any())).thenReturn(category);
            when(mapper.toResponseDto(category)).thenReturn(responseDto);
            // Act
            var r = useCase.getById(id);
            // Assert
            assertNotNull(r);
            assertEquals(responseDto, r);
        }
    }

    @Nested @DisplayName("getAll")
    class GetAll {
        @Test @DisplayName("debe retornar lista de categorias")
        void getAll_retornaLista() {
            // Arrange
            TypeCategoryFilterDto filter = new TypeCategoryFilterDto(null, null, null, null);
            when(mapper.fromFilterDto(filter)).thenReturn(category);
            when(getAllProcessor.execute(any())).thenReturn(List.of(category));
            when(mapper.toResponseDtoList(anyList())).thenReturn(List.of(responseDto));
            // Act
            var r = useCase.getAll(filter);
            // Assert
            assertNotNull(r);
            assertEquals(1, r.size());
        }
    }

    @Nested @DisplayName("getAllPaginado")
    class GetAllPaginado {
        @Test @DisplayName("debe retornar pagina de categorias")
        void getAllPaginado_retornaPagina() {
            // Arrange
            TypeCategoryFilterDto filter = new TypeCategoryFilterDto(null, null, null, null);
            PaginationRequest pag = new PaginationRequest(0, 10, "id", "asc", null);
            when(mapper.fromFilterDto(filter)).thenReturn(category);
            Page<TypeCategory> page = new PageImpl<>(List.of(category));
            when(getAllPaginadoProcessor.execute(any())).thenReturn(page);
            when(mapper.toResponseDto(any())).thenReturn(responseDto);
            // Act
            var r = useCase.getAllPaginado(filter, pag);
            // Assert
            assertNotNull(r);
            assertEquals(1, r.getTotalElements());
        }
    }
}
