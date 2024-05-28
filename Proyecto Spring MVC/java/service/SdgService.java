package es.uji.ei1027.clubesportiu.service;

import es.uji.ei1027.clubesportiu.dao.SDGDao;
import es.uji.ei1027.clubesportiu.dao.SDGSubscriptionDao;
import es.uji.ei1027.clubesportiu.model.SDG;
import es.uji.ei1027.clubesportiu.model.SDGSubscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SdgService {

    @Autowired
    private SDGDao sdgDao;
    private SDGSubscriptionDao sdgSubscriptionDao;

    public SdgService(SDGSubscriptionDao sdgSubscriptionDao) {
        this.sdgSubscriptionDao = sdgSubscriptionDao;
    }

    public List<SDG> getSDGs(int currentPage, int pageSize) {
        return sdgDao.getSomeSDGs(currentPage, pageSize);
    }

    public int[] getPrevAndNext(int num, int pageSize) {
        int listLength = sdgDao.getAllSDGs().size();
        return calculateIndices(num, pageSize, listLength);
    }

    private int[] calculateIndices(int num, int pageSize, double listLength) {
        int totalPages = (int) Math.ceil(listLength / pageSize);
        int prev = (num - 1 >= 0) ? num - 1 : -1;
        int next = (num + 1 <= totalPages - 1) ? num + 1 : -1;
        return new int[]{prev, next};
    }
    public boolean isUserSubscribed(String codSDG, String userEmail) {
        return sdgSubscriptionDao.isUserSubscribed(codSDG, userEmail);
    }
    public String createSdgCode() {
        List<String> iniCodes = sdgDao.getSDGsCod();
        return Coder.createCode(iniCodes, "");
    }

    public void addSDG(SDG sdg) {
        sdgDao.addSDG(sdg);
    }

    public SDG getSDG(String codSDG) {
        return sdgDao.getSDG(codSDG);
    }

    public void updateSDG(SDG sdg) {
        sdgDao.updateSDG(sdg);
    }

    public void deleteSDG(String codSDG) {
        sdgDao.deleteSDG(codSDG);
    }

}
