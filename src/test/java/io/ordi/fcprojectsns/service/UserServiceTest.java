package io.ordi.fcprojectsns.service;

import io.ordi.fcprojectsns.exception.SnsApplicationException;
import io.ordi.fcprojectsns.fixture.UserEntityFixture;
import io.ordi.fcprojectsns.model.entity.UserEntity;
import io.ordi.fcprojectsns.repository.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @Test
    public void 회원가입() {
        String username = "username";
        String password = "password";
        when(userEntityRepository.findByUsername(username))
                .thenReturn(Optional.empty());
        when(userEntityRepository.save(any()))
                .thenReturn(UserEntityFixture.get(username, password));

        assertDoesNotThrow(() -> userService.join(username, password));
    }

    @Test
    public void 회원가입_시_username이_중복인_경우() {
        String username = "username";
        String password = "password";
        UserEntity userEntity = UserEntityFixture.get(username, password);

        when(userEntityRepository.findByUsername(username))
                .thenReturn(Optional.of(userEntity));
        when(userEntityRepository.save(any()))
                .thenReturn(userEntity);

        assertThrows(SnsApplicationException.class, () -> userService.join(username, password));
    }

    @Test
    public void 로그인() {
        String username = "username";
        String password = "password";
        UserEntity userEntity = UserEntityFixture.get(username, password);

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        assertDoesNotThrow(() -> userService.login(username, password));
    }

    @Test
    public void 로그인_시_username_찾을수_없음() {
        String username = "username";
        String password = "password";

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(SnsApplicationException.class, () -> userService.login(username, password));
    }

    @Test
    public void 로그인_시_password가_일치하지_않음() {
        String username = "username";
        String password = "password";
        String wrongPassword = "wrongPassword";
        UserEntity userEntity = UserEntityFixture.get(username, password);

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        assertThrows(SnsApplicationException.class, () -> userService.login(username, wrongPassword));
    }
}
