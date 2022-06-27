package com.reandroid.xml.parser;

import com.reandroid.xml.XMLUtil;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.*;

public class ResFirstXmlTagParser {

    private final XmlPullParser mParser;
    private String mFirstTag;
    public ResFirstXmlTagParser(XmlPullParser parser){
        this.mParser=parser;
    }

    public String parse() throws XMLParseException {
        try {
            return parseFirstTag();
        } catch (XmlPullParserException | IOException e) {
            throw new XMLParseException(e.getMessage());
        }
    }

    private String parseFirstTag() throws XmlPullParserException, IOException {
        mFirstTag=null;
        int type;
        while ((type=mParser.nextToken()) !=XmlPullParser.END_DOCUMENT){
            event(type);
            if(mFirstTag!=null){
                break;
            }
        }
        event(XmlPullParser.END_DOCUMENT);
        return mFirstTag;
    }
    private void event(int type) {
        if (type == XmlPullParser.START_DOCUMENT){
            onStartDocument();
        }else if (type == XmlPullParser.END_DOCUMENT){
            onEndDocument();
        }else if (type == XmlPullParser.START_TAG){
            onStartTag();
        }else if (type == XmlPullParser.END_TAG){
            onEndTag();
        }else if (type == XmlPullParser.TEXT){
            onText();
        }else if (type == XmlPullParser.ENTITY_REF){
            onEntityRef();
        }else if (type == XmlPullParser.COMMENT){
            onComment();
        }else if (type == XmlPullParser.IGNORABLE_WHITESPACE){
            onIgnore(type);
        }else {
            onUnknownType(type);
        }
    }
    private void onStartDocument(){

    }
    private void onEndDocument(){
    }
    private void onStartTag(){
        String name=mParser.getName();
        String ns=mParser.getNamespace();
        if(!XMLUtil.isEmpty(ns)){
            String prefix=mParser.getPrefix();
            if(!XMLUtil.isEmpty(prefix)){
                name=appendPrefix(prefix,name);
            }
        }
        mFirstTag=name;
    }
    private String appendPrefix(String prefix, String attrName){
        if(!prefix.endsWith(":")){
            prefix=prefix+":";
        }
        if(!attrName.startsWith(prefix)){
            attrName=prefix+attrName;
        }
        return attrName;
    }
    private void onEndTag(){
    }
    private void onText(){
    }
    private void onEntityRef(){
    }
    private void onComment(){
    }
    private void onIgnore(int type){
    }
    private void onUnknownType(int type){
    }

}
