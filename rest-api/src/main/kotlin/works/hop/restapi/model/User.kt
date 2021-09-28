package works.hop.restapi.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.jetbrains.annotations.NotNull

@ApiModel
data class User(
    @ApiModelProperty(position = 1, required = true)
    var id: Long?,

    @ApiModelProperty(position = 2, required = true)
    @NotNull @JsonProperty("first_name")
    var firstName: String,

    @ApiModelProperty(position = 3, required = true)
    @NotNull @JsonProperty("last_name")
    var lastName: String,

    @ApiModelProperty(position = 4, required = true)
    @NotNull var email: String,
) {
    constructor() : this(0, "", "", "")
}
