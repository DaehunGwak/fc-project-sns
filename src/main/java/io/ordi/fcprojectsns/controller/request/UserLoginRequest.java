package io.ordi.fcprojectsns.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginRequest {

    private String username;
    private String password;
}
