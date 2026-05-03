package co.com.empresa.application.type;


import co.com.empresa.application.constants.ApplicationErrors;

import co.com.empresa.commons.util.ValidationGroup;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;


/**
 * DTO record de entrada para crear y actualizar {@code Type}.
 *
 * @param id             identificador único (requerido para actualizaciones y eliminaciones)
 * @param typeCategoryId identificador de la categoría asociada (requerido para creación y actualización)
 * @param name           nombre del tipo (requerido para creación y actualización)
 * @param code           código del tipo (requerido para creación y actualización)
 * @param description    descripción del tipo
 * @param active         estado de vigencia
 */
public record TypeRequestDto(
        @NotNull(groups = {ValidationGroup.OnUpdate.class, ValidationGroup.OnDelete.class}, message = "{" + ApplicationErrors.VALIDATION_TYPE_ID_REQUIRED + "}")
        Long id,

        @NotNull(groups = {ValidationGroup.OnCreate.class, ValidationGroup.OnUpdate.class}, message = "{" + ApplicationErrors.VALIDATION_TYPE_CATEGORY_ID_REQUIRED + "}")
        Long typeCategoryId,

        @NotBlank(groups = {ValidationGroup.OnCreate.class, ValidationGroup.OnUpdate.class}, message = "{" + ApplicationErrors.VALIDATION_TYPE_NAME_REQUIRED + "}")
        @Size(min = 1, max = 150, message = "{" + ApplicationErrors.VALIDATION_TYPE_NAME_SIZE + "}")
        String name,

        @NotBlank(groups = {ValidationGroup.OnCreate.class, ValidationGroup.OnUpdate.class}, message = "{" + ApplicationErrors.VALIDATION_TYPE_CODE_REQUIRED + "}")
        @Size(min = 1, max = 80, message = "{" + ApplicationErrors.VALIDATION_TYPE_CODE_SIZE + "}")
        String code,

        @Size(max = 500, message = "{" + ApplicationErrors.VALIDATION_TYPE_DESCRIPTION_SIZE + "}")
        String description,

        Boolean active
) {
}










