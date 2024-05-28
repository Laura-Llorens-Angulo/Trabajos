package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.model.ActionParticipation;
import es.uji.ei1027.clubesportiu.model.UjiMember;
import es.uji.ei1027.clubesportiu.service.ActionParticipationService;
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
@RequestMapping("/actionParticipation")
public class ActionParticipationController {

    @Autowired
    private ActionParticipationService actionParticipationService;

    @RequestMapping("/list/{codAction}")
    public String listActions(Model model, @PathVariable String codAction) {
        model.addAttribute("titleAct", actionParticipationService.getActionName(codAction));
        model.addAttribute("participations", actionParticipationService.getActionParticipations(codAction));
        return "actionParticipation/list";
    }

    @RequestMapping(value = "/join/{codAction}")
    public String joinAction(Model model, HttpSession session, @PathVariable String codAction) {
        UjiMember user = (UjiMember) session.getAttribute("user");
        ActionParticipation actionParticipation = new ActionParticipation();
        actionParticipation.setMail(user.getMail());
        actionParticipation.setFechaIni(LocalDate.now());
        model.addAttribute("titleAct", actionParticipationService.getActionName(codAction));
        model.addAttribute("actionParticipation", actionParticipation);
        session.setAttribute("codAct", codAction);
        return "actionParticipation/join";
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String processJoinSubmit(@ModelAttribute("actionParticipation") ActionParticipation actionParticipation,
                                    HttpSession session,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "actionParticipation/join";
        String codAct = (String) session.getAttribute("codAct");
        actionParticipation.setCodAction(codAct);
        actionParticipationService.addActionParticipation(actionParticipation);
        return "redirect:subscriptionStatus";
    }

    @RequestMapping(value = "/leave/{codAction}")
    public String leaveAction(Model model, HttpSession session, @PathVariable String codAction) {
        UjiMember user = (UjiMember) session.getAttribute("user");
        ActionParticipation actionParticipation = actionParticipationService.getActionParticipation(codAction, user.getMail());
        actionParticipation.setFechaFin(LocalDate.now());
        model.addAttribute("titleAct", actionParticipationService.getActionName(codAction));
        model.addAttribute("actionParticipation", actionParticipation);
        session.setAttribute("codAct", codAction);
        return "actionParticipation/leave";
    }

    @RequestMapping(value = "/leave", method = RequestMethod.POST)
    public String processLeaveSubmit(@ModelAttribute("actionParticipation") ActionParticipation actionParticipation,
                                     HttpSession session,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "actionParticipation/leave";
        String codAct = (String) session.getAttribute("codAct");
        actionParticipation.setCodAction(codAct);
        actionParticipationService.deleteActionParticipation(actionParticipation);
        return "redirect:subscriptionStatus";
    }

    @RequestMapping(value = "/subscriptionStatus")
    public String showSubscriptionStatus(Model model, HttpSession session) {
        String codAct = (String) session.getAttribute("codAct");
        UjiMember user = (UjiMember) session.getAttribute("user");
        ActionParticipation actionParticipation = actionParticipationService.getActionParticipation(codAct, user.getMail());
        model.addAttribute("action", actionParticipationService.getAction(codAct));
        if (actionParticipation != null) {
            model.addAttribute("subscribed", true);
        } else {
            model.addAttribute("subscribed", false);
        }
        return "actionParticipation/subscriptionStatus";
    }
}
