package works.hop.rest.jwt.model

data class AuthRequest(val username: String, val password: String) {

    constructor() : this("", "")
}
