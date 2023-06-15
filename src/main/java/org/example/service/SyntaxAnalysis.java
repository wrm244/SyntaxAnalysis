package org.example.service;

public interface SyntaxAnalysis {
    /**
     * <h3>实现获取下一个非终结符</h3>
     *
     * @return String
     * @author wrm244
     * @date 2023/6/15 0:20
     */
    String getNextToken();

    /**
     * <h3><程序>::=<分程序>.</h3>
     *
     * @param
     * @return <p>
     * 遇到.可以规约，返回true
     * </p>
     * <p>
     * 如果不是.不可以规约，返回false
     * </p>
     * @author RiverMountain
     * @date 2023/6/15 0:44
     */
    boolean analyzeProgram();

    /**
     * <h3><分程序>::=[<常量说明部分>][<变量说明部分>][<过程说明部分>]<语句></h3>
     *
     * @param
     * @return <p>
     * 如果可以规约，返回True
     * </p>
     * <p>
     * 如果不可以规约，返回False
     * </p>
     * @author RiverMountain
     * @date 2023/6/15 0:52
     */
    boolean analyzeSubprogram();

    /**
     * <h3><常量说明部分>::=const<常量定义>{,<常量定义>};</h3>
     *
     * @param getToken
     * @return <p>
     * 如果可以规约，返回True
     * </p>
     * <p>
     * 如果不可以规约，返回False
     * </p>
     * @author RiverMountain
     * @date 2023/6/15 0:55
     */
    boolean analyzeConstantDeclarationPart(String getToken);

    /**
     * <h3><常量定义>::=<标识符>=<无符号整数></h3>
     *
     * @param
     * @return <p>
     * 如果可以规约，返回True
     * </p>
     * <p>
     * 如果不可以规约，返回False
     * </p>
     * @author RiverMountain
     * @date 2023/6/15 0:57
     */
    void analyzeConstantDefinition();

    /**
     * <h3><变量说明部分>::=var<标识符>{,<标识符>};</h3>
     *
     * @param getToken
     * @return <p>
     * 如果可以规约，返回True
     * </p>
     * <p>
     * 如果不可以规约，返回False
     * </p>
     * @author RiverMountain
     * @date 2023/6/15 1:00
     */
    boolean analyzeVariableDeclarationPart(String getToken);

    /**
     * <h3><过程说明部分>::=<过程首部><分程序>{;<过程说明部分>};</h3>
     *
     * @param getToken
     * @return <p>
     * 如果可以规约，返回True
     * </p>
     * <p>
     * 如果不可以规约，返回False
     * </p>
     * @author RiverMountain
     * @date 2023/6/15 1:02
     */
    boolean analyzeProcedureDeclarationPart(String getToken);

    /**
     * <h3><过程首部>	::=procedure<标识符>;</h3>
     *
     * @param getToken
     * @return <p>
     * 如果可以规约，返回True
     * </p>
     * <p>
     * 如果不可以规约，返回False
     * </p>
     * @author RiverMountain
     * @date 2023/6/15 1:03
     */
    boolean analyzeProcedureHeader(String getToken);

    /**
     * <h3><语句>::=<赋值语句>|<条件语句>|<当型循环语句>|<过程调用语句>|<读语句>|<写语句>|<复合语句>|<空语句></h3>
     *
     * @param getToken
     * @return <p>
     * 如果可以规约，返回True
     * </p>
     * <p>
     * 如果不可以规约，返回False
     * </p>
     * @author RiverMountain
     * @date 2023/6/15 1:03
     */
    boolean analyzeStatement(String getToken);

    /**
     * <h3><复合语句>	::=begin<语句>{;<语句>}end</h3>
     *
     * @param
     * @return <p>
     * 如果可以规约，返回True
     * </p>
     * <p>
     * 如果不可以规约，返回False
     * </p>
     * @author RiverMountain
     * @date 2023/6/15 1:05
     */
    boolean analyzeCompoundStatement();

