package com.rabbiter.ol.controller;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rabbiter.ol.common.Result;
import com.rabbiter.ol.entity.VideosEntity;
import com.rabbiter.ol.service.VideosService;
import com.rabbiter.ol.tool.FileUtil;
import com.rabbiter.ol.tool.PathUtils;
import com.rabbiter.ol.vo.VideosVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;


/**
 * @author 
 * @email ${email}
 * @date 2024-02-12 00:24:20
 */
@RestController
@RequestMapping("study/videos")
public class VideosController {


    @Autowired
    private VideosService videosService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public Result list(@RequestBody VideosVo videosVo) {
        videosVo.setPage((videosVo.getPage() - 1) * videosVo.getPageSize());
        Map<String, Object> page = videosService.queryPage(videosVo);
        return Result.success(page);
    }

    /**
     * 列表
     */
    @RequestMapping("/selectByVideoTotalId")
    public Result selectByVideoTotalId(@RequestBody VideosVo videosVo) {
        QueryWrapper<VideosEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_total_id", videosVo.getVideoTotalId());
        queryWrapper.orderByAsc("sort");
        List<VideosEntity> list = videosService.list(queryWrapper);
        return Result.success(list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") Integer id) {
        VideosEntity videos = videosService.getById(id);

        return Result.success(videos);
    }

    /**
     * 保存
     */
    @CrossOrigin
    @RequestMapping("/save")
    public Result save(@RequestParam Integer sort,
                       @RequestParam String topic,
                       @RequestParam Integer videoTotalId,
                       MultipartFile file) throws UnknownHostException {
        VideosEntity videos = new VideosEntity();
        videos.setSort(sort);
        videos.setTopic(topic);
        videos.setVideoTotalId(videoTotalId);
        long randomNum = System.currentTimeMillis();
        videos.setVideoUrl("/file/videoFile/" + randomNum + file.getOriginalFilename());
        videos.setPath(PathUtils.getClassLoadRootPath() + "/file/videoFile/" + randomNum + file.getOriginalFilename());
        boolean save = videosService.save(videos);
        if (save) {
            try {
                FileUtil.uploadFile(file.getBytes(), PathUtils.getClassLoadRootPath() + "/file/videoFile/", randomNum + file.getOriginalFilename());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Result.successCode();
        }
        return Result.failureCode();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public Result update(@RequestBody VideosEntity videos) {
        boolean b = videosService.updateById(videos);
        if (b) {
            return Result.successCode();
        }
        return Result.failureCode();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public Result delete(@RequestBody VideosEntity videosEntity) {
//        VideosEntity byId = videosService.getById(videosEntity.getId());
//        boolean b = FileUtil.deleteFile(byId.getPath());
//        if (b) {
        boolean remove = videosService.removeById(videosEntity.getId());
        if (remove) {
            return Result.successCode();
        }
//        }
        return Result.failureCode();
    }

}
