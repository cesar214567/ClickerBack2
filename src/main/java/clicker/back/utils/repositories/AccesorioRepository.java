package clicker.back.utils.repositories;

import clicker.back.utils.entities.Accesorio;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AccesorioRepository extends PagingAndSortingRepository<Accesorio,Long> {

    List<Accesorio> findAllByEnabled(Boolean enabled);
}
