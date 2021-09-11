package works.hop.restapi.model

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate

@ApiModel
data class Hours(
    var id: Long,

    @ApiModelProperty(position = 1, required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    var date: LocalDate,

    @ApiModelProperty(position = 2, required = true)
    var hours: Float,
) {
    constructor() : this(0, LocalDate.now(), 0.0F)
}
