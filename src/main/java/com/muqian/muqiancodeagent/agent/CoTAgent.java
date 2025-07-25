package com.muqian.muqiancodeagent.agent;

import com.muqian.muqiancodeagent.prompt.CoTPrompt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * CoT模式agent（思维链模式）
 * @author muqian
 * @since 2025/7/26 00:12
 */
@Component
@Slf4j
public class CoTAgent  {

    private final ChatClient chatClient;

    private HashMap<String, List<Message>> hashMap = new HashMap<>();

    public CoTAgent(ChatClient.Builder builder){

        this.chatClient = builder
                .defaultOptions(ChatOptions.builder().temperature(0.1).build())
                .defaultSystem(CoTPrompt.COT_PROMPT)
                .build();
    }


    public String run(String id,String comment) {
        Prompt prompt = null;
        if (hashMap.get(id) == null){
            List<Message> list = new ArrayList<>(List.of(UserMessage.builder().text(comment).build()));
            hashMap.put(id,list);
            prompt = new Prompt(list);
        }
        else {
            List<Message> messageList = hashMap.get(id);
            messageList.add(UserMessage.builder().text(comment).build());
            prompt = new Prompt(messageList);
        }
        ChatResponse chatResponse = chatClient.prompt(prompt)
                .user(comment)
                .call()
                .chatResponse();
        String text = chatResponse.getResult().getOutput().getText();
        hashMap.get(id).add(chatResponse.getResult().getOutput());
        log.info("CoT agent返回的结果：{}",text);
        return text;
    }
}
