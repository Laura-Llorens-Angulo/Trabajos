package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.model.UjiMember;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return UjiMember.class.isAssignableFrom(cls);
    }
    @Override
    public void validate(Object obj, Errors errors) {

        UjiMember user = (UjiMember) obj;
        if (user.getMail().trim().equals(""))
            errors.rejectValue("mail", "obligatori", "El mail no puede estar vacío");
        if (user.getPw().trim().equals(""))
            errors.rejectValue("pw", "obligatori", "La contraseña no puede estar vacía");
    }
}
