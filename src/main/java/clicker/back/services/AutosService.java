package clicker.back.services;

import clicker.back.antiguo.Autos;

import javax.persistence.Tuple;
import java.util.List;

public interface AutosService {

    Autos save(Autos autos);

    List<Autos> getAll();

    List<Tuple> getFiltros();

    Long countAll();

    List<String> findAllMarcas();
}
