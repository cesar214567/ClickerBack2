package clicker.back.repositories;

import clicker.back.entities.Usuario;
import clicker.back.entities.VentaSemiNuevo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface VentaSemiNuevoRepository extends PagingAndSortingRepository<VentaSemiNuevo,Long> {

    @Query(nativeQuery = true,value = "select * from venta_semi_nuevo v where v.id_auto_semi_nuevo in :ids ")
    List<VentaSemiNuevo> findAllByAuto(List<Long> ids);

    @Query(nativeQuery = true,value = "select * from venta_semi_nuevo v where v.id_vendedor=:id")
    List<VentaSemiNuevo> findAllByVendedor(Long id);
}
