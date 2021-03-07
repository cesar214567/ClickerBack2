package clicker.back.services;


import clicker.back.entities.RegistroBalance;

import java.util.List;

public interface RegistroBalanceService {

    RegistroBalance save(RegistroBalance registroBalance);

    void delete(RegistroBalance registroBalance);

    List<RegistroBalance> getAll();

}
