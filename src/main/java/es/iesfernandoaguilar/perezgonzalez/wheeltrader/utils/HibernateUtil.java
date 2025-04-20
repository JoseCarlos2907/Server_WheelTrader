package es.iesfernandoaguilar.perezgonzalez.wheeltrader.utils;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration config = new Configuration();

            config.addAnnotatedClass(Anuncio.class);
            config.addAnnotatedClass(Caracteristica.class);
            config.addAnnotatedClass(Cuestionario.class);
            config.addAnnotatedClass(Imagen.class);
            config.addAnnotatedClass(Notificacion.class);
            config.addAnnotatedClass(Pago.class);
            config.addAnnotatedClass(Reporte.class);
            config.addAnnotatedClass(Reunion.class);
            config.addAnnotatedClass(Revision.class);
            config.addAnnotatedClass(TipoVehiculo.class);
            config.addAnnotatedClass(TipoVehiculo_Caracteristica.class);
            config.addAnnotatedClass(Usuario.class);
            config.addAnnotatedClass(Valoracion.class);
            config.addAnnotatedClass(ValorCaracteristica.class);
            config.addAnnotatedClass(Venta.class);

            // Crear el ServiceRegistry
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();

            sessionFactory = config.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}
