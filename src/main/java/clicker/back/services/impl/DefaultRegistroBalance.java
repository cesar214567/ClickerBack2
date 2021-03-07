package clicker.back.services.impl;

import clicker.back.entities.RegistroBalance;
import clicker.back.repositories.RegistroBalanceRepository;
import clicker.back.services.RegistroBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultRegistroBalance implements RegistroBalanceService {
    @Autowired
    RegistroBalanceRepository registroBalanceRepository;
    @Override
    public RegistroBalance save(RegistroBalance registroBalance) {
        return registroBalanceRepository.save(registroBalance);
    }

    @Override
    public void delete(RegistroBalance registroBalance) {
        registroBalanceRepository.delete(registroBalance);
    }

    @Override
    public List<RegistroBalance> getAll() {
        return (List<RegistroBalance>) registroBalanceRepository.findAll();
    }
}
