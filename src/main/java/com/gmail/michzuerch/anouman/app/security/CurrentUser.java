package com.gmail.michzuerch.anouman.app.security;

import com.gmail.michzuerch.anouman.backend.data.entity.User;

@FunctionalInterface
public interface CurrentUser {

	User getUser();
}
