package clicker.back.services.impl;

import clicker.back.entities.SolicitudRemocionAuto;
import clicker.back.repositories.AutoRemovidoRepository;
import clicker.back.services.AutoRemovidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DefaultAutoRemovidoService implements AutoRemovidoService {
    @Autowired
    AutoRemovidoRepository autoRemovidoRepository;


    @Override
    public SolicitudRemocionAuto save(SolicitudRemocionAuto solicitudRemocionAuto) {
        return autoRemovidoRepository.save(solicitudRemocionAuto);
    }

    @Override
    public void delete(SolicitudRemocionAuto solicitudRemocionAuto) {
        autoRemovidoRepository.delete(solicitudRemocionAuto);
    }

    @Override
    public SolicitudRemocionAuto getById(Long id) {
        return autoRemovidoRepository.findById(id).orElse(null);
    }


    @Override
    public List<SolicitudRemocionAuto> getAll() {
        return (List<SolicitudRemocionAuto>) autoRemovidoRepository.findAll();
    }
}
