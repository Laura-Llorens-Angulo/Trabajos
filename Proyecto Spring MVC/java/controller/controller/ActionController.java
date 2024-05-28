package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.model.Action;
import es.uji.ei1027.clubesportiu.model.UjiMember;
import es.uji.ei1027.clubesportiu.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/action")
public class ActionController {

    @Autowired
    private ActionService actionService;


    @RequestMapping("/list/{currentPage}")
    public String listActions(@PathVariable int currentPage, Model model, HttpSession session) {
        int pageSize = 6;
        if(session.getAttribute("user") != null) {
            session.setAttribute("nextUrl", "/action/list/0");
            session.setAttribute("nextActionUrl", "/action/list/0");
            UjiMember user = (UjiMember) session.getAttribute("user");
            Map<Action, List<String>> actions = actionService.getActions(user.getMail(), currentPage, pageSize);
            if (!actions.isEmpty()) {
                model.addAttribute("actions", actions);
                model.addAttribute("subscribed", true);
            } else
                model.addAttribute("subscribed", false);
            model.addAttribute("currentPage", currentPage);
            int[] prevAndNext = actionService.getPrevAndNext(user.getMail(), currentPage, pageSize);
            model.addAttribute("prevPage", prevAndNext[0]);
            model.addAttribute("nextPage", prevAndNext[1]);
        }
        return "action/list";
    }


    @RequestMapping(value = "/listDetails/{codInitiative}")
    public String listActionsFromInitiative(Model model, @PathVariable String codInitiative, HttpSession session) {

        session.setAttribute("codInitiative", codInitiative);
        session.setAttribute("nextActionUrl", "/action/listDetails/" + codInitiative);
        model.addAttribute("iniTitle", actionService.getInitiativeTitle(codInitiative));
        UjiMember user = (UjiMember) session.getAttribute("user");
        List<Action> actions = actionService.getActionsInitiative(codInitiative);
        if (!actions.isEmpty()) {
            model.addAttribute("actions", actions);
            model.addAttribute("thereAreActions", true);
        } else
            model.addAttribute("thereAreActions", false);


        return "action/listFromDetails";
    }

    @RequestMapping(value="/info/{codAction}")
    public String showInfo(Model model, @PathVariable String codAction, HttpSession session) {
        UjiMember user = (UjiMember) session.getAttribute("user");
        Action action = actionService.getAction(codAction);
        model.addAttribute("user", user);
        model.addAttribute("action", action);
        model.addAttribute("iniTitle", actionService.getInitiativeTitle(action.getCodInitiative()));
        model.addAttribute("mailResponsable", actionService.getInitiativeMailResponsable(action.getCodInitiative()));
        model.addAttribute("isSubscribed", actionService.isUserSubscribed(codAction, user.getMail()));
        model.addAttribute("nextActionUrl", session.getAttribute("nextActionUrl"));
        return "action/details";
    }

    @RequestMapping(value = "/add/{codInitiative}")
    public String addAction(Model model, @PathVariable String codInitiative, HttpSession session) {
        session.setAttribute("codInitiative", codInitiative);
        model.addAttribute("action", new Action());
        model.addAttribute("tituloIni", actionService.getInitiativeTitle(codInitiative));
        return "action/add";
    }

    @RequestMapping(value = "/add", method= RequestMethod.POST)
    public String addAction(@ModelAttribute("action") Action action, BindingResult bindingResult,
                            HttpSession session) {
        ActionValidator actionValidator = new ActionValidator();
        actionValidator.validate(action, bindingResult);
        if (bindingResult.hasErrors())
            return "action/add";
        String newCode = actionService.createActionCode();
        action.setCodAction(newCode);
        action.setCodInitiative((String) session.getAttribute("codInitiative"));
        actionService.addAction(action);
        return "redirect:list/0";
    }

    @RequestMapping(value = "/delete/{codAction}")
    public String deleteAction(@PathVariable String codAction) {
        actionService.deleteAction(codAction);
        return "redirect:../list/0";
    }
}
