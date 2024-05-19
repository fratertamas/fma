package nye.progkor.model;

import java.util.Objects;

public class Performer {

    private String membershipID;
    private String name;
    private PerformerCategory performerCategory;

    public Performer(String membershipID, String name, PerformerCategory performerCategory) {
        this.membershipID = membershipID;
        this.name = name;
        this.performerCategory = performerCategory;
    }

    public String getMembershipID() {
        return membershipID;
    }

    public void setMembershipID(String membershipID) {
        this.membershipID = membershipID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PerformerCategory getPerformerCategory() {
        return performerCategory;
    }

    public void setPerformerCategory(PerformerCategory performerCategory) {
        this.performerCategory = performerCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Performer performer = (Performer) o;
        return Objects.equals(membershipID, performer.membershipID) && Objects.equals(name, performer.name)
                && performerCategory == performer.performerCategory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(membershipID, name, performerCategory);
    }
}
