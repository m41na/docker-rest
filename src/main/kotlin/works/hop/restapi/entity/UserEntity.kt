package works.hop.restapi.entity

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("users")
data class UserEntity(
    @Id val id: Long?,
    @JsonProperty("first_name") @Column("first_name") val firstName: String?,
    @JsonProperty("last_name") @Column("last_name") val lastName: String?,
    val email: String?,
    val active: Boolean?,
    @Column("manager_id") val managerId: Long?,
    @JsonProperty("created_at") @Column("created_at") val createdAt: LocalDateTime?
) {
    constructor() : this(0, "", "", "", false, 0, LocalDateTime.now())
}
