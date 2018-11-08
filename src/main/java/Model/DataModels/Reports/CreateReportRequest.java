
package Model.DataModels.Reports;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class CreateReportRequest {
    @JsonProperty("todo")
    private String todo;


    public String getTodo() {
        return todo;
    }

    @JsonIgnore
    public void setTodo(String todo) {
        this.todo = todo;
    }
}
