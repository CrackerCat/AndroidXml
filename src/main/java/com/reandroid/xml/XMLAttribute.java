package com.reandroid.xml;


import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class XMLAttribute {
    private int mNameId;
    private int mValueId;
    private Object mValueTag;
    private Object mNameTag;
    private String mName;
    private String mValue;
    public XMLAttribute(String name, String val){
        mName=name;
        mValue= XMLUtil.escapeXmlChars(val);
    }
    public void setNameId(String id){
        setNameId(XMLUtil.hexToInt(id,0));
    }
    public void setNameId(int id){
        mNameId=id;
    }
    public void setValueId(String id){
        setValueId(XMLUtil.hexToInt(id,0));
    }
    public void setValueId(int id){
        mValueId=id;
    }
    public int getNameId(){
        return mNameId;
    }
    public int getValueId(){
        return mValueId;
    }
    public XMLAttribute cloneAttr(){
        XMLAttribute baseAttr=new XMLAttribute(getName(),getValue());
        baseAttr.setNameId(getNameId());
        baseAttr.setValueId(getValueId());
        return baseAttr;
    }
    public Object getValueTag(){
        return mValueTag;
    }
    public void setValueTag(Object obj){
        mValueTag =obj;
    }
    public Object geNameTag(){
        return mNameTag;
    }
    public void setNameTag(Object obj){
        mNameTag =obj;
    }
    public String getName(){
        return mName;
    }
    public String getNamePrefix(){
        int i=mName.indexOf(":");
        if(i>0){
            return mName.substring(0,i);
        }
        return null;
    }
    public String getNameWoPrefix(){
        int i=mName.indexOf(":");
        if(i>0){
            return mName.substring(i+1);
        }
        return mName;
    }
    public String getValue(){
        if(mValue==null){
            mValue="";
        }
        return mValue;
    }
    public int getValueInt(){
        long l=Long.decode(getValue());
        return (int)l;
    }
    public boolean getValueBool(){
        String str=getValue().toLowerCase();
        if("true".equals(str)){
            return true;
        }
        return false;
    }
    public boolean isValueBool(){
        String str=getValue().toLowerCase();
        if("true".equals(str)){
            return true;
        }
        return "false".equals(str);
    }
    public void setName(String name){
        mName=name;
    }
    public void setValue(String val){
        mValue= XMLUtil.escapeXmlChars(val);
    }
    public boolean isEmpty(){
        return XMLUtil.isEmpty(getName());
    }
    @Override
    public boolean equals(Object obj){
        if(obj instanceof XMLAttribute){
            XMLAttribute attr=(XMLAttribute)obj;
            if(isEmpty()){
                return attr.isEmpty();
            }
            String s=toString();
            return s.equals(attr.toString());
        }
        return false;
    }
    public boolean write(Writer writer) throws IOException {
        if(isEmpty()){
            return false;
        }
        writer.write(getName());
        writer.write("=\"");
        String val= XMLUtil.trimQuote(getValue());
        val= XMLUtil.escapeXmlChars(val);
        val= XMLUtil.escapeQuote(val);
        writer.write(val);
        writer.write('"');
        return true;
    }
    @Override
    public String toString(){
        if(isEmpty()){
            return null;
        }
        StringWriter writer=new StringWriter();
        try {
            write(writer);
        } catch (IOException e) {
        }
        writer.flush();
        return writer.toString();
    }
}
