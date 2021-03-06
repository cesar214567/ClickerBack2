package clicker.back.utils.services;

import clicker.back.utils.entities.FotoBanner;

import java.util.List;

public interface FotoBannerService {

    FotoBanner save(FotoBanner fotoBanner);

    void delete(FotoBanner fotoBanner);

    List<FotoBanner> findAll();
}
