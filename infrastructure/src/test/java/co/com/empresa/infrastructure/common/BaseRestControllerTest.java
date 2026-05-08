package co.com.empresa.infrastructure.common;

import co.com.empresa.commons.dto.response.GenericResponse;
import co.com.empresa.commons.helper.ApiResponseBuilder;
import co.com.empresa.commons.services.i18.MessageService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BaseRestController")
class BaseRestControllerTest {

    @Mock private ApiResponseBuilder responseBuilder;
    @Mock private MessageService messageService;

    private BaseRestController controller;

    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
        controller = new BaseRestController(responseBuilder, messageService) {};
    }

    @Nested @DisplayName("success")
    class Success {
        @Test @DisplayName("debe delegar en ApiResponseBuilder.success con mensaje del MessageService")
        void success_delega() {
            // Arrange
            String key = easyRandom.nextObject(String.class);
            String translated = easyRandom.nextObject(String.class);
            GenericResponse<String> expected = GenericResponse.success(200, translated, "data");
            when(messageService.getMessage(key)).thenReturn(translated);
            when(responseBuilder.success("data", translated)).thenReturn(expected);
            // Act
            var r = controller.success("data", key);
            // Assert
            assertEquals(expected, r);
        }
    }

    @Nested @DisplayName("successList")
    class SuccessList {
        @Test @DisplayName("debe delegar en ApiResponseBuilder.successList con mensaje traducido")
        void successList_delega() {
            // Arrange
            String key = easyRandom.nextObject(String.class);
            String translated = easyRandom.nextObject(String.class);
            List<String> list = easyRandom.objects(String.class, 3).toList();
            GenericResponse<String> expected = GenericResponse.success(200, translated, list);
            when(messageService.getMessage(key)).thenReturn(translated);
            when(responseBuilder.successList(list, translated)).thenReturn(expected);
            // Act
            var r = controller.successList(list, key);
            // Assert
            assertEquals(expected, r);
        }
    }

    @Nested @DisplayName("paginated")
    class Paginated {
        @Test @DisplayName("debe delegar en ApiResponseBuilder.paginated con mensaje traducido")
        void paginated_delega() {
            // Arrange
            String key = easyRandom.nextObject(String.class);
            String translated = easyRandom.nextObject(String.class);
            Page<String> page = new PageImpl<>(List.of("a"), PageRequest.of(0, 10), 1);
            GenericResponse<String> expected = GenericResponse.successPaginated(200, translated, List.of("a"), 1, "1/1");
            when(messageService.getMessage(key)).thenReturn(translated);
            when(responseBuilder.paginated(page, translated)).thenReturn(expected);
            // Act
            var r = controller.paginated(page, key);
            // Assert
            assertEquals(expected, r);
        }
    }
}
