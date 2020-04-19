package LeetCode.weekend2;

import java.util.HashMap;
import java.util.Map;

public class CroakOfFrogs {
    public static void main(String[] args) {
        System.out.println(new CroakOfFrogs().minNumberOfFrogs("ccccccccccrrccccccrcccccccccccrcccccccccrcccccccccccrcccccrcccrrcccccccccccccrocrrcccccccccrccrocccccrccccrrcccccccrrrcrrcrccrcoccroccrccccccccorocrocccrrrrcrccrcrcrcrccrcroccccrccccroorcacrkcccrrroacccrrrraocccrrcrrccorooccrocacckcrcrrrrrrkrrccrcoacrcorcrooccacorcrccccoocroacroraoaarcoorrcrcccccocrrcoccarrorccccrcraoocrrrcoaoroccooccororrrccrcrocrrcorooocorarccoccocrrrocaccrooaaarrcrarooaarrarrororrcrcckracaccorarorocacrrarorrraoacrcokcarcoccoorcrrkaocorcrcrcrooorrcrroorkkaaarkraroraraarooccrkcrcraocooaoocraoorrrccoaraocoorrcokrararrkaakaooroorcororcaorckrrooooakcarokokcoarcccroaakkrrororacrkraooacrkaraoacaraorrorrakaokrokraccaockrookrokoororoooorroaoaokccraoraraokakrookkroakkaookkooraaocakrkokoraoarrakakkakaroaaocakkarkoocokokkrcorkkoorrkraoorkokkarkakokkkracocoaaaaakaraaooraokarrakkorokkoakokakakkcracarcaoaaoaoorcaakkraooaoakkrrroaoaoaarkkarkarkrooaookkroaaarkooakarakkooaokkoorkroaaaokoarkorraoraorcokokaakkaakrkaaokaaaroarkokokkokkkoakaaookkcakkrakooaooroaaaaooaooorkakrkkakkkkaokkooaakorkaroaorkkokaakaaaaaocrrkakrooaaroroakrakrkrakaoaaakokkaaoakrkkoakocaookkakooorkakoaaaaakkokakkorakaaaaoaarkokorkakokakckckookkraooaakokrrakkrkookkaaoakaaaokkaokkaaoakarkakaakkakorkaakkakkkakaaoaakkkaoaokkkakkkoaroookakaokaakkkkkkakoaooakcokkkrrokkkkaoakckakokkocaokaakakaaakakaakakkkkrakoaokkaakkkkkokkkkkkkkrkakkokkroaakkakaoakkoakkkkkkakakakkkaakkkkakkkrkoak"));
    }

    public int minNumberOfFrogs(String croakOfFrogs) {
        char[] croaks = croakOfFrogs.toCharArray();
        Map<Character, Integer> count = new HashMap<>();
        count.put('c', 0);
        count.put('r', 0);
        count.put('o', 0);
        count.put('a', 0);
        count.put('k', 0);
        int frogNum = 0;
        for (int i = 0; i < croaks.length; i++) {
            int num = count.get(croaks[i]);
            switch (croaks[i]) {
                case 'r':
                    if (num >= count.get('c'))
                        return -1;
                    break;
                case 'o':
                    if (num >= count.get('r'))
                        return -1;
                    break;
                case 'a':
                    if (num >= count.get('o'))
                        return -1;
                    break;
                case 'k':
                    if (num >= count.get('a'))
                        return -1;
                    break;
            }
            count.put(croaks[i], num + 1);
            if (croaks[i] == 'c')
                frogNum = Math.max(frogNum, count.get('c') - count.get('k'));
        }
        if (!count.get('c').equals(count.get('r')) ||
                !count.get('r').equals(count.get('o')) ||
                !count.get('o').equals(count.get('a')) ||
                !count.get('a').equals(count.get('k')) ||
                !count.get('k').equals(count.get('c')))
            return -1;
        return frogNum;
    }
}
