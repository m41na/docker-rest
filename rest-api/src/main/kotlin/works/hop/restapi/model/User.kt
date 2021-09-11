package works.hop.restapi.model

import com.fasterxml.jackson.annotation.JsonProperty

data class User(
    var id: Long?,
    @JsonProperty("first_name") var firstName: String,
    @JsonProperty("last_name") var lastName: String,
    var email: String,
){
    constructor(): this(0, "", "", "")
}
