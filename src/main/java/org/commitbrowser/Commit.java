
package org.commitbrowser;

import java.util.Date;

/**
 *
 * @author Matti Tahvonen
 */
public class Commit {
    
    private String committer;
    private String email;
    private Date timestamp;
    private String message;
    private String id;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getCommitter() {
        return committer;
    }

    public void setCommitter(String committer) {
        this.committer = committer;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
}
