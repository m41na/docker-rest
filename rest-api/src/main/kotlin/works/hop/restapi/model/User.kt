package works.hop.restapi.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.jetbrains.annotations.NotNull

data class User(
    var id: Long?,
    @NotNull @JsonProperty("first_name") var firstName: String,
    @NotNull @JsonProperty("last_name") var lastName: String,
    @NotNull var email: String,
){
    constructor(): this(0, "", "", "")
}
