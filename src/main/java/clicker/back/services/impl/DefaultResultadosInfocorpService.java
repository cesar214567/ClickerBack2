package clicker.back.services.impl;

import clicker.back.antiguo.ResultadosInfocorp;
import clicker.back.repositories.ResultadosInfocorpRepository;
import clicker.back.services.ResultadosInfocorpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultResultadosInfocorpService implements ResultadosInfocorpService {
    @Autowired
    ResultadosInfocorpRepository resultadosInfocorpRepository;


    @Override
    public ResultadosInfocorp save(ResultadosInfocorp resultadosInfocorp) {
        return resultadosInfocorpRepository.save(resultadosInfocorp);
    }

    @Override
    public void delete(ResultadosInfocorp resultadosInfocorp) {
        resultadosInfocorpRepository.delete(resultadosInfocorp);
    }

    @Override
    public ResultadosInfocorp getById(Long id ) {
        return resultadosInfocorpRepository.findById(id).orElse(null);
    }

    @Override
    public List<ResultadosInfocorp> getAll() {
        return (List<ResultadosInfocorp>) resultadosInfocorpRepository.findAll();
    }
}
