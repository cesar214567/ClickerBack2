package clicker.back.services.impl;

import clicker.back.entities.Users;
import clicker.back.repositories.UsersRepository;
import clicker.back.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultUsersService implements UsersService {

    @Autowired
    UsersRepository usersRepository;


    @Override
    public Users save(Users users) {
        return usersRepository.save(users);
    }

    @Override
    public void delete(Users users) {
        usersRepository.delete(users);
    }

    @Override
    public Users getById(String id) {
        return usersRepository.findById(id).orElse(null);
    }

    @Override
    public List<Users> getAll() {
        return (List<Users>) usersRepository.findAll();
    }

    @Override
    public Users login(String email, String password) {
        return usersRepository.findByEmailAndPassword(email,password);
    }

    @Override
    public Users getByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
