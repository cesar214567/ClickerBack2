package clicker.back.services;

import clicker.back.entities.AutoSemiNuevo;
import clicker.back.entities.Denuncia;
import clicker.back.entities.Usuario;

import java.util.List;

public interface DenunciaService {
    Denuncia save(Denuncia denuncia);

    void delete(Denuncia denuncia);

    Denuncia getById(Long id );

    List<Denuncia> getAll();

    List<Long> getIdsAutosDenunciados();

    Denuncia getByAutoAndUsuario(AutoSemiNuevo autoSemiNuevo, Usuario usuario);
}
