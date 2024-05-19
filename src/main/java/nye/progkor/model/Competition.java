package nye.progkor.model;

import java.util.Date;
import java.util.List;

public class Competition {
    private String id;
    private Date date;
    private String location;
    private int maxPerformers;
    private List<Performer> registretedPerformers;

    public Competition(String id, Date date, String location, int maxPerformers, List<Performer> registretedPerformers) {
        this.id = id;
        this.date = date;
        this.location = location;
        this.maxPerformers = maxPerformers;
        this.registretedPerformers = registretedPerformers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMaxPerformers() {
        return maxPerformers;
    }

    public void setMaxPerformers(int maxPerformers) {
        this.maxPerformers = maxPerformers;
    }

    public List<Performer> getRegistretedPerformers() {
        return registretedPerformers;
    }

    public void setRegistretedPerformers(List<Performer> registretedPerformers) {
        this.registretedPerformers = registretedPerformers;
    }

    public boolean registerPerformer(Performer performer) {
        if (registretedPerformers.size() < maxPerformers && !registretedPerformers.stream()
                .anyMatch(c -> c.getMembershipID().compareTo(performer.getMembershipID()) == 0)) {
            registretedPerformers.add(performer);
            return true;
        } else {
            return false;
        }
    }

}

