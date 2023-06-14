package org.example.service;

public interface IsString {
    /**
     * <h3>是否匹配以//开头的注释</h3>
     * @author RiverMountain
     * @param
     * @date 2023/6/15 1:50
     * @return
     *         <p>
     *         返回是否匹配以//开头的注释
     *         </p>
     */
    boolean isComment(String line);
    /**
     * <h3>判断是否是关键字</h3>
     * @author RiverMountain
     * @param
     * @date 2023/6/15 1:51
     * @return
     *         <p>
     *         是关键字，返回True
     *         </p>
     *         <p>
     *         不是关键字，返回True
     *         </p>
     */
    boolean isKeyword(String token);
    /**
     * <h3>判断是否为标识符</h3>
     * @author RiverMountain
     * @param
     * @date 2023/6/15 1:52
     * @return
     *         <p>
     *         是标识符，返回True
     *         </p>
     *         <p>
     *         不是标识符，返回False
     *         </p>
     */
    boolean isValidIdentifier(String token);
    /**
     * <h3>判断是否是小于8位的整型</h3>
     * @author RiverMountain
     * @param
     * @date 2023/6/15 1:53
     * @return
     *         <p>
     *         是整型，返回True
     *         </p>
     *         <p>
     *         不是整型，返回False
     *         </p>
     */
    boolean isValidInteger(String token);
    /**
     * <h3>判断字符类型是否为关系运算符（"+", "-", "*", "/", "=", "<", "<=", ">", ">=", "<>", "#", ":="）</h3>
     * @author RiverMountain
     * @param
     * @date 2023/6/15 1:54
     * @return
     *         <p>
     *         是关系运算符，返回True
     *         </p>
     *         <p>
     *         不是关系运算符，返回False
     *         </p>
     */
    boolean isOperator(char ch);
    /**
     * <h3>判断字符串类型是否为运算符（"+", "-", "*", "/"）</h3>
     * @author RiverMountain
     * @param
     * @date 2023/6/15 1:55
     * @return
     *         <p>
     *         是运算符，返回True
     *         </p>
     *         <p>
     *         不是运算符，返回False
     *         </p>
     */
    boolean isTwoOperator(String ch);
    /**
     * <h3>判断是否为关系符（  "=", "<", "<=", ">", ">=", "<>", "#"）</h3>
     * @author RiverMountain
     * @param
     * @date 2023/6/15 1:55
     * @return
     *         <p>
     *         是关系符，返回True
     *         </p>
     *         <p>
     *         不是关系符，返回False
     *         </p>
     */
    boolean isTwoRelationship(String ch);
    /**
     * <h3>判断是否为定界符</h3>
     * @author RiverMountain
     * @param
     * @date 2023/6/15 2:02
     * @return
     *         <p>
     *         是定界符，返回True
     *         </p>
     *         <p>
     *         不是定界符，返回False
     *         </p>
     */
    boolean isDelimiter(char ch);
}
