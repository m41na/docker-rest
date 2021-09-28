package works.hop.restapi.model

data class AuthRequest(val username: String, val password: String) {

    constructor() : this("", "")
}
