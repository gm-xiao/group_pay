package com.example.grouppay.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "响应")
public class ResponseBo<T> implements Serializable {

    private static final long serialVersionUID = 5344248086814045743L;

    @ApiModelProperty(value = "响应码", required = true)
    private Integer code;

    @ApiModelProperty(value = "消息", required = true)
    private String msg;

    @ApiModelProperty(value = "数据", required = true)
    private T data;

    public static ResponseBo parameterWrong(Object object){
        ResponseBo responseBo = new ResponseBo<>();
        responseBo.setCode(403);
        responseBo.setMsg("参数错误");
        responseBo.setData(object);
        return responseBo;
    }

    public static ResponseBo ok(Object object){
        ResponseBo responseBo = new ResponseBo<>();
        responseBo.setCode(200);
        responseBo.setMsg("请求成功");
        responseBo.setData(object);
        return responseBo;
    }

    public static ResponseBo error(Object object){
        ResponseBo responseBo = new ResponseBo<>();
        responseBo.setCode(500);
        responseBo.setMsg("系统错误");
        responseBo.setData(object);
        return responseBo;
    }


}
