package com.aplace.core.util;

import java.util.Iterator;
import java.util.List;

//import org.dom4j.Attribute;
//import org.dom4j.Document;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;

//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;

public class XmlUtil {
    /**
     * XML字符串转成JSON对象
     * 
     * @param xml
     * @return
     */
//    public static JSONObject xml2json(String xml) throws Exception {
//        Document document = DocumentHelper.parseText(xml);
//        return elem2json(document.getRootElement());
//    }
//
//    private static JSONObject elem2json(Element elem) {
//        if (null == elem) {
//            return null;
//        }
//
//        JSONObject obj = new JSONObject();
//        // attributes
//        for (Iterator<?> it = elem.attributeIterator(); it.hasNext();) {
//            Attribute attr = (Attribute) it.next();
//            obj.put("@" + attr.getName(), attr.getValue());
//        }
//
//        // child
//        if (elem.elements().size() > 0) {
//            for (Iterator<?> it = elem.elementIterator(); it.hasNext();) {
//                Element child = (Element) it.next();
//                if (obj.containsKey(child.getName())) {
//                    continue;
//                }
//                List<?> nodes = elem.elements(child.getName());
//                if (nodes.size() > 1) {
//                    // 同名子节点多于一个转为JSONArray
//                    JSONArray arr = new JSONArray();
//                    for (int i = 0; i < nodes.size(); i++) {
//                        Element node = (Element) nodes.get(i);
//                        arr.add(elem2json(node));
//                    }
//                    obj.put(child.getName(), arr);
//                } else {
//                    if (0 == child.attributeCount()
//                            && 0 == child.elements().size()) {
//                        obj.put(child.getName(), child.getText());
//                    } else {
//                        obj.put(child.getName(), elem2json(child));
//                    }
//                }
//            }
//        } else {
//            // text
//            if (elem.attributeCount() > 0) {
//                obj.put("#text", elem.getText());
//            } else {
//                obj.put(elem.getName(), elem.getText());
//            }
//        }
//        return obj;
//    }
//
//    public static void main(String[] args) {
//        try {
//            System.out
//                    .println(xml2json("<?xml version=\"1.0\" encoding=\"gb2312\" ?><MSG_BODY><HEAD_DATA><TranCode>DS000015</TranCode><TranName>身份照片查询</TranName><SenderSeq>151230010000001</SenderSeq><TranDate>2017-03-17</TranDate><TranTime>17:37:00</TranTime><RspCode>10000000</RspCode><RspMsg>查询成功-有数据</RspMsg><ChannelId>pc</ChannelId><FrntSeq>170517000007085</FrntSeq></HEAD_DATA><TRAN_DATA><Result>00</Result><IDNumber>152123199103284276</IDNumber><Name>张三</Name><Message>一致</Message><Birthday>19910328</Birthday><Gender>男</Gender><Photo>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</Photo></TRAN_DATA></MSG_BODY>"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