    /**
     * <h3><条件语句> ::= if<条件>then<语句>[else<语句>]</h3>
     *
     * @param
     * @return <p>
     * 如果可以规约，返回True
     * </p>
     * <p>
     * 如果不可以规约，返回False
     * </p>
     * @author RiverMountain
     * @date 2023/6/15 1:06
     */
    boolean analyzeConditionalStatement();

    /**
     * <h3><当型循环语句>::=while<条件>do<语句></h3>
     *
     * @param
     * @return <p>
     * 如果可以规约，返回True
     * </p>
     * <p>
     * 如果不可以规约，返回False
     * </p>
     * @author RiverMountain
     * @date 2023/6/15 1:06
     */
    boolean analyzeLoopStatement();

    /**
     * <h3><过程调用语句>::=call<标识符></h3>
     *
     * @param
     * @return <p>
     * 如果可以规约，返回True
     * </p>
     * <p>
     * 如果不可以规约，返回False
     * </p>
     * @author RiverMountain
     * @date 2023/6/15 1:07
     */
    boolean analyzeProcedureCallStatement();

    /**
     * <h3><读语句>::=read’（’<标识符>{,<标识符>}’）’</h3>
     *
     * @param
     * @return <p>
     * 如果可以规约，返回True
     * </p>
     * <p>
     * 如果不可以规约，返回False
     * </p>
     * @author RiverMountain
     * @date 2023/6/15 1:09
     */
    boolean analyzeReadStatement();

    /**
     * <h3><写语句>::=write’（’<表达式>{,<表达式>}’）’</h3>
     *
     * @param
     * @return <p>
     * 如果可以规约，返回True
     * </p>
     * <p>
     * 如果不可以规约，返回False
     * </p>
     * @author RiverMountain
     * @date 2023/6/15 1:09
     */
    boolean analyzeWriteStatement();

    /**
     * <h3><赋值语句>	::=<标识符>:=<表达式>(写在表达式函数)</h3>
     *
     * @param
     * @return <p>
     * 如果可以规约，返回True
     * </p>
     * <p>
     * 如果不可以规约，返回False
     * </p>
     * @author RiverMountain
     * @date 2023/6/15 1:10
     */
    boolean analyzeAssignmentStatement();

    /**
     * <h4><表达式>::=[+|-]<项>{<加减运算符><项>} </h4>
     * <h4><项>::=<因子>{<乘除运算符><因子>}</h4>
     * <h4><因子>::=<标识符>|<无符号整数>|’（’<表达式>’）’</h4>
     * <h4><加减运算符>::=+|-</h4>
     * <h4><乘除运算符>::=*|/</h4>
     * <h4><关系运算符>::==|#|<|<=|>|>=</h4>
     *
     * @param
     * @return <p>
     * 如果可以规约，返回True
     * </p>
     * <p>
     * 如果不可以规约，返回False
     * </p>
     * @author RiverMountain
     * @date 2023/6/15 1:12
     */
    boolean analyzeExpression();

    /**
     * <h3><条件>::=<表达式><关系运算符><表达式>|odd<表达式></h3>
     *
     * @param
     * @return <p>
     * 如果可以规约，返回True
     * </p>
     * <p>
     * 如果不可以规约，返回False
     * </p>
     * @author RiverMountain
     * @date 2023/6/15 1:15
     */
    boolean analyzeCondition();

    /**
     * <h3>打印预测错误，标记错误发生</h3>
     *
     * @param
     * @author RiverMountain
     * @date 2023/6/15 1:16
     */
    void reportSyntaxError();

    /**
     * <h3>计算当前标记tokenIndex所在的行号</h3>
     *
     * @param tokenIndex
     * @return <p>
     * 返回行数
     * </p>
     * @author RiverMountain
     * @date 2023/6/15 1:17
     */
    int getLineNumber(int tokenIndex);
}
