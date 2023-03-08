package com.yoga.system.controller;


import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yoga.common.result.PageResult;
import com.yoga.common.result.Result;
import com.yoga.system.entity.Place;
import com.yoga.system.query.PlacePageQuery;
import com.yoga.system.service.PlaceService;
import com.yoga.system.util.QiNiuUtil;
import com.yoga.system.vo.PlacePageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jisoo
 * @since 2022-03-05
 */
@Api(tags = "场馆接口")
@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceController {


    private final PlaceService placeService;

    @Value("${qiniu.domain-of-bucket}")
    private String domainOfBucket;

    @ApiOperation(value = "场馆列表")
    @GetMapping("/list")
    public Result getPlaceList() {
        List<Place> list = placeService.list();
        return Result.success(list);
    }

    @ApiOperation(value = "获取场馆数量")
    @GetMapping("/num")
    public Result getPlaceNum() {
        Integer num = placeService.getPlaceNum();
        return Result.success(num);
    }

    @ApiOperation(value = "场馆信息")
    @GetMapping("/{placeId}/detail")
    public Result getPlaceDetail(@ApiParam("场馆ID") @PathVariable Integer placeId) {
        Place place = placeService.getById(placeId);
        return Result.success(place);
    }

    @ApiOperation(value = "场馆分页列表")
    @GetMapping("/page")
    public PageResult<PlacePageVO> listPlacesWithPage(
            PlacePageQuery queryParams
    ) {
        IPage<PlacePageVO> result = placeService.listPlacesWithPage(queryParams);
        return PageResult.success(result);
    }

    @ApiOperation(value = "新增场馆")
    @PostMapping
    public Result addPlace(@RequestBody Place place) {
        place.setDeleted(0);
        boolean result = placeService.save(place);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改场馆")
    @PutMapping(value = "/{deleted}")
    public Result updatePlace(
            @ApiParam("用户ID") @PathVariable Integer deleted,
            @RequestBody Place place
    ) {
        boolean result = placeService.updatePlace(deleted,place);
        return Result.judge(result);
    }

    @ApiOperation(value = "场馆图片上传")
    @PostMapping(value="/uploadImg")
    public Result uploadImg(@RequestParam MultipartFile image, HttpServletRequest request) {
        Result result = new Result();
        String res = QiNiuUtil.uploadMultipartFile(image, null);
        if (res != null) {
            res = domainOfBucket + res;
            result.setData(res);
            result.setCode("00000");
            result.setMsg("图片上传成功");
        } else {
            result.setData(res);
            result.setCode("500");
            result.setMsg("图片上传失败");
        }
        return result;
    }




}

