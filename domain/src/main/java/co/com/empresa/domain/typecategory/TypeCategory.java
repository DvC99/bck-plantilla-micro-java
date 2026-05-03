package co.com.empresa.domain.typecategory;


import co.com.empresa.commons.exception.DomainException;

import co.com.empresa.domain.constants.DomainErrors;

import lombok.*;


import java.time.LocalDateTime;


/**
 * Modelo de dominio que representa una categoría de tipos.
 * <p>
 * El código de la categoría debe ser único de forma global.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class TypeCategory {


    /**
     * Identificador único de la categoría.
     */

    private Long id;


    /**
     * Nombre de la categoría (requerido).
     */

    private String name;


    /**
     * Código único de la categoría a nivel global (requerido).
     */

    private String code;


    /**
     * Descripción adicional de la categoría.
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
     * Actualiza los detalles de la categoría.
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
     * Valida el estado interno de la categoría.
     *
     * @throws DomainException si alguna regla de estado es violada
     */
    public void validate() {

        if (this.name == null || this.name.isBlank()) {

            throw new DomainException(DomainErrors.ERROR_TYPE_CATEGORY_NAME_EMPTY);

        }

        if (this.code == null || this.code.isBlank()) {

            throw new DomainException(DomainErrors.ERROR_TYPE_CATEGORY_CODE_EMPTY);

        }

    }

}

