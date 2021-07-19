package clicker.back.services.impl;

import clicker.back.entities.SolicitudRemocionAuto;
import clicker.back.repositories.SolicitudRemocionRepository;
import clicker.back.services.SolicitudRemocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DefaultSolicitudRemocionService implements SolicitudRemocionService {
    @Autowired
    SolicitudRemocionRepository solicitudRemocionRepository;


    @Override
    public SolicitudRemocionAuto save(SolicitudRemocionAuto solicitudRemocionAuto) {
        return solicitudRemocionRepository.save(solicitudRemocionAuto);
    }

    @Override
    public void delete(SolicitudRemocionAuto solicitudRemocionAuto) {
        solicitudRemocionRepository.delete(solicitudRemocionAuto);
    }

    @Override
    public SolicitudRemocionAuto getById(Long id) {
        return solicitudRemocionRepository.findById(id).orElse(null);
    }


    @Override
    public List<SolicitudRemocionAuto> getAll() {
        return solicitudRemocionRepository.findAllByAcceptedIsNull();
    }

    @Override
    public Boolean existSolicitudByCar(Long id) {
        return solicitudRemocionRepository.existByAuto(id);
    }
}
