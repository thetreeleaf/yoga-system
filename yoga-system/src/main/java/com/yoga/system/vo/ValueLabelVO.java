package com.yoga.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel("Select选择器默认Option属性")
@Data
@NoArgsConstructor
public class ValueLabelVO {

    public ValueLabelVO(Long value, String label) {
        this.value = value;
        this.label = label;
    }

    @ApiModelProperty("选项的值")
    private Long value;

    @ApiModelProperty("选项的标签，若不设置则默认与value相同")
    private String label;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<ValueLabelVO> children;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @ApiModelProperty("是否禁用该选项，默认false")
    public Boolean disabled;

}
