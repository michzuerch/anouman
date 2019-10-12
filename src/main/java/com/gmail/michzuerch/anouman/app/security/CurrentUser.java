package com.gmail.michael.app.security;

import com.gmail.michael.backend.data.entity.User;

@FunctionalInterface
public interface CurrentUser {

	User getUser();
}
