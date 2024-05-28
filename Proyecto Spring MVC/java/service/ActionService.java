package es.uji.ei1027.clubesportiu.service;

import es.uji.ei1027.clubesportiu.dao.ActionDao;
import es.uji.ei1027.clubesportiu.dao.ActionParticipationDao;
import es.uji.ei1027.clubesportiu.dao.InitiativeDao;
import es.uji.ei1027.clubesportiu.dao.InitiativeParticipationDao;
import es.uji.ei1027.clubesportiu.model.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActionService {

    @Autowired
    private InitiativeParticipationDao initiativeParticipationDao;

    @Autowired
    private InitiativeDao initiativeDao;

    @Autowired
    private ActionDao actionDao;

    @Autowired
    private ActionParticipationDao actionParticipationDao;

    public Map<Action, List<String>> getActions(String userEmail) {
        List<String> myInitiatives = initiativeParticipationDao.getMySubs(userEmail);
        myInitiatives.addAll(initiativeDao.getMyCreatedInitiatives(userEmail));
        Map<Action, List<String>> actions = new HashMap<>();
        if (!myInitiatives.isEmpty()) {
            for (String codIni : myInitiatives) {
                List<Action> initiativeActions = actionDao.getActionsInitiative(codIni);
                String initiativeTitle = initiativeDao.getInitiative(codIni).getTitle();
                for (Action action : initiativeActions)
                    actions.put(action, Arrays.asList(codIni, initiativeTitle));
            }
        }
        return actions;
    }

    public Map<Action, List<String>> getActions(String userEmail, int currentPage, int pageSize) {
        List<String> myInitiatives = initiativeParticipationDao.getMySubs(userEmail);
        myInitiatives.addAll(initiativeDao.getMyCreatedInitiatives(userEmail));
        Map<Action, List<String>> actions = new LinkedHashMap<>();
        int start = currentPage * pageSize;
        int count = 0;
        for (String codIni : myInitiatives) {
            List<Action> initiativeActions = actionDao.getActionsInitiative(codIni);
            String initiativeTitle = initiativeDao.getInitiative(codIni).getTitle();
            for (Action action : initiativeActions) {
                if (count >= start && count < start + pageSize) {
                    actions.put(action, Arrays.asList(codIni, initiativeTitle));
                }
                count++;
            }
        }
        return actions;
    }


    public Action getAction(String codAction) {
        return actionDao.getAction(codAction);
    }

    public String getInitiativeTitle(String codInitiative) {
        return initiativeDao.getInitiative(codInitiative).getTitle();
    }

    public String getInitiativeMailResponsable(String codInitiative) {
        return initiativeDao.getInitiative(codInitiative).getMailResponsable();
    }

    public boolean isUserSubscribed(String codAction, String userEmail) {
        return actionParticipationDao.isUserSubscribed(codAction, userEmail);
    }

    public String createActionCode() {
        List<String> iniCodes = actionDao.getActionsCod();
        return Coder.createCode(iniCodes, "act");
    }

    public void addAction(Action action) {
        actionDao.addAction(action);
    }

    public void deleteAction(String codAction) {
        actionDao.deleteAction(codAction);
    }

    public List<Action> getActionsInitiative(String codIniciative){return actionDao.getActionsInitiative(codIniciative);}

    public int[] getPrevAndNext(String userEmail, int currentPage, int pageSize) {
        List<String> myInitiatives = initiativeParticipationDao.getMySubs(userEmail);
        myInitiatives.addAll(initiativeDao.getMyCreatedInitiatives(userEmail));
        int listLength = 0;
        for (String codIni : myInitiatives) {
            listLength += actionDao.getActionsInitiative(codIni).size();
        }
        return calculateIndices(currentPage, pageSize, listLength);
    }

    private int[] calculateIndices(int currentPage, int pageSize, double listLength) {
        int totalPages = (int) Math.ceil(listLength / pageSize);
        int prevPage = (currentPage - 1 >= 0) ? currentPage - 1 : -1;
        int nextPage = (currentPage + 1 <= totalPages - 1) ? currentPage + 1 : -1;
        return new int[]{prevPage, nextPage};
    }
}
