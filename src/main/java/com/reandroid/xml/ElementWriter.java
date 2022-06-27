package com.reandroid.xml;

import java.io.IOException;
import java.io.Writer;

class ElementWriter extends Writer {
    private final Writer mWriter;
    private final long mMaxLen;
    private final boolean mUnlimitedLength;
    private long mCurrentLength;
    private boolean mLengthFinished;
    ElementWriter(Writer writer, long maxLen){
        mWriter=writer;
        this.mMaxLen=maxLen;
        this.mUnlimitedLength=maxLen<0;
    }
    ElementWriter(Writer writer){
        this(writer, -1);
    }
    boolean isFinished(){
        return mLengthFinished;
    }
    private boolean mInterruptedWritten;
    void writeInterrupted(){
        if(!mLengthFinished){
            return;
        }
        if(mInterruptedWritten){
            return;
        }
        mInterruptedWritten=true;
        String txt="\n      .\n      .\n      .\n   more items ...\n";
        try {
            mWriter.write(txt);
        } catch (IOException e) {
        }
    }
    @Override
    public void write(char[] chars, int i, int i1) throws IOException {
        updateCurrentLength(i1);
        mWriter.write(chars, i, i1);
    }

    @Override
    public void flush() throws IOException {
        mWriter.flush();
    }
    @Override
    public void close() throws IOException {
        mWriter.close();
    }
    private boolean updateCurrentLength(int len){
        if(mUnlimitedLength){
            return false;
        }
        if(mLengthFinished){
            mLengthFinished=true;
            //return true;
        }
        mCurrentLength+=len;
        mLengthFinished=mCurrentLength>=mMaxLen;
        return mLengthFinished;
    }
}
