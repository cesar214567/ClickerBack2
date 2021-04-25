package clicker.back.services;

import clicker.back.entities.AutoSemiNuevo;
import org.springframework.data.domain.Pageable;

import javax.persistence.Tuple;
import java.util.List;

public interface AutoSemiNuevoService {

    AutoSemiNuevo save(AutoSemiNuevo autoSemiNuevo);

    void delete(AutoSemiNuevo autoSemiNuevo);

    AutoSemiNuevo getById(Long id );

    List<AutoSemiNuevo> getAll();

    List<AutoSemiNuevo> getAllEnabled(Boolean enabled,Boolean validado,Boolean comprado, Pageable pageable);

    Long getPages(Boolean enabled, Boolean validado, Boolean comprado);

    List<AutoSemiNuevo> getAllEnabled(Boolean enabled,Boolean validado,Boolean comprado);

    List<AutoSemiNuevo> getAllFromIdList(List<Long> ids);

    List<AutoSemiNuevo>  getByPlaca(String placa);

    Long getAllVendidos();

    Long getAllNoVendidos();

    Long getAllMarcas();

    List<Tuple> getFilters();

    List<String> getAllMarcasString();

    List<AutoSemiNuevo> getAutosNoValidados();

    void setRevisado(Boolean revisado,Long id);

    void borrarAuto(Long id);

    List<AutoSemiNuevo> getReportados();

    List<Long> getAllAutosVendidosByUsuario(Long userId);
}
