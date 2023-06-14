package org.example.service;

public interface SyntaxAnalysis {
    /**
     * <h3>实现获取下一个非终结符</h3>
     * @author wrm244
     * @return String
     * @date 2023/6/15 0:20
     */
    String getNextToken();
    /**
     * <h3><程序>::=<分程序>.</h3>
     * @author RiverMountain
     * @param
     * @date 2023/6/15 0:44
     * @return
     *         <p>
     *         遇到.可以规约，返回true
     *         </p>
     *         <p>
     *         如果不是.不可以规约，返回false
     *         </p>
     */
    boolean analyzeProgram();
    /**
     * <h3><分程序>::=[<常量说明部分>][<变量说明部分>][<过程说明部分>]<语句></h3>
     * @author RiverMountain
     * @param
     * @date 2023/6/15 0:52
     * @return
     *         <p>
     *          如果可以规约，返回True
     *         </p>
     *         <p>
     *          如果不可以规约，返回False
     *         </p>
     */
    boolean analyzeSubprogram();
    /**
     * <h3><常量说明部分>::=const<常量定义>{,<常量定义>};</h3>
     * @author RiverMountain
     * @param getToken
     * @date 2023/6/15 0:55
     * @return
     *         <p>
     *          如果可以规约，返回True
     *         </p>
     *         <p>
     *          如果不可以规约，返回False
     *         </p>
     */
    boolean analyzeConstantDeclarationPart(String getToken);
    /**
     * <h3><常量定义>::=<标识符>=<无符号整数></h3>
     * @author RiverMountain
     * @param
     * @date 2023/6/15 0:57
     * @return
     *         <p>
     *          如果可以规约，返回True
     *         </p>
     *         <p>
     *          如果不可以规约，返回False
     *         </p>
     */
    void analyzeConstantDefinition();
    /**
     * <h3><变量说明部分>::=var<标识符>{,<标识符>};</h3>
     * @author RiverMountain
     * @param getToken
     * @date 2023/6/15 1:00
     * @return
     *         <p>
     *         如果可以规约，返回True
     *         </p>
     *         <p>
     *         如果不可以规约，返回False
     *         </p>
     */
    boolean analyzeVariableDeclarationPart(String getToken);
    /**
     * <h3><过程说明部分>::=<过程首部><分程序>{;<过程说明部分>};</h3>
     * @author RiverMountain
     * @param getToken
     * @date 2023/6/15 1:02
     * @return
     *         <p>
     *         如果可以规约，返回True
     *         </p>
     *         <p>
     *         如果不可以规约，返回False
     *         </p>
     */
    boolean analyzeProcedureDeclarationPart(String getToken);

