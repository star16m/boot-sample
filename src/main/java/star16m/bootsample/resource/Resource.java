package star16m.bootsample.resource;

public enum Resource {
    TEAM("팀"),
    PLAYER("선수")
    ;
    private String description;
    Resource(String description) {
        this.description = description;
    }
    public String getDescription() {
        return this.description;
    }
}
