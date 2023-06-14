package org.example.service.impl;

import org.example.service.SyntaxAnalysis;

import java.util.Objects;
/**
 * @Description:    语法分析类,对词法分析产生的单词符号表tokens进行分析
 * @Author:         RiverMountain
 * @CreateDate:
 * @UpdateUser:     RiverMountain
 * @UpdateDate:     2023/6/15 0:20
 * @Version:        1.0
 */
public class SyntaxAnalysisImpl extends IsStringImpl implements SyntaxAnalysis {
    private static int currentTokenIndex = 1;
    private static boolean ifReportSyntaxError = false;
    private static String[] tokens;

    public SyntaxAnalysisImpl() {
    }

    public static void setTokens(String[] tokens) {
        SyntaxAnalysisImpl.tokens = tokens;
    }


    /**
     * <h3>返回是否有错误产生，因为要分析每一行代码，所以在分析后要模拟规约，如果分析过程有错误产生则不用打印语法正确</h3>
     * @author RiverMountain
     * @param
     * @date 2023/6/15 1:47
     * @return
     *         <p>
     *         返回是否有错误产生
     *         </p>
     */
    public static boolean getIfReportSyntaxError() {
        return ifReportSyntaxError;
    }

    public String getNextToken() {
        if (currentTokenIndex < tokens.length) {
            return tokens[currentTokenIndex++];
        } else {
            return null;
        }
    }

    public boolean analyzeProgram() {
        if (analyzeSubprogram()) {
            return getNextToken().equals(".");
        }
        return false;
    }

    public boolean analyzeSubprogram() { // <分程序> ::= [<常量说明部分>][<变量说明部分>][<过程说明部分>]<语句>.
        String getTokenTemp = getNextToken();
        if (analyzeConstantDeclarationPart(getTokenTemp)) {
            getTokenTemp = getNextToken();
            while (!isKeyword(getTokenTemp)) {
                getTokenTemp = getNextToken();
            }
            if (getTokenTemp.equals("end")) {
                return true;
            } else if (analyzeVariableDeclarationPart(getTokenTemp)) {
                getTokenTemp = getNextToken();
                while (!isKeyword(getTokenTemp)) {
                    getTokenTemp = getNextToken();
                }
                if (getTokenTemp.equals("end")) {
                    return true;
                } else if (analyzeProcedureDeclarationPart(getTokenTemp)) {
                    getTokenTemp = getNextToken();
                    while (!isKeyword(getTokenTemp)) {
                        getTokenTemp = getNextToken();
                    }
                    if (getTokenTemp.equals("end")) {
                        return true;
                    } else {
                        while (analyzeStatement(getTokenTemp)) { //多个begin
                            if (getNextToken().equals(".")) {
                                currentTokenIndex--; //给上层判断（预判段）
                                return true;
                            }
                            getTokenTemp = getNextToken();
                            while (!isKeyword(getTokenTemp)) {
                                getTokenTemp = getNextToken();
                            }
                        }
                    }
                }
            }
        }
        return analyzeStatement(getTokenTemp);
    }

    public boolean analyzeConstantDeclarationPart(String getToken) { // <常量说明部分> ::= const <常量定义>{, <常量定义>};
        if (getToken.equals("const")) {
            analyzeConstantDefinition(); // <常量定义>
            String getTokenTemp = getNextToken();
            if (getTokenTemp.equals(",")) {
                do {
                    analyzeConstantDefinition();
                } while (getNextToken().equals(","));
            } else if (!getTokenTemp.equals(";")) {
                reportSyntaxError();
            } else {
                currentTokenIndex++; //忽略掉行尾换行
                return true;
            }
        }
        return false;
    }

    public void analyzeConstantDefinition() { // <常量定义> ::= <标识符> = <无符号整数>.
        if (!isValidIdentifier(getNextToken())) {
            reportSyntaxError();
        }
        if (!getNextToken().equals("=")) {
            reportSyntaxError();
        }
        if (!isValidInteger(getNextToken())) {
            reportSyntaxError();
        }
    }

