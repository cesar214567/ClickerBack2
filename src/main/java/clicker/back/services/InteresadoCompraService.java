package clicker.back.services;

import clicker.back.entities.AutoSemiNuevo;
import clicker.back.entities.InteresadoCompra;
import clicker.back.entities.InteresadoReventa;

import java.util.List;

public interface InteresadoCompraService {
    InteresadoCompra save(InteresadoCompra interesadoCompra);

    void delete(InteresadoCompra interesadoCompra);

    InteresadoCompra getById(Long id );

    List<InteresadoCompra> getAll();
    List<InteresadoCompra> getAllByAuto(AutoSemiNuevo autoSemiNuevo);

    Integer existByAutoAndCorreo(Long autoId,String correo);
}
