package ca.lazanomentsoa.ebankaiagent.controller;

import ca.lazanomentsoa.ebankaiagent.agents.EBankAiAgent;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbankAgentRestController {
    private final EBankAiAgent ebankAiAgent;


    public EbankAgentRestController(EBankAiAgent ebankAiAgent) {
        this.ebankAiAgent = ebankAiAgent;
    }


    @GetMapping(value = "/chat", produces = MediaType.TEXT_PLAIN_VALUE)
    public String chat(String query){
        return ebankAiAgent.chat(query);
    }
}
