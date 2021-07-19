package clicker.back.repositories;

import clicker.back.entities.SolicitudRemocionAuto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SolicitudRemocionRepository extends PagingAndSortingRepository<SolicitudRemocionAuto,Long> {
    List<SolicitudRemocionAuto> findAllByAcceptedIsNull();


    @Query(nativeQuery = true,value = "select TRUE from solicitud_remocion_auto s where s.id_auto_semi_nuevo=:id")
    Boolean existByAuto(Long id);
}
