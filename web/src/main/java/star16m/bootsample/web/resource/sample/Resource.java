package star16m.bootsample.web.resource.sample;

public enum Resource {
    TEAM("팀"),
    PLAYER("선수"),
    SCHEDULE("스케줄")
    ;
    private String description;
    Resource(String description) {
        this.description = description;
    }
    public String getDescription() {
        return this.description;
    }
}
