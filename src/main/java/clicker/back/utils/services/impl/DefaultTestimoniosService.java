package clicker.back.utils.services.impl;

import clicker.back.utils.entities.Testimonios;
import clicker.back.utils.repositories.TestimoniosRepository;
import clicker.back.utils.services.TestimoniosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultTestimoniosService  implements TestimoniosService {

    @Autowired
    TestimoniosRepository testimoniosRepository;

    @Override
    public Testimonios save(Testimonios testimonios) {
        return testimoniosRepository.save(testimonios);
    }

    @Override
    public void delete(Testimonios testimonios) {
        testimoniosRepository.delete(testimonios);
    }

    @Override
    public Testimonios findById(Long id) {
        return testimoniosRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<Testimonios> findAll() {
        return testimoniosRepository.findAll();
    }
}
