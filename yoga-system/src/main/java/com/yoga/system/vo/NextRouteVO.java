package com.yoga.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NextRouteVO {

    private String path;

    private String component;

    private String redirect;

    private String name;

    private Meta meta;

    @Data
    public static class Meta {

        private String title;

        private String icon;

        private Boolean hidden;

        /**
         * 如果设置为 true，菜单就算没有子节点也会显示
         */
        private Boolean alwaysShow;

        private List<String> roles;
    }

    private List<NextRouteVO> children;
}
