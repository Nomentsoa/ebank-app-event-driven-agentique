package ca.lazanomentsoa.ebankaiagent.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Component;

@Component
public class EBankAiAgent {
    private final ChatClient chatClient;

    public EBankAiAgent(ChatClient.Builder chatClient, ToolCallbackProvider tools, ChatMemory memory) {
        this.chatClient = chatClient
                .defaultTools(tools)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build())// pour donner la memoire a l'agent
                .build();
    }


    public String chat(String query){
        return chatClient.prompt(query)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, "C1")) // conversation id personnel a chaque utilisateur
                .call()
                .content();
    }

}
