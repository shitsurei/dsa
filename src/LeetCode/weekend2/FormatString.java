package LeetCode.weekend2;

import java.util.LinkedList;
import java.util.List;

public class FormatString {
    public String reformat(String s) {
        List<Character> listI = new LinkedList<>();
        List<Character> listC = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9')
                listI.add(s.charAt(i));
            else
                listC.add(s.charAt(i));
        }
        if (Math.abs(listC.size() - listI.size()) > 1)
            return "";
        else {
            List longer, shorter;
            if (listI.size() > listC.size()) {
                longer = listI;
                shorter = listC;
            } else {
                longer = listC;
                shorter = listI;
            }
            StringBuilder builder = new StringBuilder();
            int i = 0;
            for (; i < shorter.size(); i++)
                builder.append(longer.get(i)).append(shorter.get(i));
            if (i < longer.size())
                builder.append(longer.get(i));
            return builder.toString();
        }
    }
}
