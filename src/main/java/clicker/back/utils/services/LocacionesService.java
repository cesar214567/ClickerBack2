package clicker.back.utils.services;

import clicker.back.utils.entities.Locaciones;

import java.util.List;

public interface LocacionesService {

    Locaciones save(Locaciones locaciones);

    void delete(Locaciones locaciones);

    Locaciones findById(String id);

    List<Locaciones> findAll();
}
