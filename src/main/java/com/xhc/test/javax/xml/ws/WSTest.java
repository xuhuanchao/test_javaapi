package com.xhc.test.javax.xml.ws;

import cn.com.webxml.ArrayOfString;
import cn.com.webxml.WeatherWebService;
import cn.com.webxml.WeatherWebServiceSoap;

/**
 * 测试ws,从http://www.webxml.com.cn/WebServices/WeatherWebService.asmx?wsdl 生成客户端代码
 * 使用wsimport 命令，wsdl中<s:element ref="s:schema" /> 会报错，全部删掉，之后提供wsdl文件来生成代码 
 * @author xhc
 *
 */
public class WSTest {

    public static void main(String[] args) {
        WeatherWebService wws = new WeatherWebService();  
        WeatherWebServiceSoap wwsp = wws.getWeatherWebServiceSoap();  
          
        ArrayOfString aos = wwsp.getWeatherbyCityName("北京");  
          
        for (String s : aos.getString()) {     
            System.out.println(s);  
        }  
    }

}
