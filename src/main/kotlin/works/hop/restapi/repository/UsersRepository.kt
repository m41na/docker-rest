package works.hop.restapi.repository

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import works.hop.restapi.entity.UserEntity

@Repository
interface UsersRepository : CrudRepository<UserEntity, Long> {

    @Query("select * from users u where u.active = true")
    fun allActiveUsers(): List<UserEntity>
}