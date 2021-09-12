package works.hop.restapi.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class Hours(
    var id: Long,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    var date: LocalDate,
    @NotBlank @Min(0) @Max(24) var hours: Float,
) {
    constructor() : this(0, LocalDate.now(), 0.0F)
}
