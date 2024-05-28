package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.model.SDG;
import es.uji.ei1027.clubesportiu.model.UjiMember;
import es.uji.ei1027.clubesportiu.service.SdgService;
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
@RequestMapping("/SDG")
public class SDGController {

    @Autowired
    private SdgService sdgService;

    @RequestMapping("/list/{currentPage}")
    public String listSDGs(@PathVariable int currentPage, Model model, HttpSession session) {
        int pageSize = 6;
        model.addAttribute("SDGs", sdgService.getSDGs(currentPage, pageSize));
        model.addAttribute("currentPage", currentPage);
        int[] prevAndNext = sdgService.getPrevAndNext(currentPage, pageSize);
        model.addAttribute("prevPage", prevAndNext[0]);
        model.addAttribute("nextPage", prevAndNext[1]);
        session.setAttribute("nextUrl", "/SDG/list/0");
        if(session.getAttribute("user") != null){
            UjiMember user = (UjiMember) session.getAttribute("user");
            if(user.getType().equals("OCDS-Staff")){
                return "SDG/listOCDS";
            }
        }
        return "SDG/list";
    }
    @RequestMapping(value="/info/{codSDG}")
    public String showInfo(Model model, @PathVariable String codSDG, HttpSession session) {
        UjiMember user = (UjiMember) session.getAttribute("user");
        SDG sdg = sdgService.getSDG(codSDG);
        model.addAttribute("user", user);
        model.addAttribute("SDG", sdg);
        model.addAttribute("isSubscribed", sdgService.isUserSubscribed(codSDG, user.getMail()));
        model.addAttribute("nextUrl", session.getAttribute("nextUrl"));
        return "SDG/Suscribirse";
    }
    @RequestMapping(value="/add")
    public String addSDG(Model model, HttpSession session) {
        if(session.getAttribute("user") != null) {
            UjiMember user = (UjiMember) session.getAttribute("user");
            if(user.getType().equals("OCDS-Staff")){
                model.addAttribute("SDG", new SDG());
                return "SDG/add";
            }
        }
        return "redirect:list/0";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("SDG") SDG SDG, HttpSession session,
                                   BindingResult bindingResult) {
        SDGValidator sdgValidator = new SDGValidator();
        sdgValidator.validate(SDG, bindingResult);
        if (bindingResult.hasErrors())
            return "SDG/add";
        String newCode = sdgService.createSdgCode();
        SDG.setCodSDG(newCode);
        sdgService.addSDG(SDG);
        session.setAttribute("codSDG", newCode);
        return "redirect:sdgStatus";
    }

    @RequestMapping(value="/sdgStatus")
    public String showsdgStatus(Model model, HttpSession session){
        String codSDG = (String) session.getAttribute("codSDG");
        UjiMember user = (UjiMember) session.getAttribute("user");
        SDG sdg = sdgService.getSDG(codSDG);
        model.addAttribute("SDG", sdg);
        if (sdg != null) {
            model.addAttribute("created", true);
        } else {
            model.addAttribute("created", false);
        }
        return "SDG/sdgStatus";
    }


    @RequestMapping(value="/update/{codSDG}", method = RequestMethod.GET)
    public String editSDG(Model model, @PathVariable String codSDG, HttpSession session) {
        model.addAttribute("SDG", sdgService.getSDG(codSDG));
        session.setAttribute("codSDG", codSDG);
        return "SDG/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("SDG") SDG sdg,
            BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors())
            return "SDG/update";
        String codSDG = (String) session.getAttribute("codSDG");
        sdg.setCodSDG(codSDG);
        sdgService.updateSDG(sdg);
        return "redirect:UpdateConfirmation";
    }

    @RequestMapping(value="/UpdateConfirmation")
    public String showUpdateStatus(Model model, HttpSession session){
        String codSDG = (String) session.getAttribute("codSDG");
        SDG sdg = sdgService.getSDG(codSDG);
        model.addAttribute("SDG", sdg);
        return "SDG/UpdateConfirmation";
    }

    @RequestMapping(value="/delete/{codSDG}")
    public String processDelete(@PathVariable String codSDG) {
        sdgService.deleteSDG(codSDG);
        return "redirect:../list/0";
    }

    @RequestMapping(value = "/EUSdg")
    public String europeanSDGs(){
        return "SDG/EuropeanSDGs";
    }

}
