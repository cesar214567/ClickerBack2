package clicker.back.services;

import clicker.back.entities.InteresadoCompra;
import clicker.back.entities.SolicitudesRetiro;

import java.util.List;

public interface SolicitudesRetiroService {
    SolicitudesRetiro save(SolicitudesRetiro solicitudesRetiro);

    void delete(SolicitudesRetiro solicitudesRetiro);

    SolicitudesRetiro getById(Long id );

    List<SolicitudesRetiro> getAll();
}
