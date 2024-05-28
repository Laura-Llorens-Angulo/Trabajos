package es.uji.ei1027.clubesportiu.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Coder {
    public static String createCode(List<String> iniCodes, String base) {
        List<Integer> codes = new ArrayList<>();
        int beginIndex = ( base.equals("") ? 0 : 3 );
        for (String code : iniCodes) {
            codes.add(Integer.parseInt(code.substring(beginIndex)));
        }
        Collections.sort(codes);
        String newCode = base;
        for (int i = 0; i < codes.size(); i++) {
            int currentCode = codes.get(i);
            if (i != currentCode) {
                newCode += String.valueOf(i);
                break;
            }
        }
        if (newCode.equals(base)) {
            newCode += String.valueOf(codes.size());
        }
        return newCode;
    }
}
