package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.InitiativeDao;
import es.uji.ei1027.clubesportiu.dao.InitiativeParticipationDao;
import es.uji.ei1027.clubesportiu.model.InitiativeParticipation;
import es.uji.ei1027.clubesportiu.model.UjiMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
@RequestMapping("/initiativeParticipation")
public class InitiativeParticipationController {

    private InitiativeParticipationDao initiativeParticipationDao;
    private InitiativeDao initiativeDao;

    @Autowired
    public void setInitiativeParticipationDao(InitiativeParticipationDao initiativeParticipationDao) {
        this.initiativeParticipationDao = initiativeParticipationDao;
    }

    @Autowired
    public void setInitiativeParticipation(InitiativeDao initiativeDao) {
        this.initiativeDao = initiativeDao;
    }

    @RequestMapping("/list/{codInitiative}")
    public String listInitiatives(Model model, @PathVariable String codInitiative) {
        model.addAttribute("titleIni", initiativeDao.getInitiative(codInitiative).getTitle());
        model.addAttribute("participants", initiativeParticipationDao.getParticipants(codInitiative));
        return "initiativeParticipation/list";
    }

    @RequestMapping(value="/join/{codInitiative}")
    public String joinInitiative(Model model, HttpSession session, @PathVariable String codInitiative) {
        UjiMember user = (UjiMember) session.getAttribute("user");
        InitiativeParticipation initiativeParticipation = new InitiativeParticipation();
        initiativeParticipation.setMail(user.getMail());
        initiativeParticipation.setFechaIni(LocalDate.now());
        model.addAttribute("titleIni", initiativeDao.getInitiative(codInitiative).getTitle());
        model.addAttribute("initiativeParticipation", initiativeParticipation);
        session.setAttribute("codIni", codInitiative);
        return "initiativeParticipation/join";
    }


    @RequestMapping(value="/join", method= RequestMethod.POST)
    public String processJoinSubmit(@ModelAttribute("initiativeParticipation") InitiativeParticipation initiativeParticipation,
                                    HttpSession session,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "initiativeParticipation/join";
        String codIni = (String) session.getAttribute("codIni");
        initiativeParticipation.setCodInitiative(codIni);
        initiativeParticipationDao.addIniciativeParticipation(initiativeParticipation);
        return "redirect:subscriptionStatus";
    }

    @RequestMapping(value="/leave/{codInitiative}")
    public String leaveInitiative(Model model, HttpSession session, @PathVariable String codInitiative) {
        UjiMember user = (UjiMember) session.getAttribute("user");
        InitiativeParticipation initiativeParticipation = initiativeParticipationDao.getInitiativeParticipation(codInitiative, user.getMail());
        initiativeParticipation.setFechaFin(LocalDate.now());
        model.addAttribute("titleIni", initiativeDao.getInitiative(codInitiative).getTitle());
        model.addAttribute("initiativeParticipation", initiativeParticipation);
        session.setAttribute("codIni", codInitiative);
        return "initiativeParticipation/leave";
    }


    @RequestMapping(value="/leave", method= RequestMethod.POST)
    public String processLeaveSubmit(@ModelAttribute("initiativeParticipation") InitiativeParticipation initiativeParticipation,
                                    HttpSession session,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "initiativeParticipation/leave";
        String codIni = (String) session.getAttribute("codIni");
        initiativeParticipation.setCodInitiative(codIni);
        initiativeParticipationDao.deleteInitiativeParticipation(initiativeParticipation);
        return "redirect:subscriptionStatus";
    }

    @RequestMapping(value="/subscriptionStatus")
    public String showSubscriptionStatus(Model model, HttpSession session){
        String codIni = (String) session.getAttribute("codIni");
        UjiMember user = (UjiMember) session.getAttribute("user");
        InitiativeParticipation initiativeParticipation = initiativeParticipationDao.getInitiativeParticipation(codIni, user.getMail());
        model.addAttribute("initiative", initiativeDao.getInitiative(codIni));
        if (initiativeParticipation != null) {
            model.addAttribute("subscribed", true);
        } else {
            model.addAttribute("subscribed", false);
        }
        return "initiativeParticipation/subscriptionStatus";
    }

}
