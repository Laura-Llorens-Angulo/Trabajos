package es.uji.ei1027.clubesportiu.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class InitiativeParticipation {
    private String mail;
    private String codInitiative;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fechaIni;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fechaFin;
    private String comments;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCodInitiative() {
        return codInitiative;
    }

    public void setCodInitiative(String codInitiative) {
        this.codInitiative = codInitiative;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "IniciativeParticipation{" +
                "mail='" + mail + '\'' +
                ", codInitiative='" + codInitiative + '\'' +
                ", fechaIni=" + fechaIni +
                ", fechaFin=" + fechaFin +
                ", comments='" + comments + '\'' +
                '}';
    }
}
