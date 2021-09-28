package works.hop.rest.jwt.model

data class AuthResponse(val token: String?) {

    constructor() : this("")
}
