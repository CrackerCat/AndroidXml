package com.reandroid.xml;

import java.util.Comparator;

public abstract class ResourceIDComparator implements Comparator<XMLElement> {
    public abstract int getId(XMLElement element);
    private String getCompareName(XMLElement element){
        String noIdName="zzz";
        int resId=getId(element);
        if(resId==0){
            return noIdName;
        }
        return String.format("0x%08x", resId);
    }
    @Override
    public int compare(XMLElement e1, XMLElement e2) {
        String n1=getCompareName(e1);
        String n2=getCompareName(e2);
        return n1.compareTo(n2);
    }
}
