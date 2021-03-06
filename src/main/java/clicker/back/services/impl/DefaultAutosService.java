package clicker.back.services.impl;

import clicker.back.antiguo.Autos;
import clicker.back.repositories.AutosRepository;
import clicker.back.services.AutosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.List;

@Service
public class DefaultAutosService implements AutosService {
    @Autowired
    AutosRepository autosRepository;


    @Override
    public Autos save(Autos autos) {
        return autosRepository.save(autos);
    }

    @Override
    public List<Autos> getAll() {
        return (List<Autos>) autosRepository.findAll();
    }

    @Override
    public List<Tuple> getFiltros() {
        return autosRepository.filters();
    }

    @Override
    public Long countAll() {
        return autosRepository.count();
    }

    @Override
    public List<String> findAllMarcas() {
        return autosRepository.findDistinctMarca();
    }

}
