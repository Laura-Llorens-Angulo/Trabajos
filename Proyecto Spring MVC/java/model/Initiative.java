package es.uji.ei1027.clubesportiu.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Initiative {
    private String codInitiative;
    private String codSDG;
    private String mailResponsable;
    private String title;
    private String description;
    private Boolean active;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fechaIni;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fechaFin;
    private String state;


    public String getCodInitiative() {
        return codInitiative;
    }

    public void setCodInitiative(String codInitiative) {
        this.codInitiative = codInitiative;
    }

    public String getCodSDG() {
        return codSDG;
    }

    public void setCodSDG(String codSDG) {
        this.codSDG = codSDG;
    }

    public String getMailResponsable() {
        return mailResponsable;
    }

    public void setMailResponsable(String mailResponsable) {
        this.mailResponsable = mailResponsable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    public boolean isPending() {
        if (state == null){
            return false;
        }
        return state.equalsIgnoreCase("WithoutValuing");
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(String active) {
        if(active.equalsIgnoreCase("TRUE")){this.active=true;}
        else {this.active = false;}
    }

    @Override
    public String toString() {
        return "Initiative{" +
                "codInitiative='" + codInitiative + '\'' +
                ", codSDG='" + codSDG + '\'' +
                ", mailResponsable='" + mailResponsable + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", active=" + active +
                ", fechaIni=" + fechaIni +
                ", fechaFin=" + fechaFin +
                ", state='" + state + '\'' +
                '}';
    }
}
