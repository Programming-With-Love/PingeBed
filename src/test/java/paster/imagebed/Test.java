package paster.imagebed;

import paster.bean.History;
import paster.utlis.HistoryTools;

import java.util.List;

public class Test {

    public static void main(String[] args) {

//        History history = HistoryTools.getHistory("10.1.1.1");
//        List<String> list = HistoryTools.allImg(history);
//        for (String s : list) {
//            System.out.println(s);
//        }

        System.out.println(HistoryTools.historyExist());
    }
}
