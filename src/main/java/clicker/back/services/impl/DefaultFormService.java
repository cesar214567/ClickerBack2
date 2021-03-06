package clicker.back.services.impl;

import clicker.back.entities.Form;
import clicker.back.repositories.FormRepository;
import clicker.back.services.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DefaultFormService implements FormService {
    @Autowired
    FormRepository formRepository;


    @Override
    public Form save(Form form) {
        return formRepository.save(form);
    }

    @Override
    public void delete(Form form) {
        formRepository.delete(form);
    }

    @Override
    public Form getById(Long id ) {
        return formRepository.findById(id).orElse(null);
    }

    @Override
    public List<Form> getAll() {
        return (List<Form>) formRepository.findAll();
    }
}
