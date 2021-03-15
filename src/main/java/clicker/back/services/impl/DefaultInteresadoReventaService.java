package clicker.back.services.impl;

import clicker.back.entities.AutoSemiNuevo;
import clicker.back.entities.InteresadoReventa;
import clicker.back.repositories.InteresadoReventaRepository;
import clicker.back.services.InteresadoReventaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultInteresadoReventaService implements InteresadoReventaService {
    @Autowired
    InteresadoReventaRepository interesadoReventaRepository;

    @Override
    public InteresadoReventa save(InteresadoReventa interesadoReventa) {
        return interesadoReventaRepository.save(interesadoReventa);
    }

    @Override
    public void delete(InteresadoReventa interesadoReventa) {
        interesadoReventaRepository.delete(interesadoReventa);
    }

    @Override
    public InteresadoReventa getById(Long id) {
        return interesadoReventaRepository.findById(id).orElse(null);
    }

    @Override
    public List<InteresadoReventa> getAll() {
        return (List<InteresadoReventa>) interesadoReventaRepository.findAll();
    }

    @Override
    public List<InteresadoReventa> getAllByAuto(AutoSemiNuevo autoSemiNuevo) {
        return interesadoReventaRepository.findAllByAutoSemiNuevo(autoSemiNuevo);
    }
}
