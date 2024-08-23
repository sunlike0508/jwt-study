package security.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import security.jwt.entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {}
