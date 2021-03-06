package clicker.back.services.impl;

import clicker.back.antiguo.Concesionarios;
import clicker.back.repositories.ConcesionariosRepository;
import clicker.back.services.ConcesionariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultConcesionariosService implements ConcesionariosService {
    @Autowired
    ConcesionariosRepository concesionariosRepository;


    @Override
    public Concesionarios save(Concesionarios concesionarios) {
        return concesionariosRepository.save(concesionarios);
    }

    @Override
    public void delete(Concesionarios concesionarios) {
        concesionariosRepository.delete(concesionarios);
    }

    @Override
    public Concesionarios getById(String id) {
        return concesionariosRepository.findById(id).orElse(null);
    }


    @Override
    public List<Concesionarios> getAll() {
        return (List<Concesionarios>) concesionariosRepository.findAll();
    }
}
