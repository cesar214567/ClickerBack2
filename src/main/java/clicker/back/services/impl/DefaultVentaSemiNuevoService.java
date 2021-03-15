package clicker.back.services.impl;

import clicker.back.entities.Usuario;
import clicker.back.entities.VentaSemiNuevo;
import clicker.back.repositories.VentaSemiNuevoRepository;
import clicker.back.services.VentaSemiNuevoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultVentaSemiNuevoService implements VentaSemiNuevoService {
    @Autowired
    VentaSemiNuevoRepository ventaSemiNuevoRepository;
    
    @Override
    public VentaSemiNuevo save(VentaSemiNuevo ventaSemiNuevo) {
        return ventaSemiNuevoRepository.save(ventaSemiNuevo);
    }

    @Override
    public void delete(VentaSemiNuevo ventaSemiNuevo) {
        ventaSemiNuevoRepository.delete(ventaSemiNuevo);
    }

    @Override
    public VentaSemiNuevo getById(Long id ) {
        return ventaSemiNuevoRepository.findById(id).orElse(null);
    }

    @Override
    public List<VentaSemiNuevo> getAll() {
        return (List<VentaSemiNuevo>) ventaSemiNuevoRepository.findAll();
    }

    @Override
    public List<VentaSemiNuevo> getVentasByUsuario(String usuario) {
        return ventaSemiNuevoRepository.findAllByVendedor(usuario);
    }

    @Override
    public List<VentaSemiNuevo> getByIdAuto(List<Long> id) {
        return ventaSemiNuevoRepository.findAllByAuto(id);
    }


}
