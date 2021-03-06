package clicker.back.services.impl;

import clicker.back.antiguo.Solicitudes;
import clicker.back.repositories.SolicitudesRepository;
import clicker.back.services.SolicitudesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultSolicitudesService implements SolicitudesService {

    @Autowired
    SolicitudesRepository solicitudesRepository;


    @Override
    public Solicitudes save(Solicitudes solicitudes) {
        return solicitudesRepository.save(solicitudes);
    }

    @Override
    public void delete(Solicitudes solicitudes) {
        solicitudesRepository.delete(solicitudes);
    }

    @Override
    public Solicitudes getById(Long id) {
        return solicitudesRepository.findById(id).orElse(null);
    }

    @Override
    public List<Solicitudes> getAll() {
        return (List<Solicitudes>) solicitudesRepository.findAll();
    }
}
