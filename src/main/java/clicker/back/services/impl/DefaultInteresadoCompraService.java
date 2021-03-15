package clicker.back.services.impl;

import clicker.back.entities.AutoSemiNuevo;
import clicker.back.entities.InteresadoCompra;
import clicker.back.repositories.InteresadoCompraRepository;
import clicker.back.services.InteresadoCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultInteresadoCompraService implements InteresadoCompraService {
    @Autowired
    InteresadoCompraRepository interesadoCompraRepository;


    @Override
    public InteresadoCompra save(InteresadoCompra interesadoCompra) {
        return interesadoCompraRepository.save(interesadoCompra);
    }

    @Override
    public void delete(InteresadoCompra interesadoCompra) {
        interesadoCompraRepository.delete(interesadoCompra);
    }

    @Override
    public InteresadoCompra getById(Long id) {
        return interesadoCompraRepository.findById(id ).orElse(null);
    }

    @Override
    public List<InteresadoCompra> getAll() {
        return (List<InteresadoCompra>) interesadoCompraRepository.findAll();
    }

    @Override
    public List<InteresadoCompra> getAllByAuto(AutoSemiNuevo autoSemiNuevo) {
        return interesadoCompraRepository.findAllByAutoSemiNuevo(autoSemiNuevo);
    }
}
