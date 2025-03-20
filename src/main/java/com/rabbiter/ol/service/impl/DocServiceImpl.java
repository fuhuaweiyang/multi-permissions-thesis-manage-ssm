package com.rabbiter.ol.service.impl;

import com.rabbiter.ol.dao.DocDao;
import com.rabbiter.ol.entity.DocEntity;
import com.rabbiter.ol.service.DocService;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class DocServiceImpl implements DocService {

    @Autowired
    private DocDao docDao;

    public DocEntity saveDocument(String filePath) {
        String textContent = readWordFile(filePath);
        if (textContent == null) {
            System.out.println("无法提取文本");
            return null;
        }

//        String sql = "INSERT INTO doc (id, word) VALUES (?, ?)";
//
//        String URL = "jdbc:mysql://localhost:3306/online_learn?&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8";
//        String USER = "root";
//        String PASSWORD = "Gy3503556";

        DocEntity docEntity = new DocEntity();
        try (FileInputStream fis = new FileInputStream(filePath);)
        {
            // 读取文件为 byte[]
            File file = new File(filePath);
            byte[] fileBytes = new byte[(int) file.length()];
            fis.read(fileBytes);
            docEntity.setFiles(fileBytes);
            docEntity.setTxt(textContent);
            docEntity.setStuId(2L);
            docEntity.setTeacherId(3L);
            System.out.println("文件已成功插入数据库。");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return docEntity;
    }

    // 读取 .doc 或 .docx 文件
    public static String readWordFile(String filePath) {
        if (filePath.toLowerCase().endsWith(".doc")) {
            return readDocFile(filePath);
        } else if (filePath.toLowerCase().endsWith(".docx")) {
            return readDocxFile(filePath);
        } else {
            System.out.println("不支持的文件格式");
            return null;
        }
    }

    // 读取 .doc 文件 (Word 2003 及更早版本)
    private static String readDocFile(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             HWPFDocument doc = new HWPFDocument(fis);
             WordExtractor extractor = new WordExtractor(doc)) {

            return extractor.getText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 读取 .docx 文件 (Word 2007 及更新版本)
    private static String readDocxFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(filePath);
             XWPFDocument doc = new XWPFDocument(fis)) {

            List<XWPFParagraph> paragraphs = doc.getParagraphs();
            for (XWPFParagraph para : paragraphs) {
                content.append(para.getText()).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    @Override
    public DocEntity saveDoc(byte[] file) throws Exception {
        String textContent = readWordFile("C:\\Users\\18133\\Desktop\\shuiyue.doc");
        if (textContent == null) {
            System.out.println("无法提取文本");
            return null;
        }
//        String filePath = "C:\\Users\\18133\\Desktop\\shuiyue.doc";  // 替换成你的 Word 文件路径
//        saveDocument(filePath);
        DocEntity docEntity = new DocEntity();
        docEntity.setFiles(file);
        docEntity.setTxt(textContent);
        docEntity.setStuId(2L);
        docEntity.setTeacherId(3L);
//        DocEntity docEntity = saveDocument(filePath);
        docDao.save(docEntity);
        return docEntity;
    }

    @Override
    public Resource getDocById(Integer id) throws Exception {
        return null;
    }

    @Override
    public void deleteDoc(Integer id) {
        docDao.deleteById(id);
    }
}