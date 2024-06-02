package com.tony.fast.architecture.controller;

import com.tony.fast.architecture.model.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "主页")
@RestController
public class IndexController {

    @GetMapping({"/", "/info"})
    public R index() {
        return R.ok("fast-architecture");
    }

}