    public boolean analyzeVariableDeclarationPart(String getToken) { // <变量说明部分> ::= var <标识符>{, <标识符>};
        if (getToken.equals("var")) {
            if (!isValidIdentifier(getNextToken())) { // 如果不是标识符号
                reportSyntaxError();
            }
            String getTokenTemp = getNextToken();
            while (getTokenTemp.equals(",")) {
                if (!isValidIdentifier(getNextToken())) {
                    reportSyntaxError();
                }
                getTokenTemp = getNextToken();
            }
            if (!getTokenTemp.equals(";")) {
                reportSyntaxError();
            } else {
                currentTokenIndex++; //忽略掉行尾换行
            }
            return true;
        }
        return false;
    }

    public boolean analyzeProcedureDeclarationPart(String getToken) { // <过程说明部分> ::= {<过程首部><分程序>;}.
        while (analyzeProcedureHeader(getToken)) {
            analyzeSubprogram();
            String getTokenTemp = getNextToken();
            if (!getTokenTemp.equals(";")) {
                if (isKeyword(getTokenTemp)) {
                    currentTokenIndex--; //返回上层让分程序判断
                    return true;
                } else reportSyntaxError();
            } else {
                currentTokenIndex++; //忽略掉行尾换行
                return true;
            }
        }
        return false;
    }

    public boolean analyzeProcedureHeader(String getToken) { // <过程首部> ::= procedure <标识符>;
        if (getToken.equals("procedure")) {
            while (!isValidIdentifier(getNextToken())) { //procedure 后面应该是标识符
                reportSyntaxError();
            }
            if (!getNextToken().equals(";")) {
                reportSyntaxError();
            }
            currentTokenIndex++; //忽略掉行尾换行
            return true;
        }
        return false;
    }

    public boolean analyzeStatement(String getToken) {
        if (getToken == null) {
            reportSyntaxError();
        }
        if (getToken != null) {
            switch (getToken) {
                case "begin":
                    return analyzeCompoundStatement();
                case "if":
                    return analyzeConditionalStatement();
                case "while":
                    return analyzeLoopStatement();
                case "call":
                    return analyzeProcedureCallStatement();
                case "read":
                    return analyzeReadStatement();
                case "write":
                    return analyzeWriteStatement();
                case "end":
                    currentTokenIndex--; //使其读取到end让他回溯
                    return true;

                case "do":
                    currentTokenIndex++; //忽略掉行尾换行
                    return analyzeStatement(getNextToken());
                default:
                    if (isValidIdentifier(getToken)) { //<赋值语句>
                        String getTokenTemp = getNextToken();
                        if (getTokenTemp.equals(":=")) {
                            currentTokenIndex--; //对":="的判断
                            return analyzeAssignmentStatement();
                        } else if (getTokenTemp.equals("=")) {
                            if (analyzeAssignmentStatement()) { //判断表达式
                                if (getNextToken().equals(";")) {
                                    return true;
                                } else {
                                    reportSyntaxError();
                                    return true;
                                }
                            }
                        }
                    }
            }
        }
        return false;
    }

    public boolean analyzeCompoundStatement() { //<复合语句> ::= begin<语句>{;<语句>}end
        currentTokenIndex++;// begin 之后换行
        if (analyzeStatement(getNextToken())) {
            String getTokenTemp = getNextToken();
            while (getTokenTemp.equals(";") || isKeyword(getTokenTemp)) {
                if (!isKeyword(getTokenTemp)) {
                    currentTokenIndex++;// 之后换行
                    if (!analyzeStatement(getNextToken())) {
                        reportSyntaxError();
                    }
                } else {
                    if (!analyzeStatement(getTokenTemp)) {
                        reportSyntaxError();
                    }
                }
                getTokenTemp = getNextToken();
            }
            if (!getTokenTemp.equals("end")) {
                reportSyntaxError();
                return getNextToken().equals("end");
            } else return true; //把分号传递上去
        }
        return false;
    }

    public boolean analyzeConditionalStatement() { //<条件语句> ::= if<条件>then<语句>[else<语句>]
        if (analyzeCondition()) { //条件分析
            if (getNextToken().equals("then")) {
                currentTokenIndex++; //忽略掉行尾换行
                if (analyzeStatement(getNextToken())) {
                    currentTokenIndex += 2;//判断是否是else开头
                    String getTokenTemp = getNextToken();
                    if (getTokenTemp.equals("else")) {
                        return analyzeStatement(getNextToken());
                    } else currentTokenIndex -= 3;//不是else开头返回;
                    return true;
                }
            } else {  //如果不是then就报错 ，判断语句不用交给上层处理
                reportSyntaxError();
                return false;
            }

        }
        return false;
    }

