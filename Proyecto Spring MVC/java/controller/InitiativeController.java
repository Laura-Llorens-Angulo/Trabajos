package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.model.Initiative;
import es.uji.ei1027.clubesportiu.model.UjiMember;
import es.uji.ei1027.clubesportiu.service.InitiativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/initiative")
public class InitiativeController {

    @Autowired
    private InitiativeService initiativeService;

    @RequestMapping(value="/list/{currentPage}")
    public String listInitiatives(@PathVariable int currentPage, Model model, HttpSession session) {
        boolean boss = false;
        String viewName = "initiative/list";
        int pageSize = 6;
        if(session.getAttribute("user") != null){
            session.setAttribute("nextUrl", "/initiative/list/0");
            UjiMember user = (UjiMember) session.getAttribute("user");
            model.addAttribute("user", user);
            if(user.getType().equals("OCDS-Staff")){
                model.addAttribute("initiatives", initiativeService.getInitiatives(currentPage, pageSize));
                boss = true;
                viewName = "initiative/listOCDS";
            }
            if(user.getType().equals("Student")){
                model.addAttribute("initiatives", initiativeService.getAcceptedInitiatives(currentPage, pageSize));
                viewName = "initiative/listUjiMember";
            }
        }else{
            model.addAttribute("initiatives", initiativeService.getAcceptedInitiatives(currentPage, pageSize));
        }
        model.addAttribute("currentPage", currentPage);
        int[] prevAndNext = initiativeService.getPrevAndNext(currentPage, pageSize, boss);
        model.addAttribute("prevPage", prevAndNext[0]);
        model.addAttribute("nextPage", prevAndNext[1]);
        return viewName;
    }

    @RequestMapping(value="/Mylist/{currentPage}")
    public String listMyInitiatives(@PathVariable int currentPage, Model model, HttpSession session) {
        boolean boss = false;
        String viewName = "initiative/Mylist";
        int pageSize = 6;
        if (session.getAttribute("user") != null) {
            session.setAttribute("nextUrl", "/initiative/Mylist/0");
            UjiMember user = (UjiMember) session.getAttribute("user");
            model.addAttribute("initiatives", initiativeService.getMyInitiatives(currentPage, pageSize,user.getMail()));
            model.addAttribute("user", user);
            model.addAttribute("currentPage", currentPage);
            int[] prevAndNext = initiativeService.getMyPrevAndNext(currentPage, pageSize, user.getMail());
            model.addAttribute("prevPage", prevAndNext[0]);
            model.addAttribute("nextPage", prevAndNext[1]);
        }
        return viewName;
    }

    @RequestMapping(value="/Pending/{currentPage}")
    public String listPendingInitiatives(@PathVariable int currentPage, Model model, HttpSession session) {
        boolean boss = false;
        String viewName = "index";
        int pageSize = 6;
        if (session.getAttribute("user") != null ) {
            UjiMember user = (UjiMember) session.getAttribute("user");
            session.setAttribute("nextUrl", "/initiative/Pending/0");
            if (user.getType().equals("OCDS-Staff")){
                viewName = "/initiative/Pending";
                model.addAttribute("pendientes", initiativeService.getPendingInitiatives(currentPage, pageSize));
                model.addAttribute("user", user);
                model.addAttribute("currentPage", currentPage);
                int[] prevAndNext = initiativeService.getPrevAndNext(currentPage, pageSize, boss);
                model.addAttribute("prevPage", prevAndNext[0]);
                model.addAttribute("nextPage", prevAndNext[1]);
            }
        }
        return viewName;
    }

    @RequestMapping(value="/add")
    public String addInitiative(Model model, HttpSession session) {
        Initiative initiative = new Initiative();
        if(session.getAttribute("user") != null) {
            UjiMember user = (UjiMember) session.getAttribute("user");
            initiative.setMailResponsable(user.getMail());
        }else{
            return "redirect:list/0";
        }
        model.addAttribute("initiative", initiative);
        return "initiative/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("initiative") Initiative initiative,  HttpSession session,
                                   BindingResult bindingResult) {
        InitiativeValidator initiativeValidator = new InitiativeValidator();
        initiativeValidator.validate(initiative, bindingResult);
        if (bindingResult.hasErrors())
            return "initiative/add";
        String newCode = initiativeService.createInitiativeCode();
        initiative.setCodInitiative(newCode);
        initiativeService.addInitiative(initiative);
        session.setAttribute("codIni", newCode);
        return "redirect:InitiativeStatus";
    }

    @RequestMapping(value="/InitiativeStatus")
    public String showInitiativeStatus(Model model, HttpSession session){
        String codIni = (String) session.getAttribute("codIni");
        UjiMember user = (UjiMember) session.getAttribute("user");
        Initiative initiative = initiativeService.getInitiative(codIni);
        model.addAttribute("initiative", initiative);
        if (initiative != null) {
            model.addAttribute("created", true);
        } else {
            model.addAttribute("created", false);
        }
        return "initiative/InitiativeStatus";
    }

    @RequestMapping(value="/update/{codInitiative}", method = RequestMethod.GET)
    public String editInitiative(Model model, @PathVariable String codInitiative, HttpSession session) {
        if(session.getAttribute("user") != null) {
            model.addAttribute("initiative", initiativeService.getInitiative(codInitiative));
            session.setAttribute("codIni", codInitiative);
        }else{
            return "login";
        }
        return "initiative/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("initiative") Initiative initiative,
            BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors())
            return "initiative/update";
        initiative.setCodInitiative((String) session.getAttribute("codIni"));
        initiativeService.updateInitiative(initiative);
        return "redirect:UpdateConfirmation";
    }

    @RequestMapping(value="/UpdateConfirmation")
    public String showUpdateStatus(Model model, HttpSession session){
        String codIni = (String) session.getAttribute("codIni");
        Initiative initiative = initiativeService.getInitiative(codIni);
        model.addAttribute("initiative", initiative);
        if (initiative != null) {
            model.addAttribute("modified", true);
        } else {
            model.addAttribute("modified", false);
        }
        return "initiative/UpdateConfirmation";
    }

    @RequestMapping(value = "/accept/{codInitiative}")
    public String acceptInitiative(@PathVariable String codInitiative) {
        Initiative initiative = initiativeService.getInitiative(codInitiative);
        initiative.setState("Accepted");
        initiativeService.updateInitiative(initiative);
        return "redirect:../Pending/0";
    }

    @RequestMapping(value = "/refuse/{codInitiative}")
    public String refuseInitiative(@PathVariable String codInitiative) {
        Initiative initiative = initiativeService.getInitiative(codInitiative);
        initiative.setState("Refused");
        initiativeService.updateInitiative(initiative);
        return "redirect:../Pending/0";
    }

    @RequestMapping(value="/delete/{codInitiative}")
    public String processDelete(@PathVariable String codInitiative) {
        initiativeService.deleteInitiative(codInitiative);
        return "redirect:../list/0";
    }

    @RequestMapping(value="/info/{codInitiative}")
    public String showInfo(Model model, @PathVariable String codInitiative, HttpSession session) {
        UjiMember user = (UjiMember) session.getAttribute("user");
        Initiative initiative = initiativeService.getInitiative(codInitiative);
        model.addAttribute("user", user);
        model.addAttribute("initiative", initiative);
        model.addAttribute("isSubscribed", initiativeService.isUserSubscribed(codInitiative, user.getMail()));
        model.addAttribute("nextUrl", session.getAttribute("nextUrl"));
        return "initiative/details";
    }
}
