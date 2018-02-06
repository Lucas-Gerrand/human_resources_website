package hrrss.model;

public interface ICVSkill {

    public Long getId();

    public void setId(Long id);

    public ICV getCv();

    public void setCv(ICV cv);

    public void setDescription(String description);

    public String getDescription();

    public String getSkillType();

    public void setSkillType(String skillType);
}
