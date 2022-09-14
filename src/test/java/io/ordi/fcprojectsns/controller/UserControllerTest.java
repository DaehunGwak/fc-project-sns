package io.ordi.fcprojectsns.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ordi.fcprojectsns.controller.request.UserJoinRequest;
import io.ordi.fcprojectsns.controller.request.UserLoginRequest;
import io.ordi.fcprojectsns.exception.SnsApplicationException;
import io.ordi.fcprojectsns.model.User;
import io.ordi.fcprojectsns.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void 회원가입() throws Exception {
        // given
        String username = "username";
        String password = "password";
        when(userService.join(username, password)).thenReturn(mock(User.class));

        // when & then
        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 회원가입_시_유저네임_중복이면_에러() throws Exception {
        // given
        String username = "username";
        String password = "password";
        when(userService.join(username, password)).thenThrow(new SnsApplicationException());

        // when & then
        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void 로그인() throws Exception {
        // given
        String username = "username";
        String password = "password";
        when(userService.login(username, password)).thenReturn("test_token");

        // when & then
        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 로그인_시_회원가입이_안된_username_입력으로_인한_에러반환() throws Exception {
        // given
        String username = "username";
        String password = "password";
        when(userService.login(username, password)).thenThrow(new SnsApplicationException());

        // when & then
        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void 로그인_시_틀린_password를_입력할_경우_에러반환() throws Exception {
        // given
        String username = "username";
        String password = "password";
        when(userService.login(username, password)).thenThrow(new SnsApplicationException());

        // when & then
        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
