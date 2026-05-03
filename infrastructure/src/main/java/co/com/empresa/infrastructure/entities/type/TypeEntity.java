package co.com.empresa.infrastructure.entities.type;import co.com.empresa.infrastructure.constants.EntitiesConstants;import co.com.empresa.infrastructure.entities.typeCategory.TypeCategoryEntity;import jakarta.persistence.*;import lombok.*;import org.hibernate.annotations.CreationTimestamp;import org.hibernate.annotations.UpdateTimestamp;import java.time.LocalDateTime;/**
 * JPA entity representing a Type in the system.
 * <p>
 * This entity maps to the types table and contains information about a specific type
 * associated with a type category.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(
        name = EntitiesConstants.TABLE_TYPE,        uniqueConstraints = {                @UniqueConstraint(                        name = EntitiesConstants.UQ_TYPE_CATEGORY_CODE_BY_CATEGORY,                        columnNames = {EntitiesConstants.COL_TYPE_CATEGORY_ID, EntitiesConstants.COL_CODE}                )        })public class TypeEntity {        /**
     * Unique identifier of the type.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "typeSeqGenerator")
    @SequenceGenerator(name = "typeSeqGenerator", sequenceName = EntitiesConstants.SEQ_TYPE_ID, allocationSize = 1)
    @Column(name = EntitiesConstants.COL_ID, nullable = false)
    private Long id;

    /**
     * Identifier of the associated type category.
     */
    @Column(name = EntitiesConstants.COL_TYPE_CATEGORY_ID, nullable = false)
    private Long typeCategoryId;

    /**
     * Reference to the associated {@link TypeCategoryEntity}.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = EntitiesConstants.COL_TYPE_CATEGORY_ID, insertable = false, updatable = false)
    private TypeCategoryEntity typeCategory;

    /**
     * Name of the type.
     */
    @Column(name = EntitiesConstants.COL_NAME, nullable = false, length = 150)
    private String name;

    /**
     * Unique code of the type.
     */
    @Column(name = EntitiesConstants.COL_CODE, nullable = false, length = 80)
    private String code;

    /**
     * Description of the type.
     */
    @Column(name = EntitiesConstants.COL_DESCRIPTION, length = 500)
    private String description;

    /**
     * Indicates if the type is active.
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
}