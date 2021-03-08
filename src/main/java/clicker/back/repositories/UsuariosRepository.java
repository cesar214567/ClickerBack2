package clicker.back.repositories;

import clicker.back.entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UsuariosRepository extends PagingAndSortingRepository<Usuario,String> {

    Usuario findByCorreoAndPassword(String correo, String password);

    Usuario findByCorreo(String correo);

    Long countAllByEnabledAndValidated(Boolean enabled,Boolean validated);

    @Query(value = "select u.validated from usuario u where u.id_usuario=?1 and u.password=?2",nativeQuery = true)
    Boolean loginGetValidated(String correo,String password);
}
