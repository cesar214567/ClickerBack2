package clicker.back.services;

import clicker.back.antiguo.Registrodecambio;

import java.util.List;

public interface RegistrodecambioService {

    Registrodecambio save(Registrodecambio registrodecambio);

    void delete(Registrodecambio registrodecambio);

    Registrodecambio getById(Long id );

    List<Registrodecambio> getAll();
}


