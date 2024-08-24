package com.security.service;


import com.security.dto.JoinDTO;
import com.security.entity.Role;
import com.security.entity.UserEntity;
import com.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                UserEntity.builder().username(joinDTO.getUsername()).password(encodedPassword).role(Role.ROLE_ADMIN)
                        .build();

        userRepository.save(userEntity);
    }
}
