package co.com.empresa.application.typecategory;


import co.com.empresa.application.constants.ApplicationErrors;

import co.com.empresa.commons.util.ValidationGroup;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;


/**
 * DTO record de entrada para crear y actualizar {@code TypeCategory}.
 *
 * @param id          identificador único (requerido para actualizaciones y eliminaciones)
 * @param name        nombre de la categoría (requerido para creación y actualización)
 * @param code        código de la categoría (requerido para creación y actualización)
 * @param description descripción de la categoría
 * @param active      estado de vigencia
 */
public record TypeCategoryRequestDto(
        @NotNull(groups = {ValidationGroup.OnUpdate.class, ValidationGroup.OnDelete.class}, message = "{" + ApplicationErrors.VALIDATION_CATEGORY_ID_REQUIRED + "}")
        Long id,

        @NotBlank(groups = {ValidationGroup.OnCreate.class, ValidationGroup.OnUpdate.class}, message = "{" + ApplicationErrors.VALIDATION_CATEGORY_NAME_REQUIRED + "}")
        @Size(min = 1, max = 150, message = "{" + ApplicationErrors.VALIDATION_CATEGORY_NAME_SIZE + "}")
        String name,

        @NotBlank(groups = {ValidationGroup.OnCreate.class, ValidationGroup.OnUpdate.class}, message = "{" + ApplicationErrors.VALIDATION_CATEGORY_CODE_REQUIRED + "}")
        @Size(min = 1, max = 80, message = "{" + ApplicationErrors.VALIDATION_CATEGORY_CODE_SIZE + "}")
        String code,

        @Size(max = 500, message = "{" + ApplicationErrors.VALIDATION_CATEGORY_DESCRIPTION_SIZE + "}")
        String description,

        Boolean active
) {
}










