package clicker.back.services;

import clicker.back.entities.AutoPatrocinado;
import clicker.back.entities.AutoSemiNuevo;

import java.util.List;

public interface AutoPatrocinadoService {

    AutoPatrocinado save(AutoPatrocinado autoPatrocinado);

    void delete(AutoPatrocinado autoPatrocinado);

    AutoPatrocinado findById(Long id);

    List<AutoPatrocinado> findAll();

    AutoPatrocinado findByAutoSemiNuevo(AutoSemiNuevo autoSemiNuevo);

}
