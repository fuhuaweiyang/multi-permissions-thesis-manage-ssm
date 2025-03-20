package com.rabbiter.ol.service.impl;

import com.rabbiter.ol.dao.DocDao;
import com.rabbiter.ol.dao.ModificationDao;
import com.rabbiter.ol.entity.DocEntity;
import com.rabbiter.ol.entity.ModificationEntity;
import com.rabbiter.ol.service.DocService;
import com.rabbiter.ol.tool.diff_match_patch;
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
import java.util.LinkedList;
import java.util.List;

@Service
public class DocServiceImpl implements DocService {

    @Autowired
    private DocDao docDao;

    @Autowired
    private ModificationDao modificationDao;

    public static LinkedList<diff_match_patch.Diff> generateDiff(String textA, String textB) {
        diff_match_patch dmp = new diff_match_patch();
        return dmp.diff_main(textA, textB);
    }

    public static String applyPatch(String textA, String patchText) {
        diff_match_patch dmp = new diff_match_patch();
        LinkedList<diff_match_patch.Patch> patches = new LinkedList<>(dmp.patch_fromText(patchText));
        Object[] result = dmp.patch_apply(patches, textA);
        return (String) result[0];
    }

    public DocEntity getDocByModificationId(Integer id) {
        String textA = "数据库含有以下几张表：用户表、学生-导师表、论文表、论文修改表。";
        String textB = "数据库含有以下几张表：学生-导师表、用户表、论文表、论文修改表。";

        diff_match_patch dmp = new diff_match_patch();
        LinkedList<diff_match_patch.Diff> diffs = generateDiff(textA, textB);
        System.out.println("Generated Diff:");
        for (diff_match_patch.Diff diff : diffs) {
            System.out.println(diff.operation + ": " + diff.text);
        }

        List<diff_match_patch.Patch> patches = dmp.patch_make(diffs);
        String patchText = dmp.patch_toText(patches);
        System.out.println("\nGenerated Patch:");
        System.out.println(patchText);

        String restoredTextB = applyPatch(textA, patchText);
        System.out.println("\nRestored Text B:");
        System.out.println(restoredTextB);
        return null;
    }
    @Override
    public DocEntity saveDoc(byte[] file, String title, Long stuId) throws Exception {
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
        docEntity.setStuId(stuId);
        docEntity.setTeacherId(3L);
        docEntity.setTitle(title);
        System.out.println("////////////////////////////////////////////////////////////");
        DocEntity docEntity1 = docDao.findByStuId(stuId) != null ? docDao.findByStuId(stuId) : new DocEntity();
        if(docEntity1.isEmpty()){
            docDao.save(docEntity);
        }else {
            docDao.update(docEntity);
            Long docId = docEntity1.getId();
            diff_match_patch dmp = new diff_match_patch();
            LinkedList<diff_match_patch.Diff> diffs = generateDiff(textContent,docEntity1.getTxt());
            List<diff_match_patch.Patch> patches = dmp.patch_make(diffs);
            String patchText = dmp.patch_toText(patches);
            ModificationEntity modificationEntity = new ModificationEntity();
            modificationEntity.setModification(patchText);
            modificationEntity.setDocId(docId);
            modificationEntity.setStuId(stuId);
            modificationDao.save(modificationEntity);
//            String restoredTextB = applyPatch(docEntityOld.getTxt(), patchText);
//            System.out.println("\nRestored Text B:");
//            System.out.println(restoredTextB);
        }

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