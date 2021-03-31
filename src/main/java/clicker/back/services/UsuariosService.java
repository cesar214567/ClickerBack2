package clicker.back.services;

import clicker.back.entities.Usuario;

import javax.persistence.Tuple;
import java.util.List;

public interface UsuariosService {


    Usuario save(Usuario usuario);

    void delete(Usuario usuario);

    Usuario getById(String id );

    List<Usuario> getAll();

    Long countUsers();

    Usuario login(String correo,String password);

    void updateBalance(Float extraBalance, String correo);

    Boolean existById(String correo);

    Tuple getData(String correo);
}
