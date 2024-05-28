package es.uji.ei1027.clubesportiu.model;

import java.time.LocalDate;

public class Target {
    private String codSDG;
    private String codTarget;
    private String name;
    private String description;
    private Progression progression;
    private LocalDate fechaPrevista;

    public String getCodSDG() {
        return codSDG;
    }

    public void setCodSDG(String codSDG) {
        this.codSDG = codSDG;
    }

    public String getCodTarget() {
        return codTarget;
    }

    public void setCodTarget(String codTarget) {
        this.codTarget = codTarget;
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

    public String getProgression() {
        return progression.name();
    }

    public void setProgression(Progression progression) {
        this.progression = progression;
    }

    public LocalDate getFechaPrevista() {
        return fechaPrevista;
    }

    public void setFechaPrevista(LocalDate fechaPrevista) {
        this.fechaPrevista = fechaPrevista;
    }

    @Override
    public String toString() {
        return "Target{" +
                "codSDG=" + codSDG +
                ", numTarget=" + codTarget +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", progression=" + progression +
                ", fechaPrevista=" + fechaPrevista +
                '}';
    }
}
