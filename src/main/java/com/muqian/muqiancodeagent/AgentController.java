package com.muqian.muqiancodeagent;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author muqian
 * @since 2025/7/26 02:50
 */
@RestController
@RequestMapping("/manus")
public class AgentController {
    @Resource
    private MuManus muManus;

    @GetMapping("/ai")
    public String run(String id,String comment){
        return muManus.run(id,comment);
    }
}
