package security.jwt.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import security.jwt.entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);
}
