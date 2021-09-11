package works.hop.restapi.validator

interface RequestValidator {
    fun validate(target: Any): Set<String>
}
