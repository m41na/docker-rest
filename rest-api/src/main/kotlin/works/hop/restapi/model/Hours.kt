package works.hop.restapi.model

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@ApiModel
data class Hours(
    var id: Long,

    @ApiModelProperty(position = 1, required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    var date: LocalDate,

    @ApiModelProperty(position = 2, required = true)
    @NotBlank @Min(0) @Max(24)
    var hours: Float,
) {
    constructor() : this(0, LocalDate.now(), 0.0F)
}
