package com.muqian.muqiancodeagent.agent;

import com.muqian.muqiancodeagent.prompt.CommonPrompt;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * 通用agent，根据用户输入选择合适模型
 * @author muqian
 * @since 2025/7/25 23:32
 */
@Component
@Slf4j
@Data
public class CommonAgent {

    private final ChatClient chatClient;




    public CommonAgent(ChatClient.Builder builder){

        this.chatClient = builder
                .defaultOptions(ChatOptions.builder().temperature(0.1).build())
                .defaultSystem(CommonPrompt.DECIDE_PROMPT)

                .build();
    }

    public String run(String id,String comment){

        ChatResponse chatResponse = chatClient.prompt()
                .user(comment)
                .call()
                .chatResponse();
        String text = chatResponse.getResult().getOutput().getText();
        log.info("路由agent给出模型选择：{}", text);
        return text;
    }
}
