package clicker.back.services.impl;

import clicker.back.antiguo.SedesConcesionarios;
import clicker.back.repositories.SedesConcesionariosRepository;
import clicker.back.services.SedesConcesionariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultSedesConcesionariosService implements SedesConcesionariosService {
    @Autowired
    SedesConcesionariosRepository sedesConcesionariosRepository;


    @Override
    public SedesConcesionarios save(SedesConcesionarios sedesConcesionarios) {
        return sedesConcesionariosRepository.save(sedesConcesionarios);
    }

    @Override
    public void delete(SedesConcesionarios sedesConcesionarios) {
        sedesConcesionariosRepository.delete(sedesConcesionarios);
    }

    @Override
    public SedesConcesionarios getById(Long id ) {
        return sedesConcesionariosRepository.findById(id).orElse(null);
    }

    @Override
    public List<SedesConcesionarios> getAll() {
        return (List<SedesConcesionarios>) sedesConcesionariosRepository.findAll();
    }
}
