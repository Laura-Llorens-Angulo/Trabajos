package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.model.Action;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

class ActionValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Action.class.isAssignableFrom(cls);
    }
    @Override
    public void validate(Object obj, Errors errors) {

        Action action = (Action) obj;

        if (action.getDescription().trim().equals(""))
            errors.rejectValue("description", "obligatori", "La descripción no puede estar vacía");
        if (action.getName().trim().equals(""))
            errors.rejectValue("name", "obligatori", "El nombre no puede estar vacío");
        if (action.getFechaIni() == null)
            errors.rejectValue("fechaIni", "obligatori", "La fecha de inicio no puede estar vacía");
        if (action.getFechaIni() != null && action.getFechaFin() != null && action.getFechaIni().isAfter(action.getFechaFin()))
            errors.rejectValue("fechaIni", "invalid", "La fecha de inicio no puede ser posterior a la fecha de fin");
    }
}
