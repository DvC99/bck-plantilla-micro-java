package co.com.empresa.application.typecategory;


import java.time.LocalDateTime;


/**
 * DTO record de salida para {@code TypeCategory}.
 *
 * @param id          identificador único de la categoría
 * @param name        nombre de la categoría
 * @param code        código de la categoría
 * @param description descripción de la categoría
 * @param active      estado de vigencia
 * @param createBy    usuario que creó el registro
 * @param createDate  fecha de creación
 * @param updateBy    usuario que actualizó el registro
 * @param updateDate  fecha de la última actualización
 */
public record TypeCategoryResponseDto(
        Long id,
        String name,
        String code,
        String description,
        Boolean active,
        String createBy,
        LocalDateTime createDate,
        String updateBy,
        LocalDateTime updateDate
) {
}












