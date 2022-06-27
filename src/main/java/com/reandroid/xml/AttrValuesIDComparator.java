package com.reandroid.xml;


class AttrValuesIDComparator extends ResourceIDComparator {
    @Override
    public int getId(XMLElement element) {
        XMLAttribute nameAttr=element.getAttribute("name");
        if(nameAttr!=null){
            return nameAttr.getValueId();
        }
        return 0;
    }
}
