package com.ihrm.poi.test;

import com.ihrm.poi.handler.SheetHandler;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>使用事件模型解析百万数据excel报表</p>
 *
 * @author xiaodongsun
 * @date 2019/1/12
 */
public class PoiTest {

    public static void main(String[] args) throws OpenXML4JException, IOException, SAXException {
        String path = "";
        //根据excel报表获取OPCPackage
        OPCPackage opcPackage = OPCPackage.open(path, PackageAccess.READ);
        //创建XSSFReader
        XSSFReader reader = new XSSFReader(opcPackage);
        //获取ShareStringsTable对象
        SharedStringsTable sharedStringsTable = reader.getSharedStringsTable();
        //获取stylesTable对象
        StylesTable stylesTable = reader.getStylesTable();
        //创建一个Sax的xmlReader对象
        XMLReader xmlReader = XMLReaderFactory.createXMLReader();
        //注册事件处理器
        XSSFSheetXMLHandler xmlHandler = new XSSFSheetXMLHandler(stylesTable, sharedStringsTable, new SheetHandler(), false);
        xmlReader.setContentHandler(xmlHandler);
        //逐行读取
        XSSFReader.SheetIterator sheetIterator = (XSSFReader.SheetIterator)reader.getSheetsData();
        while (sheetIterator.hasNext()){
            //每一个sheet的流数据
            InputStream inputStream = sheetIterator.next();
            InputSource inputSource = new InputSource(inputStream);
            xmlReader.parse(inputSource);
        }
    }
}
