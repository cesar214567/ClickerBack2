package clicker.back;

import clicker.back.antiguo.Autos;
import clicker.back.entities.*;
import clicker.back.services.AutoPatrocinadoService;
import clicker.back.services.AutosService;
import clicker.back.services.UsersService;
import clicker.back.services.UsuariosService;
import clicker.back.utils.entities.Locaciones;
import clicker.back.utils.services.LocacionesService;
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
    AutosService autosService;
    @Autowired
    LocacionesService locacionesService;
    @Autowired
    UsersService usersService;

    @Bean
    InitializingBean sendDatabase() {
        return () -> {
            Locaciones locaciones = new Locaciones("000000","Lima","Lima","Jesus Maria",true);
            locaciones = locacionesService.save(locaciones);

            Usuario usuario = new Usuario();
            usuario.setRol("REMAX");
            usuario.setCorreo("cesar.madera@utec.edu.pe");
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
            autoSemiNuevo.setLocaciones(locaciones);

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
            autoSemiNuevo1.setTipoCarroceria("CITYCAR");
            autoSemiNuevo1.setPrecioVenta((float) 11000);
            autoSemiNuevo2.setPlaca("2");
            autoSemiNuevo2.setMarca("Toyota");
            autoSemiNuevo2.setModelo("Hilux");
            autoSemiNuevo2.setTipoCarroceria("PICKUP");
            autoSemiNuevo2.setPrecioVenta((float) 17500);
            autoSemiNuevo3.setPlaca("3");
            autoSemiNuevo3.setMarca("Toyota");
            autoSemiNuevo3.setModelo("Prius");
            autoSemiNuevo3.setTipoCarroceria("CONVERTIBLE");
            autoSemiNuevo3.setPrecioVenta((float) 25000);
            autoSemiNuevo4.setPlaca("4");
            autoSemiNuevo4.setMarca("Toyota");
            autoSemiNuevo4.setModelo("Avanza");
            autoSemiNuevo4.setTipoCarroceria("COUPE");
            autoSemiNuevo4.setPrecioVenta((float) 9000);
            autoSemiNuevo5.setPlaca("5");
            autoSemiNuevo5.setMarca("Toyota");
            autoSemiNuevo5.setModelo("Eltios");
            autoSemiNuevo5.setTipoCarroceria("CITYCAR");
            autoSemiNuevo5.setPrecioVenta((float) 10000);
            autoSemiNuevo6.setPlaca("6");
            autoSemiNuevo6.setMarca("Hyundai");
            autoSemiNuevo6.setModelo("Tucson");
            autoSemiNuevo6.setTipoCarroceria("SUV");
            autoSemiNuevo6.setPrecioVenta((float) 16000);
            autoSemiNuevo7.setPlaca("7");
            autoSemiNuevo7.setMarca("Hyundai");
            autoSemiNuevo7.setModelo("Venue");
            autoSemiNuevo7.setTipoCarroceria("CITYCAR");
            autoSemiNuevo7.setPrecioVenta((float) 12434);
            autoSemiNuevo8.setPlaca("8");
            autoSemiNuevo8.setMarca("Hyundai");
            autoSemiNuevo8.setModelo("Verna");
            autoSemiNuevo8.setTipoCarroceria("SEDAN");
            autoSemiNuevo8.setPrecioVenta((float) 15710);
            autoSemiNuevo9.setPlaca("9");
            autoSemiNuevo9.setMarca("Hyundai");
            autoSemiNuevo9.setModelo("Accent");
            autoSemiNuevo9.setTipoCarroceria("SEDAN");
            autoSemiNuevo9.setPrecioVenta((float) 17657);
            autoSemiNuevo10.setPlaca("10");
            autoSemiNuevo10.setMarca("Hyundai");
            autoSemiNuevo10.setModelo("Elantra");
            autoSemiNuevo10.setTipoCarroceria("COUPE");
            autoSemiNuevo10.setPrecioVenta((float) 17899);
            autoSemiNuevo11.setPlaca("11");
            autoSemiNuevo11.setMarca("Hyundai");
            autoSemiNuevo11.setModelo("Ioniq");
            autoSemiNuevo11.setTipoCarroceria("CITYCAR");
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

            Autos autos = new Autos();
            autos.setAnofabricacion(new Date());
            autos.setCiudadesDisponibles(Arrays.asList("Lima","Piura","Trujillo"));
            autos.setCodVersion(1);
            autos.setDocumentacion("prueba1");
            autos.setMoneda("dólares");
            autos.setPrecio((long) 12000);
            autos.setVersion("1");
            autos.setUsoAuto(Arrays.asList("particular","remisse"));
            
            Autos autos1 = (Autos) autos.clone();
            Autos autos2 = (Autos) autos.clone();
            Autos autos3 = (Autos) autos.clone();
            Autos autos4 = (Autos) autos.clone();
            Autos autos5 = (Autos) autos.clone();
            Autos autos6 = (Autos) autos.clone();
            Autos autos7 = (Autos) autos.clone();
            Autos autos8 = (Autos) autos.clone();
            Autos autos9 = (Autos) autos.clone();
            Autos autos10 = (Autos) autos.clone();
            Autos autos11 = (Autos) autos.clone();

            autos.setMarca("Volkswagen");
            autos.setModelo("Atlas");
            autos.setTipoCarroceria("SUV");
            autos1.setMarca("Volkswagen");
            autos1.setModelo("Jetta");
            autos1.setTipoCarroceria("SEDAN");
            autos2.setMarca("Volkswagen");
            autos2.setModelo("Passat");
            autos2.setTipoCarroceria("SEDAN");
            autos3.setMarca("Volkswagen");
            autos3.setModelo("Tiguan");
            autos3.setTipoCarroceria("SUV");
            autos4.setMarca("Nissan");
            autos4.setModelo("Altima");
            autos4.setTipoCarroceria("COUPE");
            autos5.setMarca("Nissan");
            autos5.setModelo("Armada");
            autos5.setTipoCarroceria("SUV");
            autos6.setMarca("Nissan");
            autos6.setModelo("Versa");
            autos6.setTipoCarroceria("CITYCAR");
            autos7.setMarca("Hyundai");
            autos7.setModelo("Elantra");
            autos7.setTipoCarroceria("COUPE");
            autos8.setMarca("Hyundai");
            autos8.setModelo("Verna");
            autos8.setTipoCarroceria("SEDAN");
            autos9.setMarca("Tesla");
            autos9.setModelo("Model Y");
            autos9.setTipoCarroceria("SEDAN");
            autos10.setMarca("Tesla");
            autos10.setModelo("Model S");
            autos10.setTipoCarroceria("CONVERTIBLE");
            autos11.setMarca("Tesla");
            autos11.setModelo("Model X");
            autos11.setTipoCarroceria("COUPE");

            autos.setFoto("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/elantra.jpeg");
            autos3.setFoto("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/elantra.jpeg");
            autos6.setFoto("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/elantra.jpeg");
            autos9.setFoto("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/elantra.jpeg");

            autos1.setFoto("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/2015_Hyundai_Genesis_3.8L_front.jpg");
            autos4.setFoto("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/2015_Hyundai_Genesis_3.8L_front.jpg");
            autos7.setFoto("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/2015_Hyundai_Genesis_3.8L_front.jpg");
            autos10.setFoto("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/2015_Hyundai_Genesis_3.8L_front.jpg");

            autos2.setFoto("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/40593-2021elantra-1584497298.jpg");
            autos5.setFoto("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/40593-2021elantra-1584497298.jpg");
            autos8.setFoto("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/40593-2021elantra-1584497298.jpg");
            autos11.setFoto("https://data-clicker-pruebas.nyc3.digitaloceanspaces.com/clicker-prueba-imagenes/40593-2021elantra-1584497298.jpg");

            autosService.save(autos);
            autosService.save(autos1);
            autosService.save(autos2);
            autosService.save(autos3);
            autosService.save(autos4);
            autosService.save(autos5);
            autosService.save(autos6);
            autosService.save(autos7);
            autosService.save(autos8);
            autosService.save(autos9);
            autosService.save(autos10);
            autosService.save(autos11);

            Users admin = new Users();
            admin.setEmail("luis.jauregui@utec.edu.pe");
            admin.setPassword("uwu");
            admin.setName("luis");
            admin.setPermitido(true);
            admin.setRol("ADMIN");
            usersService.save(admin);
        };
    }
}


