package clicker.back.utils.repositories;

import clicker.back.utils.entities.Newsletter;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NewsletterRepository extends PagingAndSortingRepository<Newsletter,Long> {
}
