package works.hop.restapi.model

data class AuthResponse(val token: String?) {

    constructor() : this("")
}
