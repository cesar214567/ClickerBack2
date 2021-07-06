package clicker.back;

import clicker.back.entities.*;
import clicker.back.services.AutoPatrocinadoService;
import clicker.back.services.UsersService;
import clicker.back.services.UsuariosService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class BackApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return  builder.sources(BackApplication.class);
    }

    @Autowired
    UsuariosService usuariosService;
    @Autowired
    AutoPatrocinadoService autoPatrocinadoService;

    @Autowired
    UsersService usersService;

    @Bean
    InitializingBean sendDatabase() {
        return () -> {


            Usuario usuario = new Usuario();
            usuario.setRol("PARTICULAR");
            usuario.setCorreo("santiago.salas@clicker.pe");
            usuario.setPassword("clicker");
            usuario.setNombre("Santiago José Salas Salas");
            usuario.setTipoDocumento("DNI");
            usuario.setNumDocumento("71838102");
            usuario.setNumTelefono((long) 987654321);
            usuario.setValidated(true);
            usuario.setEnabled(true);
            usuario.setCantidadCarrosAno(100000);
            usuario.setBalance((float) 0);
            usuariosService.save(usuario);


            Usuario usuario1 = new Usuario();
            usuario1.setRol("REMAX");
            usuario1.setCorreo("cesar.madera@utec.edu.com");
            usuario1.setPassword("uwu");
            usuario1.setValidated(true);
            usuario1.setEnabled(true);
            usuario1.setCantidadCarrosAno(3);
            usuario1.setBalance((float) 0);
            usuario1.setForm(new Form());
            usuario1.getForm().setEstado(true);
            usuario1.getForm().setUsuario(usuario);
            usuariosService.save(usuario1);

            Users admin = new Users();
            admin.setEmail("luis.jauregui@utec.edu.pe");
            admin.setPassword("uwu");
            admin.setName("luis");
            admin.setPermitido(true);
            admin.setRol("ADMIN");
            usersService.save(admin);

            Users admin2 = new Users();
            admin2.setEmail("jonathan.prieto@utec.edu.pe");
            admin2.setPassword("clicker");
            admin2.setName("jonathan");
            admin2.setPermitido(true);
            admin2.setRol("ADMIN");
            usersService.save(admin2);

            Usuario usuario2 = new Usuario();
            usuario2.setRol("PARTICULAR");
            usuario2.setCorreo("gabriel.spranger@utec.edu.pe");
            usuario2.setNombre("Gabriel Spranger Rojas");
            usuario2.setNumDocumento("72985463");
            usuario2.setNumTelefono((long) 987654321);
            usuario2.setPassword("uwu");
            usuario2.setValidated(true);
            usuario2.setEnabled(true);
            usuario2.setCantidadCarrosAno(3);
            usuario2.setBalance((float) 0);

            usuariosService.save(usuario2);


        };
    }
}


