package app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Memo {
    private int id;
    private String title;
    private String content;

    public Memo() {
    }

    public Memo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @JsonIgnore
    public boolean isNew() {
        return this.id == 0;
    }
}
