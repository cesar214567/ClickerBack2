package clicker.back.repositories;

import clicker.back.entities.Form;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FormRepository extends PagingAndSortingRepository<Form,Long> {
}
