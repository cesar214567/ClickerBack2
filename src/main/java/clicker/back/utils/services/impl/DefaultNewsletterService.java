package clicker.back.utils.services.impl;

import clicker.back.utils.entities.Newsletter;
import clicker.back.utils.repositories.NewsletterRepository;
import clicker.back.utils.services.NewsletterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultNewsletterService implements NewsletterService {
    @Autowired
    NewsletterRepository newsletterRepository;

    @Override
    public Newsletter save(Newsletter newsletter) {
        return newsletterRepository.save(newsletter);
    }

    @Override
    public void delete(Newsletter newsletter) {
        newsletterRepository.delete(newsletter);
    }

    @Override
    public Newsletter findById(Long id) {
        return newsletterRepository.findById(id).orElse(null);
    }

    @Override
    public List<Newsletter> findAll() {
        return (List<Newsletter>) newsletterRepository.findAll();
    }
}
