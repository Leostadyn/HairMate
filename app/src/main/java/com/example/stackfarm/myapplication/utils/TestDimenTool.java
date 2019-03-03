package com.example.stackfarm.myapplication.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

public class TestDimenTool {
    public static void main(String[] args) {
        StringBuilder sw400 = new StringBuilder();
        //添加xml开始的标签
        String xmlStart = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<resources>\n";
        sw400.append(xmlStart);
        //添加内容
        double num=360.0/256.0;
        for (int i = 1; i <=256; i++) {
//            此处name后的标签名可以自定义"margin_"随意更改
            String start = "    <dimen name=\"dp_" + change(i) + "\">";
            String end = "dp</dimen>";
            sw400.append(start).append(change(i)).append(end).append("\n");
        }
        //添加xml的尾标签
        sw400.append("</resources>");
        String sw400file = "./app/src/main/res/values/dimens.xml";
        writeFile(sw400file, sw400.toString());
    }
 
    public static void writeFile(String file, String text) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        out.close();
    }
 
    private static String change(double i) {
        DecimalFormat df = new DecimalFormat("#.0");
        String str=df.format(i);
            return str;
    }

    private static String change(int i) {
        return i + "";
    }
}
