package com.xhc.test.javaapi.io.serializable.framework;

import net.sf.json.JSONObject;

public interface ITester {

    public Object doSerializableTest(Object object, int n)  throws Exception;
    
    public Object doUnSerializableTest(Object serializeObj) throws Exception;
}
