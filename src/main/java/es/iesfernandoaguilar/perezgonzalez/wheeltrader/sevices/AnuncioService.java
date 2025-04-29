package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Anuncio;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Caracteristica;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.TipoVehiculo;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.ValorCaracteristica;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.AnuncioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnuncioService {

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private EntityManager entityManager;

    public void save(Anuncio anuncio) {
        this.anuncioRepository.save(anuncio);
    }

    public Anuncio findById(long id) {
        return this.anuncioRepository.findById(id);
    }

    @Transactional
    public Anuncio findByIdWithUsuariosGuardan(int idAnuncio) {
        return this.anuncioRepository.findByIdWithUsuariosGuardan(idAnuncio);
    }

    @Transactional
    public List<Anuncio> findAll(List<String> tiposVehiculo, int anioMinimo, int anioMaximo, String marca, String modelo, String provincia, String ciudad, double precioMinimo, double precioMaximo, Pageable pageable) {
        return this.anuncioRepository.findAll(
                marca,
                modelo,
                anioMinimo,
                anioMaximo,
                precioMinimo,
                precioMaximo,
                provincia,
                ciudad,
                tiposVehiculo,
                pageable
        );
    }

    @Transactional
    public List<Anuncio> findCoches(String marca, String modelo, int cantMarchas, int kmMinimo, int kmMaximo, int nPuertas, String provincia, String ciudad, int cvMinimo, int cvMaximo, int anioMinimo, int anioMaximo, String tipoCombustible, String transmision, Pageable pageable){
        return this.anuncioRepository.findCoches(
                marca,
                modelo,
                cantMarchas,
                kmMinimo,
                kmMaximo,
                nPuertas,
                provincia,
                ciudad,
                cvMinimo,
                cvMaximo,
                anioMinimo,
                anioMaximo,
                tipoCombustible,
                transmision,
                pageable
        );
    }

    public List<Anuncio> findAunciosByTipo(
        String tipoVehiculo,
        String marca,
        String modelo,
        Integer cantMarchas,
        Integer kmMinimo,
        Integer kmMaximo,
        Integer nPuertas,
        String provincia,
        String ciudad,
        Integer cvMinimo,
        Integer cvMaximo,
        Integer anioMinimo,
        Integer anioMaximo,
        String tipoCombustible,
        String transmision,
        String traccion,
        Pageable pageable
    ) {

        // Preparo el Builder para crear la Query y asignar la clase del objeto que voy a recuperar
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Anuncio> query = builder.createQuery(Anuncio.class);
        Root<Anuncio> anuncio = query.from(Anuncio.class);

        // Hago join con TipoVehiculo
        Join<Anuncio, TipoVehiculo> tipo = anuncio.join("tipoVehiculo");

        // Preparo la lista para almacenar los predicados
        // (son condiciones de los distintos campos que especificaré más adelante)
        List<Predicate> predicates = new ArrayList<>();

        // Filtro por tipo de vehículo
        predicates.add(builder.equal(tipo.get("tipo"), tipoVehiculo));

        // Filtro de provincia y ciudad
        if (provincia != null) {
            // Añado la condición de que la provincia del anuncio en minúsculas sea igual al valor indicado en minúsculas
            predicates.add(builder.equal(builder.lower(anuncio.get("provincia")), provincia.toLowerCase()));
        }
        if (ciudad != null) {
            predicates.add(builder.equal(builder.lower(anuncio.get("ciudad")), ciudad.toLowerCase()));
        }

        // Añado los filtros normales (se aplican solo si el parámetro no es nulo)
        aniadirFiltroVC(builder, anuncio, predicates, "Anio_" + tipoVehiculo, anioMinimo, anioMaximo);
        aniadirFiltroVC(builder, anuncio, predicates, "KM_" + tipoVehiculo, kmMinimo, kmMaximo);
        if("Moto".equals(tipoVehiculo)){
            aniadirFiltroVC(builder, anuncio, predicates, "Cilindrada_" + tipoVehiculo, cvMinimo, cvMaximo);
        }else{
            aniadirFiltroVC(builder, anuncio, predicates, "CV_" + tipoVehiculo, cvMinimo, cvMaximo);
        }


        // Añado los filtros exactos de Marchas y Puertas (se aplican solo si el parámetro no es nulo)
        aniadirFiltroExactoVC(builder, anuncio, predicates, "Marchas_" + tipoVehiculo, cantMarchas);
        aniadirFiltroExactoVC(builder, anuncio, predicates, "Puertas_" + tipoVehiculo, nPuertas);

        // Añado los filtros que tengan un valor de cadena igual al indicado (se aplican solo si el parámetro no es nulo)
        aniadirFiltroCadenaVC(builder, anuncio, predicates, "Marca_" + tipoVehiculo, marca);
        aniadirFiltroCadenaVC(builder, anuncio, predicates, "Modelo_" + tipoVehiculo, modelo);
        aniadirFiltroCadenaVC(builder, anuncio, predicates, "TipoCombustible_" + tipoVehiculo, tipoCombustible);
        aniadirFiltroCadenaVC(builder, anuncio, predicates, "Transmision_" + tipoVehiculo, transmision);
        aniadirFiltroCadenaVC(builder, anuncio, predicates, "Traccion_" + tipoVehiculo, traccion);

        // Ordenar por fecha de publicación descendente
        query.orderBy(builder.desc(anuncio.get("fechaPublicacion")));

        // Aplicar todos los Predicados (las condiciones)
        query.where(predicates.toArray(new Predicate[0]));

        // Termino de crear la sentencia obteniendo los resultados
        TypedQuery<Anuncio> typedQuery = entityManager.createQuery(query);

        // Si el Pageable no es nulo, asigno las cantidades al
        if (pageable != null) {
            typedQuery.setFirstResult((int) pageable.getOffset());
            typedQuery.setMaxResults(pageable.getPageSize());
        }

        // Devuelvo los anuncios en forma de lista
        return typedQuery.getResultList();
    }

    private void aniadirFiltroVC(CriteriaBuilder builder, Root<Anuncio> anuncio, List<Predicate> predicates,
                                 String caracteristicaNombre, Integer min, Integer max) {
        if (min != null || max != null) {
            // Creo una subconsulta
            Subquery<Long> subquery = builder.createQuery().subquery(Long.class);
            // Obtengo el ValorCaracteristica de la subconsulta
            Root<ValorCaracteristica> vc = subquery.from(ValorCaracteristica.class);
            // Hago un join de este ValorCaracteristica para poder filtrar por la Característica
            Join<ValorCaracteristica, Caracteristica> caracteristica = vc.join("caracteristica");

            // Ejecuto la subconsulta seleccionando IDs de anuncios que cumplan las condiciones
            subquery.select(anuncio.get("id"))
                    .where(builder.and(
                            // Comparo que el ValorCaracteristica pertenezca al anuncio actual
                            builder.equal(vc.get("anuncio"), anuncio),
                            // Filtro por el nombre de la característica
                            builder.equal(caracteristica.get("nombre"), caracteristicaNombre),
                            // Filtro por el valor de la característica (si min o max no es null, filtro valores mayores o iguales al mínimo o menores o iguales al máximo)
                            min != null ? builder.ge(builder.toInteger(vc.get("valor")), min) : builder.conjunction(),
                            max != null ? builder.le(builder.toInteger(vc.get("valor")), max) : builder.conjunction()
                    ));

            // Añado la condición a la lista
            predicates.add(builder.exists(subquery));
        }
    }

    private void aniadirFiltroExactoVC(CriteriaBuilder builder, Root<Anuncio> anuncio, List<Predicate> predicates,
                                       String caracteristicaNombre, Integer value) {
        if (value != null && value != 0) {
            // Creo una subconsulta
            Subquery<Long> subquery = builder.createQuery().subquery(Long.class);
            // Obtengo el ValorCaracteristica de la subconsulta
            Root<ValorCaracteristica> vc = subquery.from(ValorCaracteristica.class);
            // Hago un join de este ValorCaracteristica para poder filtrar por la Característica
            Join<ValorCaracteristica, Caracteristica> c = vc.join("caracteristica");

            // Ejecuto la subconsulta seleccionando IDs de anuncios que cumplan las condiciones
            subquery.select(anuncio.get("id"))
                    .where(builder.and(
                            // Comparo que el ValorCaracteristica pertenezca al anuncio actual
                            builder.equal(vc.get("anuncio"), anuncio),
                            // Filtro por el nombre de la característica
                            builder.equal(c.get("nombre"), caracteristicaNombre),
                            // Filtro por el valor de la característica (que sea igual al indicado)
                            builder.equal(builder.toInteger(vc.get("valor")), value)
                    ));

            // Añado la condición a la lista
            predicates.add(builder.exists(subquery));
        }
    }

    private void aniadirFiltroCadenaVC(CriteriaBuilder builder, Root<Anuncio> anuncio, List<Predicate> predicates,
                                       String caracteristicaNombre, String value) {
        if (value != null) {
            // Creo una subconsulta
            Subquery<Long> subquery = builder.createQuery().subquery(Long.class);
            // Obtengo el ValorCaracteristica de la subconsulta
            Root<ValorCaracteristica> vc = subquery.from(ValorCaracteristica.class);
            // Hago un join de este ValorCaracteristica para poder filtrar por la Característica
            Join<ValorCaracteristica, Caracteristica> c = vc.join("caracteristica");

            // Ejecuto la subconsulta seleccionando IDs de anuncios que cumplan las condiciones
            subquery.select(anuncio.get("id"))
                    .where(builder.and(
                            // Comparo que el ValorCaracteristica pertenezca al anuncio actual
                            builder.equal(vc.get("anuncio"), anuncio),
                            // Filtro por el nombre de la característica
                            builder.equal(c.get("nombre"), caracteristicaNombre),
                            // Filtro por el valor de la característica (si la cadena indicada es igual al valor indicado, ambos en minúscula)
                            builder.equal(builder.lower(vc.get("valor")), value.toLowerCase())
                    ));

            // Añado la condición a la lista
            predicates.add(builder.exists(subquery));
        }
    }

    public List<Anuncio> findAnunciosGuardadosByNombreUsuario(String nombreUsuario) {
        return this.anuncioRepository.findAnunciosGuardadosByNombreUsuario(nombreUsuario);
    }
}
