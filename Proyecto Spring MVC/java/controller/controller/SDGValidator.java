package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.model.SDG;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

class SDGValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return SDG.class.isAssignableFrom(cls);
    }
    @Override
    public void validate(Object obj, Errors errors) {

        SDG sdg = (SDG) obj;

        if (sdg.getDescription().trim().equals(""))
            errors.rejectValue("description", "obligatori", "La descripción no puede estar vacía");
        if (sdg.getName().trim().equals(""))
            errors.rejectValue("name", "obligatori", "El nombre no puede estar vacío");
        if (sdg.getRelevance()<=0)
            errors.rejectValue("relevance", "obligatori", "La relevancia no puede estar vacía");
        if (sdg.getURL().trim().equals(""))
            errors.rejectValue("URL", "invalid", "La url no puede estar vacía");
    }
}
