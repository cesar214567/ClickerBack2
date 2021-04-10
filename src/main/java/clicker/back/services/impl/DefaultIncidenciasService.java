package clicker.back.services.impl;

import clicker.back.entities.Incidencias;
import clicker.back.repositories.IncidenciasRepository;
import clicker.back.services.IncidenciasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultIncidenciasService implements IncidenciasService {

    @Autowired
    IncidenciasRepository incidenciasRepository;

    @Override
    public List<Incidencias> getAll() {
        return (List<Incidencias>) incidenciasRepository.findAll();
    }

    @Override
    public Incidencias getById(Long id) {
        return incidenciasRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Incidencias incidencias) {
        incidenciasRepository.delete(incidencias);
    }

    @Override
    public Incidencias save(Incidencias incidencias) {
        return incidenciasRepository.save(incidencias);
    }
}
