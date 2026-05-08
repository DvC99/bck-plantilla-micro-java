package co.com.empresa.infrastructure.controller.typecategory;

import co.com.empresa.application.typecategory.TypeCategoryFilterDto;
import co.com.empresa.application.typecategory.TypeCategoryRequestDto;
import co.com.empresa.application.typecategory.TypeCategoryResponseDto;
import co.com.empresa.application.typecategory.TypeCategoryUseCase;
import co.com.empresa.commons.dto.request.PaginationRequest;
import co.com.empresa.commons.helper.ApiResponseBuilder;
import co.com.empresa.commons.services.i18.MessageService;
import co.com.empresa.infrastructure.typecategory.TypeCategoryRestController;
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
@DisplayName("TypeCategoryRestController")
class TypeCategoryRestControllerTest {

    @Mock private TypeCategoryUseCase typeCategoryUseCase;
    @Mock private MessageService messageService;
    @Mock private ApiResponseBuilder responseBuilder;
    @InjectMocks private TypeCategoryRestController controller;

    private EasyRandom easyRandom;
    private TypeCategoryRequestDto requestDto;
    private TypeCategoryResponseDto responseDto;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
        requestDto = new TypeCategoryRequestDto(null, "Name", "CODE", "Desc", true);
        responseDto = new TypeCategoryResponseDto(1L, "Name", "CODE", "Desc", true, "sys", LocalDateTime.now(), null, null);
        when(messageService.getMessage(anyString())).thenReturn("OK");
    }

    @Nested @DisplayName("create")
    class Create {
        @Test @DisplayName("debe delegar en useCase.create y retornar success")
        void create_delega() {
            // Arrange
            when(typeCategoryUseCase.create(any())).thenReturn(responseDto);
            // Act
            controller.create(requestDto);
            // Assert
            verify(typeCategoryUseCase).create(any());
        }
    }

    @Nested @DisplayName("update")
    class Update {
        @Test @DisplayName("debe delegar en useCase.update y retornar success")
        void update_delega() {
            // Arrange
            when(typeCategoryUseCase.update(any())).thenReturn(responseDto);
            // Act
            controller.update(requestDto);
            // Assert
            verify(typeCategoryUseCase).update(any());
        }
    }

    @Nested @DisplayName("delete")
    class Delete {
        @Test @DisplayName("debe delegar en useCase.delete y retornar success")
        void delete_delega() {
            // Arrange
            Long id = Math.abs(easyRandom.nextLong()) + 1L;
            when(typeCategoryUseCase.delete(id)).thenReturn(responseDto);
            // Act
            controller.delete(id);
            // Assert
            verify(typeCategoryUseCase).delete(id);
        }
    }

    @Nested @DisplayName("getById")
    class GetById {
        @Test @DisplayName("debe delegar en useCase.getById y retornar success")
        void getById_delega() {
            // Arrange
            Long id = Math.abs(easyRandom.nextLong()) + 1L;
            when(typeCategoryUseCase.getById(id)).thenReturn(responseDto);
            // Act
            controller.getById(id);
            // Assert
            verify(typeCategoryUseCase).getById(id);
        }
    }

    @Nested @DisplayName("combo")
    class Combo {
        @Test @DisplayName("debe delegar en useCase.getAll y retornar successList")
        void combo_delega() {
            // Arrange
            TypeCategoryFilterDto filter = new TypeCategoryFilterDto(null, null, null, null);
            when(typeCategoryUseCase.getAll(any())).thenReturn(List.of(responseDto));
            // Act
            controller.combo(filter);
            // Assert
            verify(typeCategoryUseCase).getAll(any());
        }
    }

    @Nested @DisplayName("paginado")
    class Paginado {
        @Test @DisplayName("debe delegar en useCase.getAllPaginado y retornar paginated")
        void paginado_delega() {
            // Arrange
            TypeCategoryFilterDto filter = new TypeCategoryFilterDto(null, null, null, null);
            PaginationRequest pag = new PaginationRequest(0, 10, "id", "asc", null);
            Page<TypeCategoryResponseDto> page = new PageImpl<>(List.of(responseDto));
            when(typeCategoryUseCase.getAllPaginado(any(), any())).thenReturn(page);
            // Act
            controller.paginado(filter, pag);
            // Assert
            verify(typeCategoryUseCase).getAllPaginado(any(), any());
        }
    }
}
