package es.uji.ei1027.clubesportiu.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Action {
    private String codAction;
    private String codInitiative;
    private String name;
    private String description;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fechaIni;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fechaFin;

    public String getCodAction() {
        return codAction;
    }

    public void setCodAction(String codAction) {
        this.codAction = codAction;
    }

    public String getCodInitiative() {
        return codInitiative;
    }

    public void setCodInitiative(String codInitiative) {
        this.codInitiative = codInitiative;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(LocalDate fechaIni) {
        this.fechaIni = fechaIni;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "Action{" +
                "codAction='" + codAction + '\'' +
                ", codIniciative='" + codInitiative + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", fechaIni=" + fechaIni +
                ", fechaFin=" + fechaFin +
                '}';
    }
}
