import java.util.*;

class Complaint {
    private String description;
    private String status;
    private final Date complaintDate;

    public Complaint(String description) {
        this.description = description;
        this.status = "Pending";
        this.complaintDate = new Date();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getComplaintDate() {
        return complaintDate;
    }

    @Override
    public String toString() {
        return "Your complaint about " + description + " on " + complaintDate + " is held for review. You will get notified once your complaint gets resolved.";
    }
}
