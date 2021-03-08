package clicker.back.services.impl;

import clicker.back.entities.Usuario;
import clicker.back.repositories.UsuariosRepository;
import clicker.back.services.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultUsuariosService implements UsuariosService {

    @Autowired
    UsuariosRepository usuariosRepository;


    @Override
    public Boolean login(String correo, String password ) {
        return usuariosRepository.loginGetValidated(correo,password);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuariosRepository.save(usuario);
    }

    @Override
    public void delete(Usuario usuario) {
        usuariosRepository.delete(usuario);
    }

    @Override
    public Usuario getById(String id ) {
        return usuariosRepository.findByCorreo(id);
    }

    @Override
    public List<Usuario> getAll() {
        return (List<Usuario>) usuariosRepository.findAll();
    }

    @Override
    public Long countUsers() {
        return usuariosRepository.countAllByEnabledAndValidated(true,true);
    }
}
