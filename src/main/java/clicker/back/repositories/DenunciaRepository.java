package clicker.back.repositories;

import clicker.back.entities.AutoSemiNuevo;
import clicker.back.entities.Denuncia;
import clicker.back.entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DenunciaRepository extends PagingAndSortingRepository<Denuncia,Long> {

    @Query(value = "select distinct (id_auto_semi_nuevo)from denuncia",nativeQuery = true)
    List<Long> getIdsFromAutosDenunciados();

    Denuncia getByAutoSemiNuevoAndUsuario(AutoSemiNuevo autoSemiNuevo, Usuario usuario);
}
