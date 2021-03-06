package clicker.back.utils.repositories;

import clicker.back.utils.entities.Testimonios;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TestimoniosRepository extends PagingAndSortingRepository<Testimonios,Long> {
}
