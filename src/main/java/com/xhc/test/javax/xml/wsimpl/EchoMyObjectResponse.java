
package com.xhc.test.javax.xml.wsimpl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>echoMyObjectResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="echoMyObjectResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://ws.xml.javax.test.xhc.com/}myObject" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "echoMyObjectResponse", propOrder = {
    "_return"
})
public class EchoMyObjectResponse {

    @XmlElement(name = "return")
    protected MyObject _return;

    /**
     * 获取return属性的值。
     * 
     * @return
     *     possible object is
     *     {@link MyObject }
     *     
     */
    public MyObject getReturn() {
        return _return;
    }

    /**
     * 设置return属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link MyObject }
     *     
     */
    public void setReturn(MyObject value) {
        this._return = value;
    }

}
