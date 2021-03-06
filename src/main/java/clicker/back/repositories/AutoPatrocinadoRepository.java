package clicker.back.repositories;

import clicker.back.entities.AutoPatrocinado;
import clicker.back.entities.AutoSemiNuevo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AutoPatrocinadoRepository extends PagingAndSortingRepository<AutoPatrocinado,Long> {
    List<AutoPatrocinado> findAllByOrderByLevelDesc();

    AutoPatrocinado findByAutoSemiNuevo(AutoSemiNuevo autoSemiNuevo);
}
