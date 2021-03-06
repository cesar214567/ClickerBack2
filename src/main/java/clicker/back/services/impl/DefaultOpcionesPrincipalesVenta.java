package clicker.back.services.impl;

import clicker.back.antiguo.OpcionesPrincipalesVenta;
import clicker.back.repositories.OpcionesPrincipalesVentaRepository;
import clicker.back.services.OpcionesPrincipalesVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultOpcionesPrincipalesVenta implements OpcionesPrincipalesVentaService {

    @Autowired
    OpcionesPrincipalesVentaRepository opcionesPrincipalesVentaRepository;

    @Override
    public OpcionesPrincipalesVenta save(OpcionesPrincipalesVenta opcionesPrincipalesVenta) {
        return opcionesPrincipalesVentaRepository.save(opcionesPrincipalesVenta);
    }

    @Override
    public void delete(OpcionesPrincipalesVenta opcionesPrincipalesVenta) {
        opcionesPrincipalesVentaRepository.delete(opcionesPrincipalesVenta);
    }

    @Override
    public OpcionesPrincipalesVenta getById(Long id ) {
        return opcionesPrincipalesVentaRepository.findById(id).orElse(null);
    }

    @Override
    public List<OpcionesPrincipalesVenta> getAll() {
        return (List<OpcionesPrincipalesVenta>) opcionesPrincipalesVentaRepository.findAll();
    }
}
