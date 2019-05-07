import org.apache.catalina.LifecycleState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        String s="苏麒.-Java开发工程师-2年工作经验.docx";
        String reg="[.-]";
        String[] split = s.split(reg);
        StringBuilder stringBuilder=new StringBuilder();
        for (String str: split
             ) {
            stringBuilder.append(str);
        }
        String t=stringBuilder.toString();
        System.out.println(t);
//        while (true) {
//            List<Integer> syso = new Test().syso();
//            if (syso==null){
//                System.out.println("null");
//            }
//            for (int i : syso) {
//                System.out.println(i);
//            }
//        }
    }

    private List syso() {
        List li =new ArrayList();
        try{
            li.add(1);
            li.add(2);
            li.add(3);
            li.add(4);
            li.add(5);
            return li;
        }catch (Exception e){
            System.out.println("aaaa");
            return li;
        }finally {
            li=null;
        }

    }
}
