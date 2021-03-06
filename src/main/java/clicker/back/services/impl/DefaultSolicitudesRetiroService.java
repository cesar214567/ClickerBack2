package clicker.back.services.impl;

import clicker.back.entities.SolicitudesRetiro;
import clicker.back.repositories.SolicitudesRetiroRepository;
import clicker.back.services.SolicitudesRetiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DefaultSolicitudesRetiroService implements SolicitudesRetiroService {
    @Autowired
    SolicitudesRetiroRepository solicitudesRetiroRepository;

    @Override
    public SolicitudesRetiro save(SolicitudesRetiro solicitudesRetiro) {
        return solicitudesRetiroRepository.save(solicitudesRetiro);
    }

    @Override
    public void delete(SolicitudesRetiro solicitudesRetiro) {
        solicitudesRetiroRepository.delete(solicitudesRetiro);
    }

    @Override
    public SolicitudesRetiro getById(Long id) {
        return solicitudesRetiroRepository.findById(id).orElse(null);
    }

    @Override
    public List<SolicitudesRetiro> getAll() {
        return (List<SolicitudesRetiro>) solicitudesRetiroRepository.findAll();
    }
}
