package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.model.Initiative;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

class InitiativeValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Initiative.class.isAssignableFrom(cls);
    }
    @Override
    public void validate(Object obj, Errors errors) {

        Initiative initiative = (Initiative) obj;
        if (initiative.getCodSDG().equals("0"))
            errors.rejectValue("codSDG", "obligatori", "Debe seleccionar un SDG");
        if (initiative.getMailResponsable().trim().equals(""))
            errors.rejectValue("mailResponsable", "obligatori", "El mail del responsable no puede estar vacío");
        if (initiative.getTitle().trim().equals(""))
            errors.rejectValue("title", "obligatori", "El título no puede estar vacío");
        if (initiative.getFechaIni() == null)
            errors.rejectValue("fechaIni", "obligatori", "La fecha de inicio no puede estar vacía");
        if (initiative.getFechaIni() != null && initiative.getFechaFin() != null && (initiative.getFechaIni().compareTo(initiative.getFechaFin()) >= 0))
            errors.rejectValue("fechaFin", "invalid", "La fecha de fin debe ser posterior a la fecha de inicio");
  }
}
