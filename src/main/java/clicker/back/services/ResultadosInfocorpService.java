package clicker.back.services;

import clicker.back.antiguo.ResultadosInfocorp;

import java.util.List;

public interface ResultadosInfocorpService {

    ResultadosInfocorp save(ResultadosInfocorp resultadosInfocorp);

    void delete(ResultadosInfocorp resultadosInfocorp);

    ResultadosInfocorp getById(Long id );

    List<ResultadosInfocorp> getAll();

}
