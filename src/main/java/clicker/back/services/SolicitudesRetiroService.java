package clicker.back.services;

import clicker.back.controllers.RetirosController;
import clicker.back.entities.InteresadoCompra;
import clicker.back.entities.SolicitudesRetiro;
import clicker.back.entities.Usuario;

import java.util.List;

public interface SolicitudesRetiroService {
    SolicitudesRetiro save(SolicitudesRetiro solicitudesRetiro);

    void delete(SolicitudesRetiro solicitudesRetiro);

    SolicitudesRetiro getById(Long id );

    List<SolicitudesRetiro> getAll();

    List<SolicitudesRetiro> getPendientes();

    Boolean checkIfExist(Usuario usuario);

    List<SolicitudesRetiro> getAllAceptadosByUsuario(String correo);

    List<SolicitudesRetiro> findSolicitudVigente(String correo);
}
