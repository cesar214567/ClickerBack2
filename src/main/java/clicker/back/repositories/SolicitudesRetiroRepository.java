package clicker.back.repositories;

import clicker.back.entities.SolicitudesRetiro;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SolicitudesRetiroRepository extends PagingAndSortingRepository<SolicitudesRetiro,Long> {

    @Query(nativeQuery = true,value = "select * from solicitudes_retiro s where s.aceptado is null order by s.date asc ")
    List<SolicitudesRetiro> findAllByAceptado();
}
