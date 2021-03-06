package clicker.back.utils.services;

import clicker.back.utils.entities.Testimonios;

public interface TestimoniosService {

    Testimonios save(Testimonios testimonios);

    void delete(Testimonios testimonios);

    Testimonios findById(Long id);

    Iterable<Testimonios> findAll();
}
