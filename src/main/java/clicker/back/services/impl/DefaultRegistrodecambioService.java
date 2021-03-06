package clicker.back.services.impl;

import clicker.back.antiguo.Registrodecambio;
import clicker.back.repositories.RegistrodecambioRepository;
import clicker.back.services.RegistrodecambioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultRegistrodecambioService implements RegistrodecambioService {

    @Autowired
    RegistrodecambioRepository registrodecambioRepository;

    @Override
    public Registrodecambio save(Registrodecambio registrodecambio) {
        return registrodecambioRepository.save(registrodecambio);
    }

    @Override
    public void delete(Registrodecambio registrodecambio) {
        registrodecambioRepository.delete(registrodecambio);
    }

    @Override
    public Registrodecambio getById(Long id ) {
        return registrodecambioRepository.findById(id).orElse(null);
    }

    @Override
    public List<Registrodecambio> getAll() {
        return (List<Registrodecambio>) registrodecambioRepository.findAll();
    }
}
