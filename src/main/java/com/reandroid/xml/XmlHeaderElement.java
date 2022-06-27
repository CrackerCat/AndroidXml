package com.reandroid.xml;

public class XmlHeaderElement extends XMLElement {
    private static final String ATTR_VERSION="version";
    private static final String ATTR_ENCODING="encoding";
    private static final String ATTR_STANDALONE="standalone";
    public XmlHeaderElement(XmlHeaderElement element){
        this();
        copyAll(element);
    }
    public XmlHeaderElement(){
        super();
        initializeStartEnd();
        setDefaultAttr();
    }
    @Override
    XMLElement onCloneElement(){
        return new XmlHeaderElement(this);
    }
    @Override
    void cloneAllAttributes(XMLElement element){
    }
    private void copyAll(XmlHeaderElement element){
        if(element==null){
            return;
        }
        int max=element.getAttributeCount();
        for(int i=0;i<max;i++){
            XMLAttribute exist=element.getAttributeAt(i);
            setAttribute(exist.getName(), exist.getValue());
        }
    }
    private void initializeStartEnd(){
        setTagName("xml");
        setStart("<?");
        setEnd("?>");
        setStartPrefix("");
        setEndPrefix("");
    }
    private void setDefaultAttr(){
        setVersion("1.0");
        setEncoding("utf-8");
        setStandalone(null);
    }
    public Object getProperty(String name){
        XMLAttribute attr=getAttribute(name);
        if(attr==null){
            return null;
        }
        String val=attr.getValue();
        if(ATTR_STANDALONE.equalsIgnoreCase(name)){
            boolean res=false;
            if("true".equals(val)){
                res=true;
            }
            return res;
        }
        return val;
    }
    public void setProperty(String name, Object o){
        if(ATTR_STANDALONE.equalsIgnoreCase(name)){
            if(o instanceof Boolean){
                setStandalone((Boolean)o);
                return;
            }
        }
        String val=null;
        if(o!=null){
            val=o.toString();
        }
        setAttribute(name, val);
    }
    public void setVersion(String version){
        setAttribute(ATTR_VERSION, version);
    }
    public void setEncoding(String encoding){
        setAttribute(ATTR_ENCODING, encoding);
    }
    public void setStandalone(Boolean flag){
        if(flag==null){
            removeAttribute(ATTR_STANDALONE);
            return;
        }
        String str=flag?"yes":"no";
        setAttribute(ATTR_STANDALONE, str);
    }
    @Override
    int getChildIndent(){
        return 0;
    }
    @Override
    int getIndent(){
        return 0;
    }
}
