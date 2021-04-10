package clicker.back.services;

import clicker.back.entities.Incidencias;

import java.util.List;

public interface IncidenciasService {

    List<Incidencias> getAll();

    Incidencias getById(Long id);

    void delete(Incidencias incidencias);

    Incidencias save(Incidencias incidencias);


}
