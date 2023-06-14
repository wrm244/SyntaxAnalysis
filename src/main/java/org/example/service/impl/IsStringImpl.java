package org.example.service.impl;

import org.example.service.IsString;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
	* @Description:    判断是否是哪一类字符串，关键字，标识符等
	* @Author:         RiverMountain
	* @CreateDate:
	* @UpdateUser:     RiverMountain
	* @UpdateDate:     2023/6/15 0:20
	* @Version:        1.0
	*/
public class IsStringImpl implements IsString {
    private static final String[] KEYWORDS = {
            "const", "var", "procedure", "begin", "if", "then", "while", "do", "call", "read", "write"
    };

    private static final String[] OPERATORS1 = {
            "+", "-", "*", "/"
    };
    private static final String[] OPERATORS2 = {
            "=", "<", "<=", ">", ">=", "<>", "#"
    };
    private static final String[] OPERATORS = {
            "+", "-", "*", "/", "=", "<", "<=", ">", ">=", "<>", "#", ":="
    };

    private static final String[] DELIMITERS = {
            ",", ";", ".", "(", ")"
    };

    private static final Pattern IDENTIFIER_PATTERN = Pattern.compile("^[a-zA-Z]\\w{0,7}$");
    private static final Pattern INTEGER_PATTERN = Pattern.compile("^\\d{1,8}$");
    private static final Pattern COMMENT_PATTERN = Pattern.compile("(//.*$)");

    public boolean isComment(String line) {
        Matcher matcher = COMMENT_PATTERN.matcher(line);
        return matcher.find();
    }
    public boolean isKeyword(String token) {
        for (String keyword : KEYWORDS) {
            if (keyword.equals(token)) {
                return true;
            }
        }
        return false;
    }
    public boolean isValidIdentifier(String token) {
        Matcher matcher = IDENTIFIER_PATTERN.matcher(token);
        return matcher.matches();
    }

    public boolean isValidInteger(String token) {
        Matcher matcher = INTEGER_PATTERN.matcher(token);
        if (matcher.matches()) {
            try {
                int value = Integer.parseInt(token);
                return value >= 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    public boolean isOperator(char ch) {
        for (String operator : OPERATORS) {
            if (operator.indexOf(ch) != -1) {
                return true;
            }
        }
        return false;
    }
    public boolean isTwoOperator(String ch) {//加减乘除
        for (String operator : OPERATORS1) {
            if (operator.contains(ch)) {
                return true;
            }
        }
        return false;
    }
    public boolean isTwoRelationship(String ch) {//两变量关系
        for (String operator : OPERATORS2) {
            if (operator.contains(ch)) {
                return true;
            }
        }
        return false;
    }
    public boolean isTwoCharOperator(String token) {
        for (String operator : OPERATORS) {
            if (operator.equals(token)) {
                return true;
            }
        }
        return false;
    }

    public boolean isDelimiter(char ch) {
        for (String delimiter : DELIMITERS) {
            if (delimiter.indexOf(ch) != -1) {
                return true;
            }
        }
        return false;
    }
}
