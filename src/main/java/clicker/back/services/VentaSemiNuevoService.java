package clicker.back.services;

import clicker.back.entities.Usuario;
import clicker.back.entities.VentaSemiNuevo;

import java.util.List;

public interface VentaSemiNuevoService {


    VentaSemiNuevo save(VentaSemiNuevo ventaSemiNuevo);

    void delete(VentaSemiNuevo ventaSemiNuevo);

    VentaSemiNuevo getById(Long id);

    List<VentaSemiNuevo> getAll();

    List<VentaSemiNuevo> getVentasByUsuario(String string);

    List<VentaSemiNuevo> getByIdAuto(List<Long> id);

}

