package clicker.back.services.impl;

import clicker.back.entities.Comprador;
import clicker.back.repositories.CompradorRepository;
import clicker.back.services.CompradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DefaultCompradorService implements CompradorService {

    @Autowired
    CompradorRepository compradorRepository;

    @Override
    public Comprador save(Comprador comprador) {
        return compradorRepository.save(comprador);
    }

    @Override
    public void delete(Comprador comprador) {
        compradorRepository.delete(comprador);
    }

    @Override
    public Comprador getById(String id) {
        return compradorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Comprador> getAll() {
        return (List<Comprador>) compradorRepository.findAll();
    }
}
