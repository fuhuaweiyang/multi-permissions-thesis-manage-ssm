package com.rabbiter.ol.service.impl;

import com.rabbiter.ol.dao.DocDao;
import com.rabbiter.ol.entity.DocEntity;
import com.rabbiter.ol.service.DocService;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class DocServiceImpl implements DocService {

    @Autowired
    private DocDao docDao;


    @Override
    public DocEntity saveDoc(byte[] file, String title) throws Exception {
        String textContent = null;
        DocEntity docEntity = new DocEntity();
        try (InputStream is = new ByteArrayInputStream(file);
             PushbackInputStream pis = new PushbackInputStream(is, 8)) { // 使用固定长度 8 作为 PushbackInputStream 的缓冲区大小

            // 检测文件类型
            byte[] header = new byte[8];
            pis.read(header); // 读取文件头
            pis.unread(header); // 将文件头推回流中，以便后续读取

            FileMagic fm = FileMagic.valueOf(header);
            if (fm == FileMagic.OLE2) {
                // 处理 .doc 文件
                try (HWPFDocument document = new HWPFDocument(pis)) {
                    WordExtractor extractor = new WordExtractor(document);
                    textContent = extractor.getText();
                }
            } else if (fm == FileMagic.OOXML) {
                // 处理 .docx 文件
                try (XWPFDocument document = new XWPFDocument(pis)) {
                    textContent = extractTextFromDocx(document);
                }
            } else {
                throw new IllegalArgumentException("不支持的文件格式");
            }
        } catch (IOException e) {
            throw new Exception("读取文件失败", e);
        }

        if (textContent == null) {
            System.out.println("无法提取文本");
        }

        // 设置文档属性并保存
        docEntity.setFiles(file);
        docEntity.setTxt(textContent);
        docEntity.setStuId(2L);
        docEntity.setTeacherId(3L);
        docEntity.setTitle(title);
        docDao.save(docEntity);
        return docEntity;
    }

    // 提取 .docx 文件内容
    private String extractTextFromDocx(XWPFDocument document) {
        StringBuilder text = new StringBuilder();
        // 提取段落文本
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            text.append(paragraph.getText()).append("\n");
        }
        // 提取表格文本
        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    text.append(cell.getText()).append("\t");
                }
                text.append("\n");
            }
        }
        return text.toString();
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