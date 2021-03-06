package clicker.back.services;

import clicker.back.antiguo.VisitasConcesionario;

import java.util.List;

public interface VisitasConcesionarioService {


    VisitasConcesionario save(VisitasConcesionario visitasConcesionario);

    void delete(VisitasConcesionario visitasConcesionario);

    VisitasConcesionario getById(Long id );

    List<VisitasConcesionario> getAll();

}
