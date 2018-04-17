package net.onebean.kepler;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 0neBean
 * 密码生成器
 */
public class PassswordGetter {

    public static void main(String[] args) {
        StringBuffer password = new StringBuffer();
        int length = 30;
        int min =33;
        int max =126;
        char start = 33;
        String test = "[A-Za-z0-9\\-]*";
        Matcher m;


        Scanner sc = new Scanner(System.in);
        System.out.println("请输入输入所需密码的长度.");

        try {
            length = sc.nextInt();
        } catch (Exception e) {
            System.out.println("您的输入有误,即将输出默认长度30的密码!");
            try {
                Thread.sleep(1000*2);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }

        while (password.length() != length){
            start = (char) getRandom(max, min);
            m = Pattern.compile(test).matcher(start + "");
            if (m.find() && (start != 34 && start != 92)) {
                password.append(start);
            }
        }

        String result = password.toString();
        System.out.println("密码长度 "+result.length());
        System.out.println(result);
    }

    public static int getRandom(int max,int min) {
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
    }

}
