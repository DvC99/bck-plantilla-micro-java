package co.com.empresa.infrastructure.entities.typeCategory;import co.com.empresa.infrastructure.constants.EntitiesConstants;import co.com.empresa.infrastructure.entities.type.TypeEntity;import jakarta.persistence.*;import lombok.*;import org.hibernate.annotations.CreationTimestamp;import org.hibernate.annotations.UpdateTimestamp;import java.time.LocalDateTime;import java.util.List;/**
 * JPA entity representing a Type Category in the system.
 * <p>
 * This entity maps to the type_category table and serves as a group for multiple types.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(
        name = EntitiesConstants.TABLE_TYPE_CATEGORY,        uniqueConstraints = {                @UniqueConstraint(                        name = EntitiesConstants.UQ_TYPE_CATEGORY_CODE,                        columnNames = {EntitiesConstants.COL_CODE}                )        })public class TypeCategoryEntity {        /**
     * Unique identifier of the type category.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "typeCategorySeqGenerator")
    @SequenceGenerator(name = "typeCategorySeqGenerator", sequenceName = EntitiesConstants.SEQ_TYPE_CATEGORY_ID, allocationSize = 1)
    @Column(name = EntitiesConstants.COL_ID, nullable = false)
    private Long id;

    /**
     * Name of the type category.
     */
    @Column(name = EntitiesConstants.COL_NAME, nullable = false, length = 150)
    private String name;

    /**
     * Unique code of the type category.
     */
    @Column(name = EntitiesConstants.COL_CODE, nullable = false, length = 80)
    private String code;

    /**
     * Description of the type category.
     */
    @Column(name = EntitiesConstants.COL_DESCRIPTION, length = 500)
    private String description;

    /**
     * Indicates if the type category is active.
     */
    @Column(name = EntitiesConstants.COL_ACTIVE)
    private Boolean active;

    /**
     * User who created the record.
     */
    @Column(name = EntitiesConstants.COL_CREATE_BY, length = 80)
    private String createBy;

    /**
     * Date and time when the record was created.
     */
    @CreationTimestamp
    @Column(name = EntitiesConstants.COL_CREATE_DATE, updatable = false)
    private LocalDateTime createDate;

    /**
     * User who last updated the record.
     */
    @Column(name = EntitiesConstants.COL_UPDATE_BY, length = 80)
    private String updateBy;

    /**
     * Date and time when the record was last updated.
     */
    @UpdateTimestamp
    @Column(name = EntitiesConstants.COL_UPDATE_DATE)
    private LocalDateTime updateDate;

    /**
     * List of types associated with this type category.
     */
    @OneToMany(mappedBy = "typeCategory", fetch = FetchType.LAZY)
    private List<TypeEntity> types;
}