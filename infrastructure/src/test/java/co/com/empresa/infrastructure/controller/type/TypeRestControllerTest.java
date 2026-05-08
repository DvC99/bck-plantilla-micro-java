package co.com.empresa.infrastructure.controller.type;

import co.com.empresa.application.type.TypeFilterDto;
import co.com.empresa.application.type.TypeRequestDto;
import co.com.empresa.application.type.TypeResponseDto;
import co.com.empresa.application.type.TypeUseCase;
import co.com.empresa.commons.dto.request.PaginationRequest;
import co.com.empresa.commons.helper.ApiResponseBuilder;
import co.com.empresa.commons.services.i18.MessageService;
import co.com.empresa.infrastructure.type.TypeRestController;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TypeRestController")
class TypeRestControllerTest {

    @Mock private TypeUseCase typeUseCase;
    @Mock private MessageService messageService;
    @Mock private ApiResponseBuilder responseBuilder;
    @InjectMocks private TypeRestController controller;

    private EasyRandom easyRandom;
    private TypeRequestDto requestDto;
    private TypeResponseDto responseDto;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
        requestDto = new TypeRequestDto(null, 1L, "Name", "CODE", "Desc", true);
        responseDto = new TypeResponseDto(1L, 1L, "Name", "CODE", "Desc", true, "sys", LocalDateTime.now(), null, null);
        when(messageService.getMessage(anyString())).thenReturn("OK");
    }

    @Nested @DisplayName("create")
    class Create {
        @Test @DisplayName("debe delegar en useCase.create y retornar success")
        void create_delega() {
            // Arrange
            when(typeUseCase.create(any())).thenReturn(responseDto);
            // Act
            controller.create(requestDto);
            // Assert
            verify(typeUseCase).create(any());
        }
    }

    @Nested @DisplayName("update")
    class Update {
        @Test @DisplayName("debe delegar en useCase.update y retornar success")
        void update_delega() {
            // Arrange
            when(typeUseCase.update(any())).thenReturn(responseDto);
            // Act
            controller.update(requestDto);
            // Assert
            verify(typeUseCase).update(any());
        }
    }

    @Nested @DisplayName("delete")
    class Delete {
        @Test @DisplayName("debe delegar en useCase.delete y retornar success")
        void delete_delega() {
            // Arrange
            Long id = Math.abs(easyRandom.nextLong()) + 1L;
            when(typeUseCase.delete(id)).thenReturn(responseDto);
            // Act
            controller.delete(id);
            // Assert
            verify(typeUseCase).delete(id);
        }
    }

    @Nested @DisplayName("getById")
    class GetById {
        @Test @DisplayName("debe delegar en useCase.getById y retornar success")
        void getById_delega() {
            // Arrange
            Long id = Math.abs(easyRandom.nextLong()) + 1L;
            when(typeUseCase.getById(id)).thenReturn(responseDto);
            // Act
            controller.getById(id);
            // Assert
            verify(typeUseCase).getById(id);
        }
    }

    @Nested @DisplayName("combo")
    class Combo {
        @Test @DisplayName("debe delegar en useCase.getCombo y retornar successList")
        void combo_delega() {
            // Arrange
            TypeFilterDto filter = new TypeFilterDto(null, null, null, null, null);
            when(typeUseCase.getCombo(any())).thenReturn(List.of(responseDto));
            // Act
            controller.combo(filter);
            // Assert
            verify(typeUseCase).getCombo(any());
        }
    }

    @Nested @DisplayName("paginado")
    class Paginado {
        @Test @DisplayName("debe delegar en useCase.getComboPaginado y retornar paginated")
        void paginado_delega() {
            // Arrange
            TypeFilterDto filter = new TypeFilterDto(null, null, null, null, null);
            PaginationRequest pag = new PaginationRequest(0, 10, "id", "asc", null);
            Page<TypeResponseDto> page = new PageImpl<>(List.of(responseDto));
            when(typeUseCase.getComboPaginado(any(), any())).thenReturn(page);
            // Act
            controller.paginado(filter, pag);
            // Assert
            verify(typeUseCase).getComboPaginado(any(), any());
        }
    }
}
