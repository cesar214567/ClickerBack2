package clicker.back.services;

import clicker.back.entities.Users;

import java.util.List;

public interface UsersService {

    Users save(Users users);

    void delete(Users users);

    Users getById(String id );

    List<Users> getAll();

    Users login(String email, String password);

    Users getByEmail(String email);
}
