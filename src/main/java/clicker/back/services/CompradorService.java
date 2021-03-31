package clicker.back.services;

import clicker.back.entities.Comprador;
import clicker.back.entities.Denuncia;

import java.util.List;

public interface CompradorService {

    Comprador save(Comprador comprador);

    void delete(Comprador comprador);

    Comprador getById(String id );

    List<Comprador> getAll();
}
