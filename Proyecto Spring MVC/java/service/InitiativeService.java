package es.uji.ei1027.clubesportiu.service;

import es.uji.ei1027.clubesportiu.dao.InitiativeDao;
import es.uji.ei1027.clubesportiu.dao.InitiativeParticipationDao;
import es.uji.ei1027.clubesportiu.model.Initiative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InitiativeService {

    @Autowired
    private InitiativeDao initiativeDao;

    @Autowired
    private InitiativeParticipationDao initiativeParticipationDao;

    public List<Initiative> getInitiatives(int currentPage, int pageSize) {
        return initiativeDao.getInitiatives(currentPage, pageSize);
    }

    public List<Initiative> getAcceptedInitiatives(int currentPage, int pageSize) {
        return initiativeDao.getAcceptedInitiatives(currentPage, pageSize);
    }

    public List<Initiative> getMyInitiatives(int currentPage, int pageSize, String userEmail) {
        return initiativeDao.getMyAcceptedInitiatives(currentPage, pageSize,userEmail);
    }
    public List<Initiative> getPendingInitiatives(int currentPage, int pageSize) {
        return initiativeDao.getPendingInitiatives(currentPage, pageSize);
    }
    public String createInitiativeCode() {
        List<String> iniCodes = initiativeDao.getInitiativesCod();
        return Coder.createCode(iniCodes, "ini");
    }

    public void addInitiative(Initiative initiative) {
        initiativeDao.addInitiative(initiative);
    }

    public Initiative getInitiative(String codInitiative) {
        return initiativeDao.getInitiative(codInitiative);
    }

    public void updateInitiative(Initiative initiative) {
        initiativeDao.updateInitiative(initiative);
    }

    public void deleteInitiative(String codInitiative) {
        initiativeDao.deleteInitiative(codInitiative);
    }

    public boolean isUserSubscribed(String codInitiative, String userEmail) {
        return initiativeParticipationDao.isUserSubscribed(codInitiative, userEmail);
    }

    public int[] getPrevAndNext(int num, int pageSize, boolean boss) {
        int listLength = (boss ? initiativeDao.getAllInitiatives().size() : initiativeDao.getAllAcceptedInitiatives().size() );
        return calculateIndices(num, pageSize, listLength);
    }

    public int[] getMyPrevAndNext(int num, int pageSize, String userEmail) {
        int listLength = initiativeDao.getMyAcceptedInitiatives(num, pageSize, userEmail).size();
        return calculateIndices(num, pageSize, listLength);
    }

    private int[] calculateIndices(int num, int pageSize, double listLength) {
        int totalPages = (int) Math.ceil(listLength / pageSize);
        int prev = (num - 1 >= 0) ? num - 1 : -1;
        int next = (num + 1 <= totalPages - 1) ? num + 1 : -1;
        return new int[]{prev, next};
    }

}
