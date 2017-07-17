
package com.xhc.test.javax.xml.wsimpl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>myObject complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="myObject">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="intValue" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="stringValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "myObject", propOrder = {
    "intValue",
    "stringValue"
})
public class MyObject {

    protected int intValue;
    protected String stringValue;

    /**
     * 获取intValue属性的值。
     * 
     */
    public int getIntValue() {
        return intValue;
    }

    /**
     * 设置intValue属性的值。
     * 
     */
    public void setIntValue(int value) {
        this.intValue = value;
    }

    /**
     * 获取stringValue属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStringValue() {
        return stringValue;
    }

    /**
     * 设置stringValue属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStringValue(String value) {
        this.stringValue = value;
    }

}
