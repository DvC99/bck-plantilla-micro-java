package co.com.empresa.domain.type;


import co.com.empresa.commons.exception.DomainException;

import co.com.empresa.domain.constants.DomainErrors;

import lombok.*;


import java.time.LocalDateTime;


/**
 * Modelo de dominio que representa un tipo dentro de una categoría.
 * <p>
 * El código de {@code Type} debe ser único dentro de su categoría
 * ({@code typeCategoryId + code}).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Type {


    /**
     * Identificador único del tipo.
     */

    private Long id;


    /**
     * Identificador de la categoría a la que pertenece este tipo (requerido).
     * <p>
     * Un tipo no puede existir sin una categoría asociada.
     */

    private Long typeCategoryId;


    /**
     * Nombre funcional del tipo (requerido).
     */

    private String name;


    /**
     * Código único del tipo dentro de su categoría (requerido).
     * <p>
     * Debe ser único para la combinación (typeCategoryId, code).
     */

    private String code;


    /**
     * Descripción adicional del tipo.
     */

    private String description;


    /**
     * Indicador de vigencia del registro.
     */

    private Boolean active;


    /**
     * Usuario que crea el registro.
     */

    private String createBy;


    /**
     * Fecha y hora de creación del registro.
     */

    private LocalDateTime createDate;


    /**
     * Usuario que realiza la última actualización.
     */

    private String updateBy;


    /**
     * Fecha y hora de la última actualización.
     */

    private LocalDateTime updateDate;


    /**
     * Actualiza los detalles del tipo.
     *
     * @param name        nuevo nombre
     * @param code        nuevo código
     * @param description nueva descripción
     * @param active      nuevo estado
     */

    public void updateDetails(String name, String code, String description, Boolean active) {

        this.name = name;

        this.code = code;

        this.description = description;

        this.active = active;

    }


    /**
     * Valida el estado interno del tipo.
     *
     * @throws DomainException si alguna regla de estado es violada
     */
    public void validate() {

        if (this.typeCategoryId == null) {

            throw new DomainException(DomainErrors.ERROR_TYPE_CATEGORY_ID_EMPTY);

        }

        if (this.name == null || this.name.isBlank()) {

            throw new DomainException(DomainErrors.ERROR_TYPE_NAME_EMPTY);

        }

        if (this.code == null || this.code.isBlank()) {

            throw new DomainException(DomainErrors.ERROR_TYPE_CODE_EMPTY);

        }

    }

}

