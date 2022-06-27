package com.reandroid.xml;


import java.io.IOException;
import java.io.Writer;

public class XMLComment extends XMLElement {
    private String mStart;
    private String mEnd;
    private boolean mIsHidden;
    public XMLComment(String commentText){
        this();
        setCommentText(commentText);
    }
    public XMLComment(){
        super();
        initializeStartEnd();
    }
    @Override
    XMLElement onCloneElement(){
        XMLComment ce=new XMLComment(getCommentText());
        ce.setHidden(isHidden());
        return ce;
    }
    @Override
    void cloneAllAttributes(XMLElement element){
    }
    public void setHidden(boolean hide){
        mIsHidden=hide;
    }
    public boolean isHidden(){
        return mIsHidden;
    }
    public void setCommentText(String text){
        setTextContent(text);
    }
    public String getCommentText(){
        return getTextContent();
    }
    private void initializeStartEnd(){
        setTagName("");
        mStart="<!--";
        mEnd="-->";
        setStart(mStart);
        setEnd(mEnd);
        setStartPrefix("");
        setEndPrefix("");
    }
    @Override
    int getChildIndent(){
        return getIndent();
    }
    @Override
    boolean isEmpty(){
        return XMLUtil.isEmpty(getTextContent());
    }

    @Override
    public boolean write(Writer writer, boolean newLineAttributes) throws IOException {
        if(isHidden()){
            return false;
        }
        if(isEmpty()){
            return false;
        }
        boolean appendOnce=appendComments(writer);
        if(appendOnce){
            writer.write(XMLUtil.NEW_LINE);
        }
        appendIndentText(writer);
        writer.write(mStart);
        writer.write(getCommentText());
        writer.write(mEnd);
        return true;
    }
}
