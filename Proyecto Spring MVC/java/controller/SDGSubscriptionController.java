package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.SDGDao;
import es.uji.ei1027.clubesportiu.dao.SDGSubscriptionDao;
import es.uji.ei1027.clubesportiu.model.SDGSubscription;
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
@RequestMapping("/SDGSubscription")
public class SDGSubscriptionController {

    private SDGSubscriptionDao SDGsubscriptionDao;
    private SDGDao SDGDao;

    @Autowired
    public void setSDGSubscriptionDao(SDGSubscriptionDao SDGsubscriptionDao) {
        this.SDGsubscriptionDao = SDGsubscriptionDao;
    }

    @Autowired
    public void setSDGSubscription(SDGDao SDGDao) {
        this.SDGDao = SDGDao;
    }

    @RequestMapping(value="/join/{codSDG}")
    public String joinSDG(Model model, HttpSession session, @PathVariable String codSDG) {
        UjiMember user = (UjiMember) session.getAttribute("user");
        SDGSubscription SDGSubscription = new SDGSubscription();
        SDGSubscription.setMail(user.getMail());
        SDGSubscription.setFechaIni(LocalDate.now());
        SDGSubscription.setCodSDG(codSDG);
        model.addAttribute("NameSDG", SDGDao.getSDG(codSDG).getName());
        model.addAttribute("SDGSubscription", SDGSubscription);
        session.setAttribute("codSDG", codSDG);
        return "SDGSubscription/join";
    }


    @RequestMapping(value="/join", method= RequestMethod.POST)
    public String processJoinSubmit(@ModelAttribute("SDGSubscription") SDGSubscription SDGSubscription,
                                    HttpSession session,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "SDGSubscription/join";
        String codSDG = (String) session.getAttribute("codSDG");
        SDGSubscription.setCodSDG(codSDG);
        SDGsubscriptionDao.addSDGSubscription(SDGSubscription);
        return "redirect:subscriptionStatus";
    }

    @RequestMapping(value="/leave/{codSDG}")
    public String leaveSDG(Model model, HttpSession session, @PathVariable String codSDG) {
        UjiMember user = (UjiMember) session.getAttribute("user");
        SDGSubscription SDGSubscription = SDGsubscriptionDao.getSDGSubscription(codSDG, user.getMail());
        SDGSubscription.setFechaFin(LocalDate.now());
        model.addAttribute("NameSDG", SDGDao.getSDG(codSDG).getName());
        model.addAttribute("SDGSubscription", SDGSubscription);
        session.setAttribute("codSDG", codSDG);
        return "SDGSubscription/leave";
    }


    @RequestMapping(value="/leave", method= RequestMethod.POST)
    public String processLeaveSubmit(@ModelAttribute("SDGSubscription") SDGSubscription SDGSubscription,
                                     HttpSession session,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "SDGSubscription/leave";
        String codSDG = (String) session.getAttribute("codSDG");
        UjiMember user = (UjiMember) session.getAttribute("user");
        SDGSubscription sdg = SDGsubscriptionDao.getSDGSubscription(codSDG,user.getMail());
        SDGsubscriptionDao.deleteSDGSubscription(sdg);
        return "redirect:subscriptionStatus";
    }

    @RequestMapping(value="/subscriptionStatus")
    public String showSubscriptionStatus(Model model, HttpSession session){
        String codSDG = (String) session.getAttribute("codSDG");
        UjiMember user = (UjiMember) session.getAttribute("user");
        SDGSubscription SDGSubscription = SDGsubscriptionDao.getSDGSubscription(codSDG, user.getMail());
        model.addAttribute("SDG", SDGDao.getSDG(codSDG));
        if (SDGSubscription != null) {
            model.addAttribute("subscribed", true);
        } else {
            model.addAttribute("subscribed", false);
        }
        return "SDGSubscription/subscriptionStatus";
    }

}
