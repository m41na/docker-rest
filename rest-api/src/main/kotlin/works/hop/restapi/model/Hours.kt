package works.hop.restapi.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class Hours(
    var id: Long,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    var date: LocalDate,
    var hours: Float,
) {
    constructor() : this(0, LocalDate.now(), 0.0F)
}
