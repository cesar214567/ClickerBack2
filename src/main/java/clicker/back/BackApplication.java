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
            usuario.setRol("REMAX");
            usuario.setCorreo("cesar.madera@utec.edu.com");
            usuario.setPassword("uwu");
            usuario.setValidated(true);
            usuario.setEnabled(true);
            usuario.setCantidadCarrosAno(3);
            usuario.setBalance((float) 1000);
            usuario.setForm(new Form());
            usuario.getForm().setEstado(true);
            usuario.getForm().setUsuario(usuario);

            FotosAutoSemiNuevo fotosAutoSemiNuevo = new FotosAutoSemiNuevo();
            fotosAutoSemiNuevo.setFoto("");

            AutoSemiNuevo autoSemiNuevo = new AutoSemiNuevo();
            autoSemiNuevo.setUsuario(usuario);
            autoSemiNuevo.setSerie("ASDSAD");
            autoSemiNuevo.setCorreoDueno("luis.jauregui@utec.edu.pe");
            autoSemiNuevo.setNombreDueno("Luis");
            autoSemiNuevo.setTelefonoDueno("997854810");
            autoSemiNuevo.setMarca("KIA");
            autoSemiNuevo.setModelo("RIO");
            autoSemiNuevo.setAnoFabricacion(2020);
            autoSemiNuevo.setTipoCambios("Automático");
            autoSemiNuevo.setTipoCombustible("Diesel");
            autoSemiNuevo.setTipoCarroceria("PICKUP");
            autoSemiNuevo.setCilindrada(1);
            autoSemiNuevo.setKilometraje((long) 20000);
            autoSemiNuevo.setNumeroPuertas(4);
            autoSemiNuevo.setTipoTraccion("Delantera");
            autoSemiNuevo.setColor("blanco");
            autoSemiNuevo.setNumeroCilindros(65);
            autoSemiNuevo.setPrecioVenta((float) 13000);
            autoSemiNuevo.setValidado(true);
            autoSemiNuevo.setComprado(false);
            autoSemiNuevo.setEnabled(true);
            autoSemiNuevo.setFechaPublicacion(new Date());
            autoSemiNuevo.setAccesorios(Arrays.asList("ventilador", "radio", "ambientador"));
            autoSemiNuevo.setLocacion("Lima");

            AutoSemiNuevo autoSemiNuevo1 = (AutoSemiNuevo) autoSemiNuevo.clone();
            AutoSemiNuevo autoSemiNuevo2 = (AutoSemiNuevo) autoSemiNuevo.clone();
            AutoSemiNuevo autoSemiNuevo3 = (AutoSemiNuevo) autoSemiNuevo.clone();
            AutoSemiNuevo autoSemiNuevo4 = (AutoSemiNuevo) autoSemiNuevo.clone();
            AutoSemiNuevo autoSemiNuevo5 = (AutoSemiNuevo) autoSemiNuevo.clone();
            AutoSemiNuevo autoSemiNuevo6 = (AutoSemiNuevo) autoSemiNuevo.clone();
            AutoSemiNuevo autoSemiNuevo7 = (AutoSemiNuevo) autoSemiNuevo.clone();
            AutoSemiNuevo autoSemiNuevo8 = (AutoSemiNuevo) autoSemiNuevo.clone();
            AutoSemiNuevo autoSemiNuevo9 = (AutoSemiNuevo) autoSemiNuevo.clone();
            AutoSemiNuevo autoSemiNuevo10 = (AutoSemiNuevo) autoSemiNuevo.clone();
            AutoSemiNuevo autoSemiNuevo11 = (AutoSemiNuevo) autoSemiNuevo.clone();

            autoSemiNuevo.setPlaca("ABC123");
            autoSemiNuevo.setMarca("Toyota");
            autoSemiNuevo.setModelo("Corolla");
            autoSemiNuevo.setTipoCarroceria("SUV");
            autoSemiNuevo.setPrecioVenta((float) 12000);
            autoSemiNuevo1.setPlaca("1");
            autoSemiNuevo1.setMarca("Toyota");
            autoSemiNuevo1.setModelo("Yaris");
            autoSemiNuevo1.setTipoCarroceria("Pick up");
            autoSemiNuevo1.setPrecioVenta((float) 11000);
            autoSemiNuevo2.setPlaca("2");
            autoSemiNuevo2.setMarca("Toyota");
            autoSemiNuevo2.setModelo("Hilux");
            autoSemiNuevo2.setTipoCarroceria("Hatchback");
            autoSemiNuevo2.setPrecioVenta((float) 17500);
            autoSemiNuevo3.setPlaca("3");
            autoSemiNuevo3.setMarca("Toyota");
            autoSemiNuevo3.setModelo("Prius");
            autoSemiNuevo3.setTipoCarroceria("Camión");
            autoSemiNuevo3.setPrecioVenta((float) 25000);
            autoSemiNuevo4.setPlaca("4");
            autoSemiNuevo4.setMarca("Toyota");
            autoSemiNuevo4.setModelo("Avanza");
            autoSemiNuevo4.setTipoCarroceria("VAN");
            autoSemiNuevo4.setPrecioVenta((float) 9000);
            autoSemiNuevo5.setPlaca("5");
            autoSemiNuevo5.setMarca("Toyota");
            autoSemiNuevo5.setModelo("Eltios");
            autoSemiNuevo5.setTipoCarroceria("Coupé");
            autoSemiNuevo5.setPrecioVenta((float) 10000);
            autoSemiNuevo6.setPlaca("6");
            autoSemiNuevo6.setMarca("Hyundai");
            autoSemiNuevo6.setModelo("Tucson");
            autoSemiNuevo6.setTipoCarroceria("SUV");
            autoSemiNuevo6.setPrecioVenta((float) 16000);
            autoSemiNuevo7.setPlaca("7");
            autoSemiNuevo7.setMarca("Hyundai");
            autoSemiNuevo7.setModelo("Venue");
            autoSemiNuevo7.setTipoCarroceria("Van");
            autoSemiNuevo7.setPrecioVenta((float) 12434);
            autoSemiNuevo8.setPlaca("8");
            autoSemiNuevo8.setMarca("Hyundai");
            autoSemiNuevo8.setModelo("Verna");
            autoSemiNuevo8.setTipoCarroceria("Panel");
            autoSemiNuevo8.setPrecioVenta((float) 15710);
            autoSemiNuevo9.setPlaca("9");
            autoSemiNuevo9.setMarca("Hyundai");
            autoSemiNuevo9.setModelo("Accent");
            autoSemiNuevo9.setTipoCarroceria("Sedán");
            autoSemiNuevo9.setPrecioVenta((float) 17657);
            autoSemiNuevo10.setPlaca("10");
            autoSemiNuevo10.setMarca("Hyundai");
            autoSemiNuevo10.setModelo("Elantra");
            autoSemiNuevo10.setTipoCarroceria("Coupé");
            autoSemiNuevo10.setPrecioVenta((float) 17899);
            autoSemiNuevo11.setPlaca("11");
            autoSemiNuevo11.setMarca("Hyundai");
            autoSemiNuevo11.setModelo("Ioniq");
            autoSemiNuevo11.setTipoCarroceria("Camión");
            autoSemiNuevo11.setPrecioVenta((float) 11300);





            autoSemiNuevo.setFotoPrincipal("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/elantra.jpeg");
            autoSemiNuevo3.setFotoPrincipal("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/elantra.jpeg");
            autoSemiNuevo6.setFotoPrincipal("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/elantra.jpeg");
            autoSemiNuevo9.setFotoPrincipal("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/elantra.jpeg");

            autoSemiNuevo1.setFotoPrincipal("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/2015_Hyundai_Genesis_3.8L_front.jpg");
            autoSemiNuevo4.setFotoPrincipal("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/2015_Hyundai_Genesis_3.8L_front.jpg");
            autoSemiNuevo7.setFotoPrincipal("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/2015_Hyundai_Genesis_3.8L_front.jpg");
            autoSemiNuevo10.setFotoPrincipal("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/2015_Hyundai_Genesis_3.8L_front.jpg");

            autoSemiNuevo2.setFotoPrincipal("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/40593-2021elantra-1584497298.jpg");
            autoSemiNuevo5.setFotoPrincipal("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/40593-2021elantra-1584497298.jpg");
            autoSemiNuevo8.setFotoPrincipal("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/40593-2021elantra-1584497298.jpg");
            autoSemiNuevo11.setFotoPrincipal("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/40593-2021elantra-1584497298.jpg");


            usuario.setCarrosPosteados(new ArrayList<>());
            usuario.getCarrosPosteados().add(autoSemiNuevo);
            usuario.getCarrosPosteados().add(autoSemiNuevo1);
            usuario.getCarrosPosteados().add(autoSemiNuevo2);
            usuario.getCarrosPosteados().add(autoSemiNuevo3);
            usuario.getCarrosPosteados().add(autoSemiNuevo4);
            usuario.getCarrosPosteados().add(autoSemiNuevo5);
            usuario.getCarrosPosteados().add(autoSemiNuevo6);
            usuario.getCarrosPosteados().add(autoSemiNuevo7);
            usuario.getCarrosPosteados().add(autoSemiNuevo8);
            usuario.getCarrosPosteados().add(autoSemiNuevo9);
            usuario.getCarrosPosteados().add(autoSemiNuevo10);
            usuario.getCarrosPosteados().add(autoSemiNuevo11);
            usuariosService.save(usuario);

            AutoPatrocinado autoPatrocinado = new AutoPatrocinado();
            autoPatrocinado.setLevel(11);
            autoPatrocinado.setAutoSemiNuevo(new AutoSemiNuevo());
            autoPatrocinado.getAutoSemiNuevo().setId((long) 1);
            autoPatrocinadoService.save(autoPatrocinado);

            autoPatrocinado.setId(null);
            autoPatrocinado.getAutoSemiNuevo().setId((long) 5);
            autoPatrocinado.setLevel(5);
            autoPatrocinadoService.save(autoPatrocinado);

            autoPatrocinado.setId(null);
            autoPatrocinado.getAutoSemiNuevo().setId((long) 9);
            autoPatrocinado.setLevel(2);
            autoPatrocinadoService.save(autoPatrocinado);

            autoPatrocinado.setId(null);
            autoPatrocinado.getAutoSemiNuevo().setId((long) 11);
            autoPatrocinado.setLevel(20);
            autoPatrocinadoService.save(autoPatrocinado);
            
            Users admin = new Users();
            admin.setEmail("luis.jauregui@utec.edu.pe");
            admin.setPassword("uwu");
            admin.setName("luis");
            admin.setPermitido(true);
            admin.setRol("ADMIN");
            usersService.save(admin);

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
            usuario2.setBalance((float) 1000);

            usuariosService.save(usuario2);
        };
    }
}


