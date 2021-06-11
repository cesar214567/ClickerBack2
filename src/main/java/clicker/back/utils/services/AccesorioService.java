package clicker.back.utils.services;

import clicker.back.utils.entities.Accesorio;

import java.util.List;

public interface AccesorioService {

    List<Accesorio> getAllEnabled();

    List<Accesorio> getAll();   

    Accesorio save(Accesorio accesorio);

    Accesorio getById(Long id);


}