    public boolean analyzeLoopStatement() {
        if (analyzeCondition()) {
            String getTokenTemp = getNextToken();
            if (getTokenTemp.equals("do")) {
                return analyzeStatement(getTokenTemp);
            } else {
                reportSyntaxError();
                return true;
            }
        }
        return false;
    }

    public boolean analyzeProcedureCallStatement() {
        return isValidIdentifier(getNextToken());
    }

    public boolean analyzeReadStatement() { //<读语句> ::= read'('<标识符>{,<标识符>}')‘
        if (getNextToken().equals("(")) {
            if (isValidIdentifier(getNextToken())) {
                String getTokenTemp = getNextToken();
                while (getTokenTemp.equals(",")) {
                    if (!isValidIdentifier(getNextToken())) {
                        reportSyntaxError();
                    }
                    getTokenTemp = getNextToken();
                }
                if (!getTokenTemp.equals(")")) {
                    reportSyntaxError();
                    currentTokenIndex--;//交给上层处理
                }
                return true;
            }
        }
        return false;
    }

    public boolean analyzeWriteStatement() { //<写语句> ::= write'('<标识符>{,<标识符>}')‘
        if (getNextToken().equals("(")) {
            if (analyzeExpression()) {
                String getTokenTemp = getNextToken();
                while (getTokenTemp.equals(",")) {
                    if (!analyzeExpression()) {
                        reportSyntaxError();
                    }
                }
                if (!getTokenTemp.equals(")")) {
                    reportSyntaxError();
                    currentTokenIndex--;//交给上层处理
                }
                return true;
            }
        } else { //没有左边括号的错误
            reportSyntaxError();
            currentTokenIndex--;//交给表达式处理
            if (analyzeExpression()) {
                String getTokenTemp = getNextToken();
                while (getTokenTemp.equals(",")) {
                    if (!analyzeExpression()) {
                        reportSyntaxError();
                    }
                }
                if (!getTokenTemp.equals(")")) {
                    reportSyntaxError();
                    currentTokenIndex--;//交给上层处理
                }
                return true;
            }
        }
        return false;
    }

    public boolean analyzeAssignmentStatement() {
        return analyzeExpression();
    }

    public boolean analyzeExpression() { //<表达式> ::= [+|-]<项>{<加法运算符><项>}
        String getTokenTemp = getNextToken();
        if (isValidIdentifier(getTokenTemp) || isValidInteger(getTokenTemp)) { // 如果是标识符号或者是数字
            getTokenTemp = getNextToken(); //往下判断是否是算数符还是关系式
            if (isTwoOperator(getTokenTemp)) { //是加减乘除
                getTokenTemp = getNextToken();
                if (isValidIdentifier(getTokenTemp) || isValidInteger(getTokenTemp)) {
                    return true;
                }
            } else if (isTwoRelationship(getTokenTemp)) {//是关系表达式
                currentTokenIndex--; //退回，交给关系表达式分析
                return true;
            } else {
                currentTokenIndex--; //退回，交给上一层
                return true;
            }
            return true;
        } else if (Objects.equals(getTokenTemp, ":=")) {//是关系表达式
            getTokenTemp = getNextToken();
            if (isValidIdentifier(getTokenTemp) || isValidInteger(getTokenTemp)) {
                currentTokenIndex--; //返回判断
                return analyzeExpression();
            }
            return false;
        } else return false;

    }

    public boolean analyzeCondition() {//<条件> ::= <表达式><关系运算符><表达式>|odd<表达式>
        if (analyzeExpression()) {  //表达式判断
            String token = getNextToken();
            if (token.equals("=") || token.equals("<>") || token.equals("<") || token.equals("<=") || token.equals(">")
                    || token.equals(">=") || token.equals("#")) {
                return analyzeExpression();
            }
        }
        return false;
    }

    public void reportSyntaxError() {
        System.out.println("(语法错误,行号:" + getLineNumber(currentTokenIndex) + ")");
        ifReportSyntaxError = true;
    }

    public int getLineNumber(int tokenIndex) {
        // 计算当前标记所在的行号
        int lineNumber = 0;
        for (int i = 0; i < tokenIndex - 2; i++) { //减去2为了防止获取到下一行
            if (tokens[i] != null && tokens[i].equals("\n")) {
                lineNumber++;
            }
        }
        return lineNumber;
    }

}
