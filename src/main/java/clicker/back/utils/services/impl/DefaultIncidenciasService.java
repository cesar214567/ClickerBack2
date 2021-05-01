package clicker.back.utils.services.impl;

import clicker.back.utils.entities.Incidencias;
import clicker.back.utils.repositories.IncidenciasRepository;
import clicker.back.utils.services.IncidenciasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultIncidenciasService implements IncidenciasService {

    @Autowired
    IncidenciasRepository incidenciasRepository;

    @Override
    public List<Incidencias> getAll() {
        return (List<Incidencias>) incidenciasRepository.findAll(Sort.by(Sort.Direction.ASC, "date"));
    }

    @Override
    public Incidencias save(Incidencias incidencias) {
        return incidenciasRepository.save(incidencias);
    }
}
