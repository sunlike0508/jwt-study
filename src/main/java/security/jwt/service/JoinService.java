package security.jwt.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import security.jwt.dto.JoinDTO;
import security.jwt.entity.Role;
import security.jwt.entity.UserEntity;
import security.jwt.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional
    public void join(JoinDTO joinDTO) {

        // 여기에 중복 id 검사 해야함

        String encodedPassword = bCryptPasswordEncoder.encode(joinDTO.getPassword());

        UserEntity userEntity =
                UserEntity.builder().username(joinDTO.getUsername()).password(encodedPassword).role(Role.USER).build();

        userRepository.save(userEntity);
    }
}