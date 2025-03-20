package com.rabbiter.ol.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import com.rabbiter.ol.common.Result;
import com.rabbiter.ol.entity.SubjectEntity;
import com.rabbiter.ol.service.SubjectService;
import com.rabbiter.ol.vo.SubjectVo;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.rabbiter.ol.tool.diff_match_patch;






import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.*;
import java.sql.*;
import java.util.List;

/**
 * 
 *
 * @author 
 * @email ${email}
 * @date 2024-02-15 21:39:15
 */
@RestController
@RequestMapping("study/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public Result list(@RequestBody SubjectVo subjectVo){
        subjectVo.setPage((subjectVo.getPage() - 1) * subjectVo.getPageSize());
        Map<String, Object> page = subjectService.queryPage(subjectVo);
        return Result.success(page);
    }

    /**
     * 列表
     */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody SubjectVo subjectVo){
        subjectVo.setPage((subjectVo.getPage() - 1) * subjectVo.getPageSize());
        Map<String, Object> page = subjectService.findPage(subjectVo);
        return Result.success(page);
    }

    /**
     * 列表
     */
    @RequestMapping("/queryList")
    public Result queryList(@RequestBody SubjectVo SubjectVo) {
        List<HashMap> page = subjectService.queryList(SubjectVo);
        return Result.success(page);
    }

    /**
     * 列表
     */
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

    @GetMapping("/findList")
    public String findList() {
//        List<SubjectEntity> list = subjectService.list();
//        try (FileInputStream fis = new FileInputStream("C:\\Users\\18133\\Desktop\\shuiyue.doc");
//             HWPFDocument doc = new HWPFDocument(fis);
//             WordExtractor extractor = new WordExtractor(doc)) {
//             return extractor.getText();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
        return restoredTextB;
    }


    public static void saveDocument(String filePath) {
        String textContent = readWordFile(filePath);
        if (textContent == null) {
            System.out.println("无法提取文本");
            return;
        }

        String sql = "INSERT INTO doc (id, word) VALUES (?, ?)";

        String URL = "jdbc:mysql://localhost:3306/online_learn?&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8";
        String USER = "root";
        String PASSWORD = "Gy3503556";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             FileInputStream fis = new FileInputStream(filePath)) {

            pstmt.setInt(1, 1);
            pstmt.setBinaryStream(2, fis, (int) new File(filePath).length());

            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0 ? "文件成功存入数据库" : "存入失败");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
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
    @GetMapping("/saveDoc")
    public String findList2() {
        String filePath = "C:\\Users\\18133\\Desktop\\shuiyue.doc";  // 替换成你的 Word 文件路径
        saveDocument(filePath);
        return "fail";
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") Integer id){
		SubjectEntity subject = subjectService.getById(id);
        return Result.success(subject);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public Result save(@RequestBody SubjectEntity subject){
        boolean save = subjectService.save(subject);
        if (save) {
            return Result.successCode();
        }
        return Result.failureCode();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public Result update(@RequestBody SubjectEntity subject){
        boolean update = subjectService.updateById(subject);
        if (update) {
            return Result.successCode();
        }
        return Result.failureCode();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public Result delete(@RequestBody SubjectEntity subjectEntity){
        boolean b = subjectService.removeById(subjectEntity.getId());
        if (b) {
            return Result.successCode();
        }
        return Result.failureCode();
    }

}
