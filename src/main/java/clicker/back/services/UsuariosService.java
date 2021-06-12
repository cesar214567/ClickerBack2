package clicker.back.services;

import clicker.back.entities.Usuario;

import javax.persistence.Tuple;
import java.util.List;

public interface UsuariosService {


    Usuario save(Usuario usuario);

    void delete(Usuario usuario);

    Usuario getByCorreo(String id );
    Usuario getById(Long id );

    List<Usuario> getAll();

    Long countUsers();

    Usuario login(String correo,String password);

    void updateBalance(Float extraBalance, String correo);

    Boolean existById(Long userId );

    Boolean existByCorreo(String correo  );

    Boolean existByNumDocumento(String numdocumento);
    Tuple getData(String correo);
}
