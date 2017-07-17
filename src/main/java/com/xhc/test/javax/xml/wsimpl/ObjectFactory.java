
package com.xhc.test.javax.xml.wsimpl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xhc.test.javax.xml.wsimpl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _EchoString_QNAME = new QName("http://ws.xml.javax.test.xhc.com/", "echoString");
    private final static QName _EchoStringResponse_QNAME = new QName("http://ws.xml.javax.test.xhc.com/", "echoStringResponse");
    private final static QName _EchoIntResponse_QNAME = new QName("http://ws.xml.javax.test.xhc.com/", "echoIntResponse");
    private final static QName _EchoMyObject_QNAME = new QName("http://ws.xml.javax.test.xhc.com/", "echoMyObject");
    private final static QName _EchoMyObjectResponse_QNAME = new QName("http://ws.xml.javax.test.xhc.com/", "echoMyObjectResponse");
    private final static QName _EchoInt_QNAME = new QName("http://ws.xml.javax.test.xhc.com/", "echoInt");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xhc.test.javax.xml.wsimpl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EchoInt }
     * 
     */
    public EchoInt createEchoInt() {
        return new EchoInt();
    }

    /**
     * Create an instance of {@link EchoStringResponse }
     * 
     */
    public EchoStringResponse createEchoStringResponse() {
        return new EchoStringResponse();
    }

    /**
     * Create an instance of {@link EchoIntResponse }
     * 
     */
    public EchoIntResponse createEchoIntResponse() {
        return new EchoIntResponse();
    }

    /**
     * Create an instance of {@link EchoMyObject }
     * 
     */
    public EchoMyObject createEchoMyObject() {
        return new EchoMyObject();
    }

    /**
     * Create an instance of {@link EchoMyObjectResponse }
     * 
     */
    public EchoMyObjectResponse createEchoMyObjectResponse() {
        return new EchoMyObjectResponse();
    }

    /**
     * Create an instance of {@link EchoString }
     * 
     */
    public EchoString createEchoString() {
        return new EchoString();
    }

    /**
     * Create an instance of {@link MyObject }
     * 
     */
    public MyObject createMyObject() {
        return new MyObject();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EchoString }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.xml.javax.test.xhc.com/", name = "echoString")
    public JAXBElement<EchoString> createEchoString(EchoString value) {
        return new JAXBElement<EchoString>(_EchoString_QNAME, EchoString.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EchoStringResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.xml.javax.test.xhc.com/", name = "echoStringResponse")
    public JAXBElement<EchoStringResponse> createEchoStringResponse(EchoStringResponse value) {
        return new JAXBElement<EchoStringResponse>(_EchoStringResponse_QNAME, EchoStringResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EchoIntResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.xml.javax.test.xhc.com/", name = "echoIntResponse")
    public JAXBElement<EchoIntResponse> createEchoIntResponse(EchoIntResponse value) {
        return new JAXBElement<EchoIntResponse>(_EchoIntResponse_QNAME, EchoIntResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EchoMyObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.xml.javax.test.xhc.com/", name = "echoMyObject")
    public JAXBElement<EchoMyObject> createEchoMyObject(EchoMyObject value) {
        return new JAXBElement<EchoMyObject>(_EchoMyObject_QNAME, EchoMyObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EchoMyObjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.xml.javax.test.xhc.com/", name = "echoMyObjectResponse")
    public JAXBElement<EchoMyObjectResponse> createEchoMyObjectResponse(EchoMyObjectResponse value) {
        return new JAXBElement<EchoMyObjectResponse>(_EchoMyObjectResponse_QNAME, EchoMyObjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EchoInt }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.xml.javax.test.xhc.com/", name = "echoInt")
    public JAXBElement<EchoInt> createEchoInt(EchoInt value) {
        return new JAXBElement<EchoInt>(_EchoInt_QNAME, EchoInt.class, null, value);
    }

}
