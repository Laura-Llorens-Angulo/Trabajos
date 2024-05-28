package es.uji.ei1027.clubesportiu.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class SDGSubscription {
    private String codSDG;
    private String mail;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fechaIni;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fechaFin;
    private String comments;

    public String getCodSDG() {
        return codSDG;
    }

    public void setCodSDG(String codSDG) {
        this.codSDG = codSDG;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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
        return "SdgSubscription{" +
                "codSDG=" + codSDG +
                ", mail='" + mail + '\'' +
                ", fechaIni=" + fechaIni +
                ", fechaFin=" + fechaFin +
                ", comments='" + comments + '\'' +
                '}';
    }
}
