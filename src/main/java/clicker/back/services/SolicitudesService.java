package clicker.back.services;


import clicker.back.antiguo.Solicitudes;

import java.util.List;

public interface SolicitudesService {

    Solicitudes save(Solicitudes solicitudes);

    void delete(Solicitudes solicitudes);

    Solicitudes getById(Long id );

    List<Solicitudes> getAll();
}
