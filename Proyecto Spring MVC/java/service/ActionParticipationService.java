package es.uji.ei1027.clubesportiu.service;

import es.uji.ei1027.clubesportiu.dao.ActionDao;
import es.uji.ei1027.clubesportiu.dao.ActionParticipationDao;
import es.uji.ei1027.clubesportiu.dao.UjiMemberDao;
import es.uji.ei1027.clubesportiu.model.Action;
import es.uji.ei1027.clubesportiu.model.ActionParticipation;
import es.uji.ei1027.clubesportiu.model.UjiMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActionParticipationService {

    @Autowired
    private ActionParticipationDao actionParticipationDao;

    @Autowired
    private ActionDao actionDao;

    @Autowired
    private UjiMemberDao ujiMemberDao;

    public Map<String, SimpleEntry<String, LocalDate>> getActionParticipations(String codAction) {
        List<ActionParticipation> actionParticipations = actionParticipationDao.getActionParticipations(codAction);
        Map<String, SimpleEntry<String, LocalDate>> subscriptionInfo = new HashMap<>();
        for(ActionParticipation actionParticipation : actionParticipations) {
            UjiMember member = ujiMemberDao.getUjiMember(actionParticipation.getMail());
            subscriptionInfo.put(actionParticipation.getMail(),
                    new SimpleEntry<>(member.getName(), actionParticipation.getFechaIni()));
        }
        return subscriptionInfo;
    }

    public String getActionName(String codAction) {
        return actionDao.getAction(codAction).getName();
    }

    public void addActionParticipation(ActionParticipation actionParticipation) {
        actionParticipationDao.addActionParticipation(actionParticipation);
    }

    public ActionParticipation getActionParticipation(String codAction, String userEmail) {
        return actionParticipationDao.getActionParticipation(codAction, userEmail);
    }

    public void deleteActionParticipation(ActionParticipation actionParticipation) {
        actionParticipationDao.deleteActionParticipation(actionParticipation);
    }

    public Action getAction(String codAction) {
        return actionDao.getAction(codAction);
    }
}
