package com.rabbiter.ol.controller;

import com.rabbiter.ol.entity.MarkingEntity;
import com.rabbiter.ol.service.MarkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marking")
public class MarkingController {

    @Autowired
    private MarkingService markingService;

    /**
     * 保存批改信息
     */
    @PostMapping("/save")
    public ResponseEntity<String> saveMarking(@RequestBody MarkingEntity marking) {
        try {
            markingService.saveMarking(marking);
            return ResponseEntity.ok("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("保存失败");
        }
    }

    /**
     * 根据学生ID查询所有批改记录
     */
    @GetMapping("/student/{stuId}")
    public ResponseEntity<List<MarkingEntity>> getByStudentId(@PathVariable Long stuId) {
        try {
            List<MarkingEntity> list = markingService.getByStudentId(stuId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 根据批改ID查询单条记录
     */
    @GetMapping("/getByDocId/{docId}")
    public ResponseEntity<MarkingEntity> getByDocId(@PathVariable Long docId) {
        try {
            MarkingEntity result = markingService.getByDocId(docId);
            if (result != null) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 临时测试接口（可选，参考你的 temp）
     */
    @GetMapping("/temp")
    public ResponseEntity<String> temp() {
        try {
            MarkingEntity mock = new MarkingEntity();
            mock.setStuId(1L);
            mock.setDocId(1L);
            mock.setTeacherId(1L);
            mock.setMarkingWhat("内容批改");
            mock.setMarking("批改得不错");
            mock.setMarkingTime(new java.util.Date());
            markingService.saveMarking(mock);
            return ResponseEntity.ok("测试成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("测试失败");
        }
    }
}
