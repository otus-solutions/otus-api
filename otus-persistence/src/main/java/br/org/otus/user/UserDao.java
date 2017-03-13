package br.org.otus.user;

import br.org.otus.model.User;

import java.util.List;

public interface UserDao {
    User fetchByEmail(String email);

    Boolean emailExists(String email);

    User findAdmin();

    List<User> fetchAll();
}
