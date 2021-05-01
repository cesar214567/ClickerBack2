package clicker.back.utils.services;

import clicker.back.utils.entities.Incidencias;

import java.util.List;

public interface IncidenciasService {

    List<Incidencias> getAll();

    Incidencias save(Incidencias incidencias);


}
