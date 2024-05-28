package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.InitiativeDao;
import es.uji.ei1027.clubesportiu.dao.UjiMemberDao;
import es.uji.ei1027.clubesportiu.model.Initiative;
import es.uji.ei1027.clubesportiu.model.UjiMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private UjiMemberDao ujiMemberDao;
    private InitiativeDao initiativeDao;

    public LoginController(InitiativeDao initiativeDao) {
        this.initiativeDao = initiativeDao;
    }

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UjiMember());
        return "login";
    }

    @RequestMapping(value="/login", method= RequestMethod.POST)
    public String checkLogin(@ModelAttribute("user") UjiMember user,
                             BindingResult bindingResult, HttpSession session, Model model) {
        UserValidator userValidator = new UserValidator();
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }
        // Comprova que el login siga correcte
        // intentant carregar les dades de l'usuari
        UjiMember userBo = ujiMemberDao.getUjiMember(user.getMail());
        if (userBo == null) {
            bindingResult.rejectValue("mail", "badml", "Correo no valido.");
            return "login";
        }

        if (!user.getPw().equals(userBo.getPw())) {
            bindingResult.rejectValue("pw", "badpw", "Contraseña incorrecta.");
            return "login";
        }
        // Autenticats correctament.
        // Guardem les dades de l'usuari autenticat a la sessió
        session.setAttribute("user", userBo);
        // Torna a la pàgina principal per als OCDS-Staff
        if(userBo.getType().equals("OCDS-Staff")){
         // System.out.println("Som OCDS-Staff");
            List<Initiative> pendientes = initiativeDao.getPending();
            model.addAttribute("pendientes",pendientes);
            return "redirect:/initiative/Pending/0";
        }
        // Torna a la pàgina principal per a la resta
        if (session.getAttribute("nextURL") != null) {
            String ruta = (String) session.getAttribute("nextURL");
            session.removeAttribute("nextURL");
            return "redirect:" + ruta;
        }
        return "redirect:/";
    }

    @RequestMapping("/info")
    public String info(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        return "info";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
