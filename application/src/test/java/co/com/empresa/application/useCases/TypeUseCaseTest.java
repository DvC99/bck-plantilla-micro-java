package co.com.empresa.application.useCases;

import co.com.empresa.application.type.*;
import co.com.empresa.commons.dto.request.PaginationRequest;
import co.com.empresa.domain.type.*;
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
@DisplayName("TypeUseCase")
class TypeUseCaseTest {

    @Mock private TypeCreateProcessor createProcessor;
    @Mock private TypeUpdateProcessor updateProcessor;
    @Mock private TypeDeleteProcessor deleteProcessor;
    @Mock private TypeGetByIdProcessor getByIdProcessor;
    @Mock private TypeGetComboProcessor getComboProcessor;
    @Mock private TypeGetComboPaginadoProcessor getComboPaginadoProcessor;
    @Mock private TypeApplicationMapper mapper;

    @InjectMocks private TypeUseCase useCase;

    private EasyRandom easyRandom;
    private TypeRequestDto requestDto;
    private Type type;
    private TypeResponseDto responseDto;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
        requestDto = new TypeRequestDto(null, 1L, "Name", "CODE", "Desc", true);
        type = Type.builder().id(1L).typeCategoryId(1L).name("Name").code("CODE").description("Desc").active(true).build();
        responseDto = new TypeResponseDto(1L, 1L, "Name", "CODE", "Desc", true, "system", LocalDateTime.now(), null, null);
    }

    @Nested @DisplayName("create")
    class Create {
        @Test @DisplayName("debe crear tipo exitosamente")
        void create_exitoso() {
            // Arrange
            when(mapper.fromCreateDto(requestDto)).thenReturn(type);
            when(createProcessor.execute(any())).thenReturn(type);
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
        @Test @DisplayName("debe actualizar tipo exitosamente")
        void update_exitoso() {
            // Arrange
            when(mapper.fromUpdateDto(requestDto)).thenReturn(type);
            when(updateProcessor.execute(any())).thenReturn(type);
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
        @Test @DisplayName("debe eliminar tipo exitosamente")
        void delete_exitoso() {
            // Arrange
            Long id = Math.abs(easyRandom.nextLong()) + 1L;
            when(deleteProcessor.execute(any())).thenReturn(type);
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
        @Test @DisplayName("debe retornar tipo por id")
        void getById_existe_retorna() {
            // Arrange
            Long id = Math.abs(easyRandom.nextLong()) + 1L;
            when(getByIdProcessor.execute(any())).thenReturn(type);
            when(mapper.toResponseDto(type)).thenReturn(responseDto);
            // Act
            var r = useCase.getById(id);
            // Assert
            assertNotNull(r);
            assertEquals(responseDto, r);
        }
    }

    @Nested @DisplayName("getCombo")
    class GetCombo {
        @Test @DisplayName("debe retornar lista de tipos")
        void getCombo_retornaLista() {
            // Arrange
            TypeFilterDto filter = new TypeFilterDto(null, null, null, null, null);
            when(mapper.fromFilterDto(filter)).thenReturn(type);
            when(getComboProcessor.execute(any())).thenReturn(List.of(type));
            when(mapper.toResponseDtoList(anyList())).thenReturn(List.of(responseDto));
            // Act
            var r = useCase.getCombo(filter);
            // Assert
            assertNotNull(r);
            assertEquals(1, r.size());
        }
    }

    @Nested @DisplayName("getComboPaginado")
    class GetComboPaginado {
        @Test @DisplayName("debe retornar pagina de tipos")
        void getComboPaginado_retornaPagina() {
            // Arrange
            TypeFilterDto filter = new TypeFilterDto(null, null, null, null, null);
            PaginationRequest pag = new PaginationRequest(0, 10, "id", "asc", null);
            when(mapper.fromFilterDto(filter)).thenReturn(type);
            Page<Type> page = new PageImpl<>(List.of(type));
            when(getComboPaginadoProcessor.execute(any())).thenReturn(page);
            when(mapper.toResponseDto(any())).thenReturn(responseDto);
            // Act
            var r = useCase.getComboPaginado(filter, pag);
            // Assert
            assertNotNull(r);
            assertEquals(1, r.getTotalElements());
        }
    }
}
