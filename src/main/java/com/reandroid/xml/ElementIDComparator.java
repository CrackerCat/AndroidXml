package com.reandroid.xml;


public class ElementIDComparator extends ResourceIDComparator {
    @Override
    public int getId(XMLElement element) {
        return element.getResourceId();
    }
}
