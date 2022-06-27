package com.reandroid.xml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SchemaAttr extends XMLAttribute {
    private static final String DEFAULT_XMLNS="xmlns";
    private String mXmlns;
    private String mPrefix;
    public SchemaAttr(String prefix, String uri) {
        this(DEFAULT_XMLNS, prefix, uri);
    }
    public SchemaAttr(String xmlns, String prefix, String uri) {
        super(prefix, uri);
        this.set(xmlns, prefix, uri);
    }
    private void set(String xmlns, String prefix, String uri){
        setXmlns(xmlns);
        if(XMLUtil.isEmpty(prefix)){
            prefix=null;
        }
        setName(prefix);
        setUri(uri);
    }
    @Override
    public void setName(String fullName){
        if(fullName==null){
            setPrefix(null);
            return;
        }
        int i=fullName.indexOf(':');
        if(i>0 && i<fullName.length()){
            mXmlns=fullName.substring(0, i);
            mPrefix=fullName.substring(i+1);
        }else {
            setPrefix(fullName);
        }
    }
    public String getXmlns(){
        return mXmlns;
    }
    public String getPrefix(){
        return mPrefix;
    }
    public void setPrefix(String prefix){
        mPrefix=prefix;
    }
    public void setXmlns(String xmlns){
        if(XMLUtil.isEmpty(xmlns)){
            xmlns=DEFAULT_XMLNS;
        }
        mXmlns=xmlns;
    }
    public String getUri(){
        return super.getValue();
    }
    public void setUri(String uri){
        if(uri==null){
            super.setValue(null);
            return;
        }
        Matcher matcher=PATTERN_URI.matcher(uri);
        if(!matcher.find()){
            super.setValue(uri);
            return;
        }
        String prf=matcher.group("B");
        if(!XMLUtil.isEmpty(prf)){
            setPrefix(prf);
        }
        uri=matcher.group("A");
        super.setValue(uri);
    }

    @Override
    public XMLAttribute cloneAttr(){
        SchemaAttr attr=new SchemaAttr(getXmlns(), getPrefix(), getUri());
        attr.setNameId(getNameId());
        attr.setValueId(getValueId());
        return attr;
    }
    @Override
    public String getName(){
        StringBuilder builder=new StringBuilder();
        builder.append(getXmlns());
        builder.append(':');
        String prf=getPrefix();
        if(prf==null){
            prf="NULL";
        }
        builder.append(prf);
        return builder.toString();
    }
    static boolean looksSchema(String name, String value){
        if(value==null || !name.startsWith("xmlns:")){
            return false;
        }
        Matcher matcher=PATTERN_URI.matcher(value);
        return matcher.find();
    }
    private static final Pattern PATTERN_URI=Pattern.compile("^\\s*(?<A>https?://[^:\\s]+)(:(?<B>([^:/\\s]+)))?\\s*$");
}
