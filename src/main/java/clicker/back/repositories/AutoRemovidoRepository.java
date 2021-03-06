package clicker.back.repositories;

import clicker.back.entities.SolicitudRemocionAuto;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AutoRemovidoRepository extends PagingAndSortingRepository<SolicitudRemocionAuto,Long> {
}
