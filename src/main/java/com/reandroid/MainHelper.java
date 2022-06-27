package com.reandroid;

import com.reandroid.xml.XMLException;
import com.reandroid.xml.XMLDocument;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class MainHelper {
    private static OutputStream sOutputStream;
    private static InputStream sInputStream;
    private static int INDENT=4;
    static void init(String[] args) throws IOException {
        args=removeEmpty(args);
        File output=parseOut(args);
        args=removeEmpty(args);
        File input=parseInput(args);
        sOutputStream=initOut(output);
        sInputStream=initInput(input);
        if(output!=null && input !=null){
            logError(" INPUT: "+input.getAbsolutePath());
            logError("OUTPUT: "+output.getAbsolutePath());
        }else if(output==null){
            logError("");
        }
    }
    static void run() throws XMLException, IOException {
        XMLDocument resDocument=load();
        write(resDocument);
        logError("");
    }
    private static void write(XMLDocument document) throws IOException {
        OutputStream outputStream=sOutputStream;
        document.setIndent(INDENT);
        document.save(outputStream, false);
        outputStream.flush();
        outputStream.close();
    }
    private static XMLDocument load() throws XMLException, IOException {
        InputStream inputStream=sInputStream;
        XMLDocument document=XMLDocument.load(inputStream);
        inputStream.close();
        return document;
    }
    private static OutputStream initOut(File output) throws IOException {
        if(output==null){
            return System.out;
        }
        File dir=output.getParentFile();
        if(dir!=null && !dir.exists()){
            dir.mkdirs();
        }
        return new FileOutputStream(output);
    }
    private static InputStream initInput(File input) throws IOException {
        if(input==null){
            return System.in;
        }
        return new FileInputStream(input);
    }
    private static File parseOut(String[] args){
        return parseFile(args, "-o");
    }
    private static File parseInput(String[] args){
        if(isEmpty(args)){
            return null;
        }
        int i=args.length-1;
        String path=args[i];
        if(isEmpty(path)){
            return null;
        }
        File file=new File(path);
        file=new File(file.getAbsolutePath());
        args[i]=null;
        if(!file.isFile()){
            return null;
        }
        return file;
    }
    private static File parseFile(String[] args, String argSwitch){
        if(isEmpty(args)){
            return null;
        }
        int max=args.length;
        for(int i=0;i<max;i++){
            String s=args[i];
            if(isEmpty(s)){
                continue;
            }
            if(!s.equalsIgnoreCase(argSwitch)){
                continue;
            }
            int j=i+1;
            if(j>=max){
                return null;
            }
            String path=args[j];
            if(isEmpty(path)){
                return null;
            }
            args[i]=null;
            args[j]=null;
            File file=new File(path);
            return new File(file.getAbsolutePath());
        }
        return null;
    }
    static void logError(String text){
        if(text!=null){
            System.err.println(text);
        }
    }
    private static String[] removeEmpty(String[] args){
        if(isEmpty(args)){
            return null;
        }
        List<String> results=new ArrayList<>();
        int max=args.length;
        for(int i=0;i<max;i++){
            String s=args[i];
            if(!isEmpty(s)){
                results.add(s.trim());
            }
        }
        max=results.size();
        if(max==0){
            return null;
        }
        return results.toArray(new String[max]);
    }
    private static boolean isEmpty(String text){
        if(text==null){
            return true;
        }
        text=text.trim();
        return text.length()==0;
    }
    private static boolean isEmpty(String[] args){
        if(args==null){
            return true;
        }
        if(args.length==0){
            return true;
        }
        return false;
    }
}
