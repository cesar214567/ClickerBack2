package clicker.back.services.impl;

import clicker.back.entities.AutoSemiNuevo;
import clicker.back.entities.Denuncia;
import clicker.back.entities.Usuario;
import clicker.back.repositories.DenunciaRepository;
import clicker.back.services.DenunciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultDenunciaService implements DenunciaService {
    @Autowired
    DenunciaRepository denunciaRepository;


    @Override
    public Denuncia save(Denuncia denuncia) {
        return denunciaRepository.save(denuncia);
    }

    @Override
    public void delete(Denuncia denuncia) {
        denunciaRepository.delete(denuncia);
    }

    @Override
    public Denuncia getById(Long id) {
        return denunciaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Denuncia> getAll() {
        return (List<Denuncia>) denunciaRepository.findAll();
    }

    @Override
    public List<Long> getIdsAutosDenunciados() {
        return denunciaRepository.getIdsFromAutosDenunciados();
    }

    @Override
    public Denuncia getByAutoAndUsuario(AutoSemiNuevo autoSemiNuevo, Usuario usuario) {
        return denunciaRepository.getByAutoSemiNuevoAndUsuario(autoSemiNuevo,usuario);
    }
}
