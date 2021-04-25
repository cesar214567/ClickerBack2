package clicker.back.repositories;

import clicker.back.entities.AutoSemiNuevo;
import clicker.back.entities.InteresadoReventa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface InteresadoReventaRepository extends PagingAndSortingRepository<InteresadoReventa,Long> {

    List<InteresadoReventa> findAllByAutoSemiNuevo(AutoSemiNuevo autoSemiNuevo);

    @Query(nativeQuery = true,value = "select * from interesado_reventa i where i.id_usuario=:userId")
    List<InteresadoReventa> findAllByCorreo(Long userId);

    @Query(nativeQuery = true,value = "select count(*) from interesado_reventa i where i.id_usuario=:userId and i.id_auto_semi_nuevo=:autoId")
    Integer existByAutoAndUsuario(Long autoId,Long userId);

}
