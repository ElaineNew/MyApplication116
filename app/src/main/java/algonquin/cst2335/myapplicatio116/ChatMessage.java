package algonquin.cst2335.myapplicatio116;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {
    @ColumnInfo(name="message")
    private String message;
    @ColumnInfo(name="TimeSent")
    private String timeSent;
    @ColumnInfo(name="IsSentButton")
    private boolean isSentButton;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    ChatMessage(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }

    public boolean isSentButton() {
        return isSentButton;
    }

    public void setSentButton(boolean sentButton) {
        isSentButton = sentButton;
    }
}
