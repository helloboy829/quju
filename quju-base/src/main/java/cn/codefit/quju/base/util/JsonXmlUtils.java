package cn.codefit.quju.base.util;

import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * JSON对象与XML相互转换工具类
 *
 * @author Logan
 * @version 1.0.0
 * @createDate 2019-02-12
 */
public class JsonXmlUtils {

    private static final String ENCODING = "UTF-8";

    /**
     * JSON对象转漂亮的xml字符串
     *
     * @param json JSON对象
     * @return 漂亮的xml字符串
     * @throws IOException
     * @throws SAXException
     */
    public static String jsonToPrettyXml(JSONObject json) throws IOException, SAXException {
        Document document = jsonToDocument(json);

        /* 格式化xml */
        OutputFormat format = OutputFormat.createPrettyPrint();

        // 设置缩进为4个空格
        format.setIndent(" ");
        format.setIndentSize(4);

        StringWriter formatXml = new StringWriter();
        XMLWriter writer = new XMLWriter(formatXml, format);
        writer.write(document);

        return formatXml.toString();
    }

    /**
     * JSON对象转xml字符串
     *
     * @param json JSON对象
     * @return xml字符串
     * @throws SAXException
     */
    public static String JsonToXml(JSONObject json) throws SAXException {
        return jsonToDocument(json).asXML();
    }

    /**
     * JSON对象转Document对象
     *
     * @param json JSON对象
     * @return Document对象
     * @throws SAXException
     */
    public static Document jsonToDocument(JSONObject json) throws SAXException {
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding(ENCODING);

        // root对象只能有一个
        for (String rootKey : json.keySet()) {
            Element root = jsonToElement(json.getJSONObject(rootKey), rootKey);
            document.add(root);
            break;
        }
        return document;
    }

    /**
     * JSON对象转Element对象
     *
     * @param json     JSON对象
     * @param nodeName 节点名称
     * @return Element对象
     */
    public static Element jsonToElement(JSONObject json, String nodeName) {
        Element node = DocumentHelper.createElement(nodeName);
        for (String key : json.keySet()) {
            Object child = json.get(key);
            if (child instanceof JSONObject) {
                node.add(jsonToElement(json.getJSONObject(key), key));
            } else {
                Element element = DocumentHelper.createElement(key);
                element.setText(json.getString(key));
                node.add(element);
            }
        }

        return node;
    }

    /**
     * XML字符串转JSON对象
     *
     * @param xml xml字符串
     * @return JSON对象
     * @throws DocumentException
     */
    public static JSONObject xmlToJson(String xml) throws DocumentException {
        JSONObject json = new JSONObject();

        SAXReader reader = new SAXReader();
        Document document = reader.read(new StringReader(xml));
        Element root = document.getRootElement();

        json.put(root.getName(), elementToJson(root));

        return json;
    }

    /**
     * Element对象转JSON对象
     *
     * @param element Element对象
     * @return JSON对象
     */
    public static JSONObject elementToJson(Element element) {
        JSONObject json = new JSONObject();
        for (Object child : element.elements()) {
            Element e = (Element) child;
            if (e.elements().isEmpty()) {
                json.put(e.getName(), e.getText());
            } else {
                json.put(e.getName(), elementToJson(e));
            }
        }
        return json;
    }

    public static void main(String[] args) throws DocumentException {
        String xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><result_code><![CDATA[SUCCESS]]></result_code><mch_id><![CDATA[1601919820]]></mch_id><appid><![CDATA[wxaada8d5c3652f25e]]></appid><openid><![CDATA[o3AXt4lipW8fuOvMIkP738Uk9D_k]]></openid><is_subscribe><![CDATA[N]]></is_subscribe><device_info><![CDATA[WEB]]></device_info><trade_type><![CDATA[JSAPI]]></trade_type><trade_state><![CDATA[SUCCESS]]></trade_state><bank_type><![CDATA[CMB_DEBIT]]></bank_type><total_fee>1</total_fee><fee_type><![CDATA[CNY]]></fee_type><cash_fee>1</cash_fee><cash_fee_type><![CDATA[CNY]]></cash_fee_type><transaction_id><![CDATA[4200001669202212084319254833]]></transaction_id><out_trade_no><![CDATA[ONO289599416573100032]]></out_trade_no><attach><![CDATA[]]></attach><time_end><![CDATA[20221208000131]]></time_end><trade_state_desc><![CDATA[支付成功]]></trade_state_desc><nonce_str><![CDATA[oeaYXF0JxMEwpNi6]]></nonce_str><sign><![CDATA[7ACCCB68562DC0126DBBDD5D27361E76]]></sign></xml>";

        JSONObject jsonObject = JsonXmlUtils.xmlToJson(xml);
        System.out.println("jsonObject = " + jsonObject);

    }

}
