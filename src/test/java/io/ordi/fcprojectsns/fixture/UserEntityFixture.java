package io.ordi.fcprojectsns.fixture;

import io.ordi.fcprojectsns.model.entity.UserEntity;

public class UserEntityFixture {

    public static UserEntity get(String username, String password) {
        return UserEntity.builder()
                .username(username)
                .password(password)
                .build();
    }
}
