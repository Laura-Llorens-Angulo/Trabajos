package es.uji.ei1027.clubesportiu.model;

public class SDG {
    private String codSDG;
    private String name;
    private String description;
    private int relevance;
    private String URL;

    public SDG() {
    }

    public String getCodSDG() {
        return codSDG;
    }

    public void setCodSDG(String codSDG) {
        this.codSDG = codSDG;
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

    public int getRelevance() {
        return relevance;
    }

    public void setRelevance(int relevance) {
        this.relevance = relevance;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    @Override
    public String toString() {
        return "Sdg{" +
                "codSDG=" + codSDG +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", relevance=" + relevance +
                ", URL='" + URL + '\'' +
                '}';
    }
}
