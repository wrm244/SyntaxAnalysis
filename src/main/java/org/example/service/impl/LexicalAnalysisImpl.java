package org.example.service.impl;

import org.example.service.LexicalAnalysis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @Description:    词法分析类，用于把字符串表存放到待分析的tokens数组中
 * @Author:         RiverMountain
 * @CreateDate:
 * @UpdateUser:     RiverMountain
 * @UpdateDate:     2023/6/15 0:20
 * @Version:        1.0
 */
public class LexicalAnalysisImpl extends IsStringImpl implements LexicalAnalysis {
    public LexicalAnalysisImpl() {
    }
    private static final Pattern ILLEGAL_CHAR_PATTERN = Pattern.compile("[^\\w\\s]");
    /**
     * <h3>词法分析方法，用于把单词符号表存放到待分析的tokens数组中</h3>
     * @author RiverMountain
     * @param code
     * @date 2023/6/15 1:35
     * @return
     *         <p>
     *         tokens
     *         </p>
     */
    public String[] analyzeLexically(String code) {
        // 进行词法分析的代码
        // 返回词法分析结果的字符串数组
        String[] tokens = new String[300];
        int j = 1;
        tokens[0]="\n";
        String[] lines = code.split("\\n");
        boolean insideComment = false;
        for (int lineNumber = 0; lineNumber < lines.length; lineNumber++) {
            String line = lines[lineNumber].trim();
            if (line.isEmpty()) {
                tokens[j++] = "\n";
                continue;
            }

            if (line.startsWith("/*")) {
                insideComment = true;

            }

            if (line.endsWith("*/")) {
                insideComment = false;
                tokens[j++] = "\n";
                continue;
            }
            if (insideComment || isComment(line)) {
                tokens[j++] = "\n";
                continue;
            }
            int index = 0;
            int lineLength = line.length();
            StringBuilder token = new StringBuilder();
            while (index < lineLength) {
                char currentChar = line.charAt(index);

                if (Character.isLetter(currentChar)) {
                    token.append(currentChar);
                    index++;

                    while (index < lineLength
                            && (Character.isLetterOrDigit(line.charAt(index)) || line.charAt(index) == '_')) {
                        token.append(line.charAt(index));
                        index++;
                    }
                    tokens[j++] = token.toString();
                    token.setLength(0);

                } else if (Character.isDigit(currentChar)) { // 判断是否是数字
                    token.append(currentChar);
                    index++;
                    while (index < lineLength && Character.isDigit(line.charAt(index))) {
                        token.append(line.charAt(index));
                        index++;
                    }
                    tokens[j++] = token.toString();
                    token.setLength(0);
                } else if (isOperator(currentChar)) {
                    token.append(currentChar);
                    index++;

                    if (index < lineLength && isTwoCharOperator(token.toString() + line.charAt(index))) {
                        token.append(line.charAt(index));
                        index++;
                    }

                    tokens[j++] = token.toString();
                    token.setLength(0);
                } else if (isDelimiter(currentChar)) {
                    token.append(currentChar);
                    index++;

                    tokens[j++] = token.toString();
                    token.setLength(0);
                } else {
                    Matcher matcher = ILLEGAL_CHAR_PATTERN.matcher(String.valueOf(currentChar));
                    if (matcher.find()) {
                        System.out.println("(非法字符(串)," + currentChar + ",行号:" + (lineNumber + 1) + ")");
                    }
                    index++;
                }
            }
            tokens[j++] = "\n";
        }
        return tokens;
    }

}
