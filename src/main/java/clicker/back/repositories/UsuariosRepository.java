package clicker.back.repositories;

import clicker.back.entities.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

public interface UsuariosRepository extends PagingAndSortingRepository<Usuario,Long> {

    Usuario findByCorreoAndPassword(String correo, String password);

    Usuario findByCorreo(String correo);

    Long countAllByEnabledAndValidated(Boolean enabled,Boolean validated);

    @Query(value = "select * from usuario u where u.correo=?1 and u.password=?2",nativeQuery = true)
    Usuario loginGetValidated(String correo,String password);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update usuario u set balance=balance+:extraBalance where u.correo=:correo ")
    void updateBalance(Float extraBalance,String correo);

    Boolean existsByCorreo(String correo );

    @Query(nativeQuery = true,value = "select u.id_usuario,u.num_telefono,u.nombre from usuario u where u.correo=:correo")
    Tuple getData(String correo);
}
