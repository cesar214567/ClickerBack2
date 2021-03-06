package clicker.back.services.impl;

import clicker.back.entities.AutoPatrocinado;
import clicker.back.entities.AutoSemiNuevo;
import clicker.back.repositories.AutoPatrocinadoRepository;
import clicker.back.services.AutoPatrocinadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DefaultAutoPatrocinadoService implements AutoPatrocinadoService {
    @Autowired
    AutoPatrocinadoRepository autoPatrocinadoRepository;
    
    
    @Override
    public AutoPatrocinado save(AutoPatrocinado autoPatrocinado) {
        return autoPatrocinadoRepository.save(autoPatrocinado);
    }

    @Override
    public void delete(AutoPatrocinado autoPatrocinado) {
        autoPatrocinadoRepository.delete(autoPatrocinado);
    }

    @Override
    public AutoPatrocinado findById(Long id) {
        return autoPatrocinadoRepository.findById(id).orElse(null);
    }

    @Override
    public List<AutoPatrocinado> findAll() {
        return  autoPatrocinadoRepository.findAllByOrderByLevelDesc();
    }

    @Override
    public AutoPatrocinado findByAutoSemiNuevo(AutoSemiNuevo autoSemiNuevo) {
        return autoPatrocinadoRepository.findByAutoSemiNuevo(autoSemiNuevo);
    }
}
