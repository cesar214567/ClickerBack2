package clicker.back.repositories;

import clicker.back.entities.AutoSemiNuevo;
import clicker.back.entities.InteresadoReventa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface InteresadoReventaRepository extends PagingAndSortingRepository<InteresadoReventa,Long> {

    List<InteresadoReventa> findAllByAutoSemiNuevo(AutoSemiNuevo autoSemiNuevo);

    @Query(nativeQuery = true,value = "select * from interesado_reventa i where i.id_usuario=:correo")
    List<InteresadoReventa> findAllByCorreo(String correo);

}
