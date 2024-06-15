package com.iteva.yygh.cmn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-cmn")
public interface DictFeignClient {

    @GetMapping("/admin/cmn/dict/getName/{dictCode}/{value}")
    String getNameByDictCodeAndValue(@PathVariable("dictCode") String dictCode,
                                     @PathVariable("value") String value);

    @GetMapping("/admin/cmn/dict/getName/{value}")
    String getNameByValue(@PathVariable("value") String value);
}
