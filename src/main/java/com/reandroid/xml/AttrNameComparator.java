package com.reandroid.xml;

import java.util.Comparator;

class AttrNameComparator implements Comparator<XMLAttribute> {
    private String getCompareName(XMLAttribute attr){
        String attrName=attr.getName();
        if(attrName==null){
            return "zzz";
        }
        if(attrName.startsWith("xmlns:")){
            return "0";
        }
        if(attrName.equals("android:id")){
            return "1";
        }
        if(attrName.equals("android:layout_height")){
            return "2";
        }
        if(attrName.equals("android:layout_width")){
            return "3";
        }
        if(attrName.equals("android:name")){
            return "4";
        }
        if(attrName.equals("id")){
            return "5";
        }
        if(attrName.equals("type")){
            return "6";
        }
        if(attrName.equals("name")){
            return "7";
        }
        return attrName;
    }
    @Override
    public int compare(XMLAttribute attr1, XMLAttribute attr2) {
        String name1=getCompareName(attr1);
        String name2=getCompareName(attr2);
        return name1.compareTo(name2);
    }
}
