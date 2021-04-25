package clicker.back.repositories;

import clicker.back.entities.AutoSemiNuevo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.transaction.Transactional;
import java.util.List;



public interface AutoSemiNuevoRepository extends PagingAndSortingRepository<AutoSemiNuevo,Long> {


    Page<AutoSemiNuevo> findAllByEnabledAndValidadoAndComprado(Boolean enabled,Boolean validado,Boolean comprado, Pageable pageable);

    Long countAllByEnabledAndValidadoAndComprado(Boolean enabled,Boolean validado,Boolean comprado);

    List<AutoSemiNuevo> findAllByEnabledAndValidadoAndComprado(Boolean enabled, Boolean validado, Boolean comprado);

    List<AutoSemiNuevo> findAllByPlaca(String placa);

    Long countAllByComprado(Boolean comprado);

    @Query(nativeQuery = true,value = "select count(distinct(a.marca)) from auto_semi_nuevo a where a.validado=true and a.enabled=true")
    Long countMarcas();

    @Query(nativeQuery = true,value = "select count(a.id_auto_semi_nuevo),a.marca,a.modelo,a.tipo_carroceria  from auto_semi_nuevo a  where a.validado=true and a.enabled=true group by (a.marca,a.modelo,a.tipo_carroceria )")
    List<Tuple> filters();

    @Query(nativeQuery = true,value = "select distinct (a.marca)from auto_semi_nuevo a  where a.validado=true and a.enabled=true")
    List<String> findAllMarcasDistinct();

    List<AutoSemiNuevo> findAllByValidadoAndEnabled(Boolean validado,Boolean enabled);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update auto_semi_nuevo a set revisado=:revisado where a.id_auto_semi_nuevo=:id ")
    void setRevisado(Boolean revisado,Long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update auto_semi_nuevo a set revisado=:revisado, enabled=:enabled where a.id_auto_semi_nuevo=:id ")
    void setRevisadoAndEnabled(Boolean revisado,Boolean enabled,Long id);

    List<AutoSemiNuevo> findAllByEnabledAndRevisadoAndComprado(Boolean enabled, Boolean revisado, Boolean comprado);

    @Query(nativeQuery = true,value = "select a.id_auto_semi_nuevo from auto_semi_nuevo a where a.id_usuario=:userId and a.comprado=true ")
    List<Long> findAllAutosNotComprados(Long userId);


}
