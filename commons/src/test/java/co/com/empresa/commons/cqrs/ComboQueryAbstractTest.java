package co.com.empresa.commons.cqrs;

import co.com.empresa.commons.exception.DomainException;
import co.com.empresa.commons.services.IGenericService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ComboQueryAbstract")
class ComboQueryAbstractTest {

    @Mock
    private IGenericService<String, Long> service;

    private ComboQueryAbstract<String, Long> combo() {
        return new ComboQueryAbstract<>(service) {};
    }

    @Test
    @DisplayName("debe retornar lista combo cuando el contexto es valido")
    void ejecutar_contextoValido_retornaLista() {
        // Arrange
        when(service.getComboSencillo("filtro")).thenReturn(List.of("a", "b"));
        // Act
        List<String> resultado = combo().execute("filtro");
        // Assert
        assertEquals(List.of("a", "b"), resultado);
        verify(service).getComboSencillo("filtro");
    }

    @Test
    @DisplayName("debe lanzar DomainException cuando el contexto es null")
    void ejecutar_contextoNulo_lanzaExcepcion() {
        // Arrange
        var c = combo();
        // Act & Assert
        assertThrows(DomainException.class, () -> c.execute(null));
        verifyNoInteractions(service);
    }
}
