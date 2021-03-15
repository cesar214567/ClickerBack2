package clicker.back.repositories;

import clicker.back.entities.AutoSemiNuevo;
import clicker.back.entities.InteresadoCompra;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface InteresadoCompraRepository extends PagingAndSortingRepository<InteresadoCompra,Long> {

    List<InteresadoCompra> findAllByAutoSemiNuevo(AutoSemiNuevo autoSemiNuevo);

}
