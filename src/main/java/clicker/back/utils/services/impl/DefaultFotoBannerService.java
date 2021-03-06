package clicker.back.utils.services.impl;

import clicker.back.utils.entities.FotoBanner;
import clicker.back.utils.repositories.FotoBannerRepository;
import clicker.back.utils.services.FotoBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultFotoBannerService implements FotoBannerService {
    @Autowired
    FotoBannerRepository fotoBannerRepository;

    @Override
    public FotoBanner save(FotoBanner fotoBanner) {
        return fotoBannerRepository.save(fotoBanner);
    }

    @Override
    public void delete(FotoBanner fotoBanner) {
        fotoBannerRepository.delete(fotoBanner);
    }

    @Override
    public List<FotoBanner> findAll() {
        return (List<FotoBanner>) fotoBannerRepository.findAll();
    }
}
