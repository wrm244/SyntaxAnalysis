package org.example;
import org.example.service.impl.LexicalAnalysisImpl;
import org.example.service.impl.SyntaxAnalysisImpl;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder code = new StringBuilder();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            code.append(line).append("\n");
            if (line.equals("end.")) {
                break;
            }
        }
        scanner.close();

        LexicalAnalysisImpl analyzeLexical=new LexicalAnalysisImpl(); //实例化词法分析
        SyntaxAnalysisImpl analyzeSyntax = new SyntaxAnalysisImpl();  //实例化语法分析

        SyntaxAnalysisImpl.setTokens(analyzeLexical.analyzeLexically(code.toString()));
        //把词法分析结果的单词符号表传递给语法分析实例
        boolean isValidSyntax = analyzeSyntax.analyzeProgram(); //开始自顶向下的递归下降分析，把结果返回
        if (isValidSyntax && !SyntaxAnalysisImpl.getIfReportSyntaxError()) {
            System.out.println("语法正确");
        }

    }

}