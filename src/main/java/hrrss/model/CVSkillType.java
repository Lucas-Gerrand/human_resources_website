package hrrss.model;

public enum CVSkillType {

    JOB_RELATED_SKILLS("Job related skill"), COMMUNICATION_SKILLS(
	    "Communication skill"), COMPUTER_SKILLS("Computer skill"), OTHER_SKILLS(
	    "Other skill"), DRIVING_LICENCE("Driving licence"), MANAGER_SKILLS(
	    "Manager skill");

    private String displayName;

    CVSkillType(String displayName) {
	this.displayName = displayName;
    }

    public String displayName() {
	return displayName;
    }

    @Override
    public String toString() {
	return displayName;
    }
}