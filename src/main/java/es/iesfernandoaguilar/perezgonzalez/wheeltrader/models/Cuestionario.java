package es.iesfernandoaguilar.perezgonzalez.wheeltrader.models;

import jakarta.persistence.*;

@Entity
@Table(name = "cuestionarios")
public class Cuestionario {
    // *-- Atributos --* //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuestionario;

    private int comodidad;

    private int rendimiento;

    private int cuidado;

    private int valoracionGeneral;

    // *-- Relaciones --* //
    // Se asigna desde Reunion
    @OneToOne
    @JoinColumn(name = "reunion_id", unique = true)
    private Reunion reunion;

    // *-- Constructores --* //
    public Cuestionario() {}

    public Cuestionario(int comodidad, int rendimiento, int cuidado, int valoracionGeneral) {
        this.comodidad = comodidad;
        this.rendimiento = rendimiento;
        this.cuidado = cuidado;
        this.valoracionGeneral = valoracionGeneral;
    }

    // *-- Getters, Setters --* //

    public Long getIdCuestionario() {
        return idCuestionario;
    }

    public void setIdCuestionario(Long idCuestionario) {
        this.idCuestionario = idCuestionario;
    }

    public int getComodidad() {
        return comodidad;
    }

    public void setComodidad(int comodidad) {
        this.comodidad = comodidad;
    }

    public int getRendimiento() {
        return rendimiento;
    }

    public void setRendimiento(int rendimiento) {
        this.rendimiento = rendimiento;
    }

    public int getCuidado() {
        return cuidado;
    }

    public void setCuidado(int cuidado) {
        this.cuidado = cuidado;
    }

    public int getValoracionGeneral() {
        return valoracionGeneral;
    }

    public void setValoracionGeneral(int valoracionGeneral) {
        this.valoracionGeneral = valoracionGeneral;
    }

    public Reunion getReunion() {
        return reunion;
    }

    public void setReunion(Reunion reunion) {
        this.reunion = reunion;
    }


    // *-- Métodos --* //
}
