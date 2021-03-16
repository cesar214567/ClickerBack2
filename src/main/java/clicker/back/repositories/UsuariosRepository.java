package clicker.back.repositories;

import clicker.back.entities.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;

public interface UsuariosRepository extends PagingAndSortingRepository<Usuario,String> {

    Usuario findByCorreoAndPassword(String correo, String password);

    Usuario findByCorreo(String correo);

    Long countAllByEnabledAndValidated(Boolean enabled,Boolean validated);

    @Query(value = "select * from usuario u where u.id_usuario=?1 and u.password=?2",nativeQuery = true)
    Usuario loginGetValidated(String correo,String password);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update usuario u set balance=balance+:extraBalance where u.id_usuario=:correo ")
    void updateBalance(Float extraBalance,String correo);
}
