package clicker.back.repositories;

import clicker.back.entities.AutoSemiNuevo;
import clicker.back.entities.InteresadoCompra;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface InteresadoCompraRepository extends PagingAndSortingRepository<InteresadoCompra,Long> {

    List<InteresadoCompra> findAllByAutoSemiNuevo(AutoSemiNuevo autoSemiNuevo);

    @Query(nativeQuery = true,value = "select count(*) from interesado_compra i where i.id_auto_semi_nuevo=:autoId and i.correo=:correo")
    Integer existByAutoAndCorreo(Long autoId,String correo);

}
