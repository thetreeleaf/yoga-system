package com.yoga.system.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoga.api.dto.AuthClientDTO;
import com.yoga.common.result.PageResult;
import com.yoga.common.result.Result;
import com.yoga.system.entity.SysOauthClient;
import com.yoga.system.service.SysOauthClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-26
 */
@Api(tags = "客户端接口")
@RestController
@RequestMapping("/sysOauthClient")
@Slf4j
@AllArgsConstructor
public class SysOauthClientController {

    private SysOauthClientService sysOauthClientService;

    @ApiOperation(value = "列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "页码", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageNum", value = "每页数量", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "clientId", value = "客户端ID", paramType = "query", dataType = "String")
    })
    @GetMapping
    public PageResult<SysOauthClient> list(long pageNum, long pageSize, String clientId) {
        IPage<SysOauthClient> result = sysOauthClientService.page(
                new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<SysOauthClient>()
                        .like(StrUtil.isNotBlank(clientId), SysOauthClient::getClientId, clientId));
        return PageResult.success(result);
    }

    @ApiOperation(value = "客户端详情")
    @ApiImplicitParam(name = "clientId", value = "客户端id", required = true, paramType = "path", dataType = "String")
    @GetMapping("/{clientId}")
    public Result detail(@PathVariable String clientId) {
        SysOauthClient client = sysOauthClientService.getById(clientId);

        return Result.success(client);
    }

    @ApiOperation(value = "新增客户端")
    @ApiImplicitParam(name = "client", value = "实体JSON对象", required = true, paramType = "body", dataType = "OauthClientDetails")
    @PostMapping
    public Result add(@RequestBody SysOauthClient client) {
        boolean status = sysOauthClientService.save(client);
        return Result.judge(status);
    }

    @ApiOperation(value = "修改客户端")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientId", value = "客户端id", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "client", value = "实体JSON对象", required = true, paramType = "body", dataType = "OauthClientDetails")
    })
    @PutMapping(value = "/{clientId}")
    public Result update(
            @PathVariable String clientId,
            @RequestBody SysOauthClient client) {
        boolean status = sysOauthClientService.updateById(client);
        return Result.judge(status);
    }

    @ApiOperation(value = "删除客户端")
    @ApiImplicitParam(name = "ids", value = "id集合,以,拼接字符串", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable("ids") String ids) {
        boolean status = sysOauthClientService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }


    @ApiOperation(hidden = true, value = "获取 OAuth2 客户端认证信息", notes = "Feign 调用")
    @PostMapping("/getOAuth2ClientById")
    public Result<AuthClientDTO> getOAuth2ClientById(@RequestParam String clientId) {
        SysOauthClient client = sysOauthClientService.getById(clientId);
        Assert.isTrue(client!=null, "OAuth2 客户端不存在");
        AuthClientDTO authClientDTO = new AuthClientDTO();
        BeanUtil.copyProperties(client, authClientDTO);
        return Result.success(authClientDTO);
    }

}

