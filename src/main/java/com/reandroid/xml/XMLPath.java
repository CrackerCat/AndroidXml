package com.reandroid.xml;

import java.util.ArrayList;
import java.util.List;

public class XMLPath {
    private final String mName;
    private final XMLPathType mPathType;
    private XMLPath mChild;
    private XMLPath mParent;
    private XMLPath(XMLPathType pathType, String name){
        this.mPathType=pathType;
        this.mName=name;
    }
    public XMLPath setChild(XMLPathType pathType, String name){
        XMLPath child=getChild();
        if(child!=null){
            return child.setChild(pathType, name);
        }
        child=new XMLPath(pathType, name);
        return setChild(child);
    }
    @Override
    public XMLPath clone(){
        XMLPath xmlPath=new XMLPath(getPathType(), getName());
        XMLPath child=getChild();
        if(child!=null){
            xmlPath.setChild(child.clone());
        }
        return xmlPath;
    }
    public List<XMLElement> search(XMLDocument resDocument){
        if(resDocument==null){
            return new ArrayList<>();
        }
        return search(resDocument.getDocumentElement());
    }
    public List<XMLElement> search(XMLElement parent){
        List<XMLElement> results=new ArrayList<>();
        if(parent==null){
            return results;
        }
        String name=getName();
        if(matchesAttr(parent)){
            results.add(parent);
            return results;
        }
        int count=parent.getChildesCount();
        for(int i=0;i<count;i++){
            XMLElement child=parent.getChildAt(i);
            if(name.equalsIgnoreCase(child.getTagName())){
                XMLPath childPath=getChild();
                if(childPath==null){
                    results.add(child);
                    continue;
                }
                List<XMLElement> sub = childPath.search(child);
                results.addAll(sub);
            }
        }
        return results;
    }
    private boolean matchesAttr(XMLElement element){
        XMLPathType pathType=getPathType();
        if(pathType!=XMLPathType.ATTRIBUTE){
            return false;
        }
        String name=getName();
        XMLAttribute baseAttr=element.getAttribute(name);
        if(baseAttr==null){
            return false;
        }
        XMLPath child=getChild();
        if(child!=null){
            return child.getName().equals(baseAttr.getValue());
        }
        return true;
    }
    public String getName(){
        return mName;
    }
    public XMLPath setChild(XMLPath path) {
        if(path==this){
            return this;
        }
        XMLPath child=this.mChild;
        if(child!=null){
            child.mParent=null;
        }
        if(path!=null){
            path.mParent=this;
        }
        this.mChild = path;
        return path;
    }
    private void setParent(XMLPath path) {
        if(path==this){
            return;
        }
        this.mParent = path;
    }
    XMLPathType getPathType(){
        return mPathType;
    }
    XMLPath getChild() {
        return mChild;
    }
    XMLPath getParent() {
        return mParent;
    }
    public String getPath(){
        StringBuilder builder=new StringBuilder();
        builder.append(getName());
        XMLPath child=getChild();
        if(child!=null){
            XMLPathType type=child.getPathType();
            builder.append(type.getSeparator());
            builder.append(child.getPath());
        }
        return builder.toString();
    }
    @Override
    public String toString(){
        return getPath();
    }

    public static XMLPath compile(String path){
        if(isEmpty(path)){
            return null;
        }
        String[] allNames=path.split("[/]+");
        String name=allNames[0];
        XMLPathType type=XMLPathType.ELEMENT;
        XMLPath root=new XMLPath(type, name);
        XMLPath child=root;
        int max=allNames.length-1;
        for(int i=1;i<max; i++){
            name=allNames[i];
            XMLPath xmlPath=new XMLPath(type, name);
            child=child.setChild(xmlPath);
        }
        if(max>0){
            name=allNames[max];
            XMLPath last=compileLast(name);
            child=child.setChild(last);
        }
        return root;
    }
    private static XMLPath compileLast(String path){
        String sep=XMLPathType.ATTRIBUTE.getSeparator();
        int i=path.indexOf(sep);
        if(i<0){
            return new XMLPath(XMLPathType.ELEMENT, path);
        }
        String name=path.substring(0, i);
        i=i+sep.length();
        path=path.substring(i);
        XMLPath elemPath=new XMLPath(XMLPathType.ELEMENT, name);
        sep=XMLPathType.VALUE.getSeparator();
        i=path.indexOf(sep);
        if(i<0){
            XMLPath xmlPath=new XMLPath(XMLPathType.ATTRIBUTE, path);
            elemPath.setChild(xmlPath);
            return elemPath;
        }
        name=path.substring(0, i);
        i=i+sep.length();
        path=path.substring(i);
        XMLPath attrPath=new XMLPath(XMLPathType.ATTRIBUTE, name);
        elemPath.setChild(attrPath);
        XMLPath xmlPath=new XMLPath(XMLPathType.VALUE, path);
        attrPath.setChild(xmlPath);
        return elemPath;
    }
    private static boolean isEmpty(String text){
        if(text==null){
            return true;
        }
        text=text.trim();
        return text.length()==0;
    }
    public enum XMLPathType{
        ELEMENT("/"),
        ATTRIBUTE("<"),
        VALUE(">");

        final String mSeparator;
        XMLPathType(String separator) {
            this.mSeparator=separator;
        }

        public String getSeparator() {
            return mSeparator;
        }
    }
}
