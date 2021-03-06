package clicker.back.utils.services.impl;

import clicker.back.utils.entities.Locaciones;
import clicker.back.utils.services.LocacionesService;
import clicker.back.utils.repositories.LocacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DefaultLocacionesService implements LocacionesService {
    @Autowired
    LocacionesRepository locacionesRepository;

    @Override
    public Locaciones save(Locaciones locaciones) {
        return locacionesRepository.save(locaciones);
    }

    @Override
    public void delete(Locaciones locaciones) {
        locacionesRepository.delete(locaciones);
    }

    @Override
    public Locaciones findById(String id) {
        return locacionesRepository.findById(id).orElse(null);
    }

    @Override
    public List<Locaciones> findAll() {
        return (List<Locaciones>) locacionesRepository.findAll();
    }
}
