package com.reandroid.xml;


import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AndroidXMLHelper {
    public static final String ATTR_ANDROID_NAME="android:name";
    private static final String TAG_APPLICATION="application";
    public static Set<XMLElement> mergeAndroidManifest(XMLDocument base, Set<XMLDocument> splits){
        Set<XMLElement> ret=new HashSet<>();
        if(base==null||splits==null){
            return ret;
        }
        for(XMLDocument res:splits){
            Set<XMLElement> merged=mergeAndroidManifest(base, res);
            ret.addAll(merged);
        }
        return ret;
    }
    private static Set<XMLElement> mergeAndroidManifest(XMLDocument base, XMLDocument split){
        Set<XMLElement> ret=new HashSet<>();
        XMLElement docElemBase=getApplicationElement(base);
        XMLElement docElemSplit=getApplicationElement(split);
        if(docElemBase==null||docElemSplit==null){
            return ret;
        }
        int max=docElemSplit.getChildesCount();
        for(int i=0;i<max;i++){
            XMLElement elementSplit=docElemSplit.getChildAt(i);
            XMLAttribute nameAttr=elementSplit.getAttribute(ATTR_ANDROID_NAME);
            if(nameAttr==null){
                continue;
            }
            if(containsManifestElement(docElemBase,elementSplit)){
                continue;
            }
            ret.add(elementSplit);
            elementSplit.setTag(split.getTag());
            docElemBase.addChild(elementSplit);
        }
        return ret;
    }
    private static XMLElement getApplicationElement(XMLDocument resDocument){
        if(resDocument==null){
            return null;
        }
        XMLElement docElem=resDocument.getDocumentElement();
        int max=docElem.getChildesCount();
        for(int i=0;i<max;i++){
            XMLElement element=docElem.getChildAt(i);
            if(TAG_APPLICATION.equals(element.getTagName())){
                return element;
            }
        }
        return null;
    }
    private static boolean containsManifestElement(XMLElement docElemBase, XMLElement elementSplit){
        int max=docElemBase.getChildesCount();
        for(int i=0;i<max;i++){
            XMLElement elementBase=docElemBase.getChildAt(i);
            if(isEqualAndroidNameElement(elementBase,elementSplit)){
                return true;
            }
        }
        return false;
    }
    private static boolean isEqualAndroidNameElement(XMLElement elementBase, XMLElement elementSplit){
        XMLAttribute attrBase=elementBase.getAttribute(ATTR_ANDROID_NAME);
        XMLAttribute attrSplit=elementSplit.getAttribute(ATTR_ANDROID_NAME);
        if(attrBase==null||attrSplit==null){
            return false;
        }
        String nameValBase=attrBase.getValue();
        if(nameValBase==null){
            nameValBase="";
        }
        return nameValBase.equals(attrSplit.getValue());
    }
    public static XMLDocument loadResourcesXml(File file){
        if(!canIndent(file)){
            return null;
        }
        XMLDocument resDocument=null;
        try {
            resDocument= XMLDocument.load(file);
            resDocument.setTag(file);
        } catch (XMLException e) {
        }
        return resDocument;
    }
    public static XMLDocument sortAndSaveValuesXml(File file){
        XMLDocument resDocument=loadValuesXml(file);
        if(resDocument==null){
            return null;
        }
        try {
            resDocument.saveAndroidValuesResource(file);
        } catch (IOException e) {
        }
        return resDocument;
    }
    public static XMLDocument loadValuesXml(File file){
        if(!isValuesXml(file)){
            return null;
        }
        XMLDocument resDocument=null;
        try {
            resDocument= XMLDocument.load(file);
            resDocument.setTag(file);
        } catch (XMLException e) {
        }
        return resDocument;
    }
    public static void indentAndSaveXml(XMLDocument resDocument, File file){
        if(resDocument==null||file==null){
            return;
        }
        try {
            resDocument.saveAndroidResource(file);
        } catch (IOException e) {
        }
    }
    private static boolean isValuesXml(File file){
        if(file==null){
            return false;
        }
        if(!file.isFile()){
            return false;
        }
        String name=file.getName();
        name=name.toLowerCase();
        if(!name.equals("strings.xml")){
            return false;
        }
        File dir=file.getParentFile();
        if(dir==null){
            return false;
        }
        name=dir.getName().toLowerCase();
        if(!name.startsWith("values")){
            return false;
        }
        return true;
    }
    private static boolean canIndent(File file){
        if(file==null){
            return false;
        }
        if(!file.isFile()){
            return false;
        }
        String name=file.getName();
        if("AndroidManifest.xml".equals(name)){
            return true;
        }
        name=file.getName().toLowerCase();
        if(!name.endsWith(".xml")){
            return false;
        }
        File dir=file.getParentFile();
        if(dir==null){
            return false;
        }
        name=dir.getName().toLowerCase();
        if(name.startsWith("values")){
            return false;
        }
        if(name.equals("raw")){
            return false;
        }
        return true;
    }
}
