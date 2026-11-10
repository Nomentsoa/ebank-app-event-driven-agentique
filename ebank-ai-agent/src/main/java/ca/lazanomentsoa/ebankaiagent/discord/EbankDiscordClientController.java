package ca.lazanomentsoa.ebankaiagent.discord;


import ca.lazanomentsoa.ebankaiagent.agents.EBankAiAgent;
import com.zgamelogic.discord.annotations.DiscordController;
import com.zgamelogic.discord.annotations.DiscordMapping;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordController
public class EbankDiscordClientController {

    private final EBankAiAgent eBankAiAgent;


    public EbankDiscordClientController(EBankAiAgent eBankAiAgent) {
        this.eBankAiAgent = eBankAiAgent;
    }

    @DiscordMapping
    private void perform(MessageReceivedEvent event){
        // si le message vient du bot lui meme, car il envoye des messages a lui meme
        if(event.getAuthor().isBot()) return;

        String query = event.getMessage().getContentRaw();
        String response = eBankAiAgent.chat(query);
        event.getChannel().sendMessage(response).queue();

    }
}
