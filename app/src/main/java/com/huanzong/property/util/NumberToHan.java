package com.huanzong.property.util;

import java.util.Stack;

public class NumberToHan {

    private static String[] number_zh = new String[]{"零","壹","贰","叁","肆","伍","陆","柒","捌","玖","拾"};//拾是多出位
    private static String[] number_zh_jian = new String[]{"零","一","二","三","四","五","六","七","八","九","十"};//拾是多出位
    private static String[] unit_zh = new String[]{"","拾","佰","仟","萬","亿"};



    public static String shuzizhuanzhongwen(Long number){
//number_zh unit_zh
        StringBuffer sb = new StringBuffer();

        String str = number.toString();
        Stack<String> _stack = new Stack<String>();
        for(int i = 0;i < str.length();i++){
            _stack.push(number_zh_jian[(int) (number % 10)] );// 带 拾、佰、仟...  修改为  (int) (number % 10) + getUnitZH(Long.valueOf(i))
            number = number / 10;
        }
        while(!_stack.isEmpty()){
            sb.append(_stack.pop());
        }
        return sb.toString();
    }

    private static String getUnitZH(Long num){
        if(num >= 5 && num < 8){
            return getUnitZH(num - 4);
        }else if(num > 8){
            return getUnitZH(num - 8);
        }else if(num == 8){
            return unit_zh[5]; //亿
        }else if(num > 17){
            return null;//暂不支持 亿亿
        }else{
            return unit_zh[num.intValue()];
        }
    }
}
