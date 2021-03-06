package clicker.back.repositories;

import clicker.back.antiguo.Autos;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.Tuple;
import java.util.List;

public interface AutosRepository extends PagingAndSortingRepository<Autos,Long> {
    @Query(nativeQuery = true,value = "select count(a.id_auto),a.marca,a.modelo,a.tipo_carroceria  from autos a  group by (a.marca,a.modelo,a.tipo_carroceria )")
    List<Tuple> filters();

    @Query(nativeQuery = true, value = "select distinct (a.marca) from autos a ")
    List<String> findDistinctMarca();


}
