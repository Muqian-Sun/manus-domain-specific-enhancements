package com.muqian.muqiancodeagent;

import com.muqian.muqiancodeagent.agent.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author muqian
 * @since 2025/7/25 23:15
 */
@Service
public class MuManus {
    @Resource
    private CommonAgent commonAgent;
    @Resource
    private CodeAgent codeAgent;
    @Resource
    private CoTAgent coTAgent;
    @Resource
    private DesignAgent designAgent;
    @Resource
    private FinanceAgent financeAgent;
    @Resource
    private MedialAgent medialAgent;
    @Resource
    private ReActAgent reActAgent;

    public String run(String id,String comment){
        String string = commonAgent.run(id,comment);
        return switch (string) {
            case "CoT" -> coTAgent.run(id,comment);
            case "ReAct" -> reActAgent.run(id,comment);
            default -> null;
        };
    }

}
