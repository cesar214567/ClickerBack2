package clicker.back.repositories;

import clicker.back.entities.Usuario;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UsuariosRepository extends PagingAndSortingRepository<Usuario,String> {

    Usuario findByCorreoAndPassword(String correo, String password);

    Usuario findByCorreo(String correo);

    Long countAllByEnabledAndValidated(Boolean enabled,Boolean validated);
}
