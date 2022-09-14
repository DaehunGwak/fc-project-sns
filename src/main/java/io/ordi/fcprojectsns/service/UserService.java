package io.ordi.fcprojectsns.service;

import io.ordi.fcprojectsns.exception.SnsApplicationException;
import io.ordi.fcprojectsns.model.User;
import io.ordi.fcprojectsns.model.entity.UserEntity;
import io.ordi.fcprojectsns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;

    public User join(String username, String password) {
        // username 이 기존에 있는지
        Optional<UserEntity> userEntity = userEntityRepository.findByUsername(username);

        // user 등록
        userEntityRepository.save(new UserEntity());

        return new User();
    }

    /**
     *
     * @return jwt token 반환
     */
    public String login(String username, String password) {
        // 회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUsername(username)
                .orElseThrow(SnsApplicationException::new);

        // 비밀번호 체크
        if (!userEntity.getPassword().equals(password)) {
            throw new SnsApplicationException();
        }

        // 토큰 생성
        return "";
    }
}
