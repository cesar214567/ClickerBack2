package clicker.back.services;

import clicker.back.antiguo.EmpresasTaxi;

import java.util.List;

public interface EmpresasTaxiService {
    EmpresasTaxi save(EmpresasTaxi empresasTaxi);

    void delete(EmpresasTaxi empresasTaxi);

    EmpresasTaxi getById(Long id);

    List<EmpresasTaxi> getAll();


}
