package Model.DataModels.Wizard;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompilePostCommand {

    @JsonProperty("command")
    private String command;

    public String getCommand() {
        return command;
    }

}
