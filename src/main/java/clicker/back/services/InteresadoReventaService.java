package clicker.back.services;

import clicker.back.entities.AutoSemiNuevo;
import clicker.back.entities.InteresadoReventa;

import java.util.List;

public interface InteresadoReventaService {

    InteresadoReventa save(InteresadoReventa interesadoReventa);

    void delete(InteresadoReventa interesadoReventa);

    InteresadoReventa getById(Long id);

    List<InteresadoReventa> getAll();

    List<InteresadoReventa> getAllByAuto(AutoSemiNuevo autoSemiNuevo);
    List<InteresadoReventa> getAllByUsuario(String correo);
    Integer existByAutoIdAndCorreo(Long autoId,String correo);
}
