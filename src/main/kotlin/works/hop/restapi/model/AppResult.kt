package works.hop.restapi.model

data class AppResult<T>(val data: T?, val error: String?) {

    fun success(): Boolean {
        return data != null
    }
}
