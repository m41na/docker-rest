package works.hop.restapi.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.time.LocalDateTime

@Table("worked_hours")
data class HoursEntity(
    @Id @Column("user_id") val id: Long,
    val date: LocalDate,
    val hours: Float,
    @Column("created_at") val createdAt: LocalDateTime?
) {
    constructor() : this(0, LocalDate.now(), 0.0F, LocalDateTime.now())
}
