package clicker.back.services.impl;

import clicker.back.antiguo.VisitasConcesionario;
import clicker.back.repositories.VisitasConcesionarioRepository;
import clicker.back.services.VisitasConcesionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultVisitasConcesionarioService implements VisitasConcesionarioService {

    @Autowired
    VisitasConcesionarioRepository visitasConcesionarioRepository;

    @Override
    public VisitasConcesionario save(VisitasConcesionario visitasConcesionario) {
        return visitasConcesionarioRepository.save(visitasConcesionario);
    }

    @Override
    public void delete(VisitasConcesionario visitasConcesionario) {
        visitasConcesionarioRepository.delete(visitasConcesionario);
    }

    @Override
    public VisitasConcesionario getById(Long id) {
        return visitasConcesionarioRepository.findById(id).orElse(null);
    }

    @Override
    public List<VisitasConcesionario> getAll() {
        return (List<VisitasConcesionario>) visitasConcesionarioRepository.findAll();
    }
}
