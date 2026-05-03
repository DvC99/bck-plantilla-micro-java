package co.com.empresa.application.type;


import java.time.LocalDateTime;


/**
 * DTO record de salida para {@code Type}.
 *
 * @param id             identificador único del tipo
 * @param typeCategoryId identificador de la categoría asociada
 * @param name           nombre del tipo
 * @param code           código del tipo
 * @param description    descripción del tipo
 * @param active         estado de vigencia
 * @param createBy       usuario que creó el registro
 * @param createDate     fecha de creación
 * @param updateBy       usuario que actualizó el registro
 * @param updateDate     fecha de la última actualización
 */
public record TypeResponseDto(
        Long id,
        Long typeCategoryId,
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












