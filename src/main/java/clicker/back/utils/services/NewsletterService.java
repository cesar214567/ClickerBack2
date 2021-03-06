package clicker.back.utils.services;

import clicker.back.utils.entities.Locaciones;
import clicker.back.utils.entities.Newsletter;

import java.util.List;

public interface NewsletterService {
    Newsletter save(Newsletter newsletter);

    void delete(Newsletter newsletter);

    Newsletter findById(Long id);

    List<Newsletter> findAll();

}
