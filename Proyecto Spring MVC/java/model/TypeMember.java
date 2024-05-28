package es.uji.ei1027.clubesportiu.model;

public enum TypeMember {
    OCDS_STAFF("OCDS-Staff"),
    STUDENT("Student"),
    NON_REGISTERED("NonRegistered");

    private String tipo;
    TypeMember(String tipo) {
        this.tipo=tipo;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
