package es.uji.ei1027.clubesportiu.model;

public class UjiMember {
    private String mail;
    private String dni;
    private String name;
    private String pw;
    private TypeMember type;

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type.getTipo();
    }

    public void setType(String tipo) {
        if(tipo.equals("STUDENT")){
            type = TypeMember.STUDENT;
        } else if (tipo.equals("OCDS-STAFF")) {
            type = TypeMember.OCDS_STAFF;
        }else{
            type = TypeMember.NON_REGISTERED;
        }
    }

    public boolean isResponsibleForInitiative(Initiative initiative) {
        return mail.equals(initiative.getMailResponsable());
    }

    @Override
    public String toString() {
        return "UjiMember{" +
                "mail='" + mail + '\'' +
                ", dni='" + dni + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type.getTipo() +
                '}';
    }
}
