package clicker.back.services;

import clicker.back.entities.Form;

import java.util.List;

public interface FormService {

    Form save(Form form);

    void delete(Form form);

    Form getById(Long id);

    List<Form> getAll();
}