    /**
     * <h3><过程首部>	::=procedure<标识符>;</h3>
     * @author RiverMountain
     * @param getToken
     * @date 2023/6/15 1:03
     * @return
     *         <p>
     *         如果可以规约，返回True
     *         </p>
     *         <p>
     *         如果不可以规约，返回False
     *         </p>
     */
    boolean analyzeProcedureHeader(String getToken);
    /**
     * <h3><语句>::=<赋值语句>|<条件语句>|<当型循环语句>|<过程调用语句>|<读语句>|<写语句>|<复合语句>|<空语句></h3>
     * @author RiverMountain
     * @param getToken
     * @date 2023/6/15 1:03
     * @return
     *         <p>
     *         如果可以规约，返回True
     *         </p>
     *         <p>
     *         如果不可以规约，返回False
     *         </p>
     */
    boolean analyzeStatement(String getToken);
    /**
     * <h3><复合语句>	::=begin<语句>{;<语句>}end</h3>
     * @author RiverMountain
     * @param
     * @date 2023/6/15 1:05
     * @return
     *         <p>
     *         如果可以规约，返回True
     *         </p>
     *         <p>
     *         如果不可以规约，返回False
     *         </p>
     */
    boolean analyzeCompoundStatement();
    /**
     * <h3><条件>::=<表达式><关系运算符><表达式>|odd<表达式></h3>
     * @author RiverMountain
     * @param
     * @date 2023/6/15 1:06
     * @return
     *         <p>
     *         如果可以规约，返回True
     *         </p>
     *         <p>
     *         如果不可以规约，返回False
     *         </p>
     */
    boolean analyzeConditionalStatement();
    /**
     * <h3><当型循环语句>::=while<条件>do<语句></h3>
     * @author RiverMountain
     * @param
     * @date 2023/6/15 1:06
     * @return
     *         <p>
     *         如果可以规约，返回True
     *         </p>
     *         <p>
     *         如果不可以规约，返回False
     *         </p>
     */
    boolean analyzeLoopStatement();
    /**
     * <h3><过程调用语句>::=call<标识符></h3>
     * @author RiverMountain
     * @param
     * @date 2023/6/15 1:07
     * @return
     *         <p>
     *         如果可以规约，返回True
     *         </p>
     *         <p>
     *         如果不可以规约，返回False
     *         </p>
     */
    boolean analyzeProcedureCallStatement();
    /**
     * <h3><读语句>::=read’（’<标识符>{,<标识符>}’）’</h3>
     * @author RiverMountain
     * @param
     * @date 2023/6/15 1:09
     * @return
     *         <p>
     *         如果可以规约，返回True
     *         </p>
     *         <p>
     *         如果不可以规约，返回False
     *         </p>
     */
    boolean analyzeReadStatement();
    /**
     * <h3><写语句>::=write’（’<表达式>{,<表达式>}’）’</h3>
     * @author RiverMountain
     * @param
     * @date 2023/6/15 1:09
     * @return
     *         <p>
     *         如果可以规约，返回True
     *         </p>
     *         <p>
     *         如果不可以规约，返回False
     *         </p>
     */
    boolean analyzeWriteStatement();
    /**
     * <h3><赋值语句>	::=<标识符>:=<表达式>(写在表达式函数)</h3>
     * @author RiverMountain
     * @param
     * @date 2023/6/15 1:10
     * @return
     *         <p>
     *         如果可以规约，返回True
     *         </p>
     *         <p>
     *         如果不可以规约，返回False
     *         </p>
     */
    boolean analyzeAssignmentStatement();
    /**
     * <h4><表达式>::=[+|-]<项>{<加减运算符><项>} </h4>
     * <h4><项>::=<因子>{<乘除运算符><因子>}</h4>
     * <h4><因子>::=<标识符>|<无符号整数>|’（’<表达式>’）’</h4>
     * <h4><加减运算符>::=+|-</h4>
     * <h4><乘除运算符>::=*|/</h4>
     * <h4><关系运算符>::==|#|<|<=|>|>=</h4>
     * @author RiverMountain
     * @param
     * @date 2023/6/15 1:12
     * @return
     *         <p>
     *         如果可以规约，返回True
     *         </p>
     *         <p>
     *         如果不可以规约，返回False
     *         </p>
     */
    boolean analyzeExpression();
    /**
     * <h3><条件语句>	::=if<条件>then<语句></h3>
     * @author RiverMountain
     * @param
     * @date 2023/6/15 1:15
     * @return
     *         <p>
     *         如果可以规约，返回True
     *         </p>
     *         <p>
     *         如果不可以规约，返回False
     *         </p>
     */
    boolean analyzeCondition();
    /**
     * <h3>打印预测错误，标记错误发生</h3>
     * @author RiverMountain
     * @param
     * @date 2023/6/15 1:16
     */
    void reportSyntaxError();
    /**
     * <h3>计算当前标记tokenIndex所在的行号</h3>
     * @author RiverMountain
     * @param tokenIndex
     * @date 2023/6/15 1:17
     * @return
     *         <p>
     *         返回行数
     *         </p>
     */
    int getLineNumber(int tokenIndex);
}
