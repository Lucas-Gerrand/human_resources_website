package hrrss.model;

import java.util.Date;

public interface ICVExperience {

    public Long getId();

    public void setId(Long id);

    public ICV getCv();

    public void setCv(ICV cv);

    public String getEnd();

    public void setEnd(String end);

    public String getStart();

    public void setStart(String start);

    public void setDescription(String descr);

    public String getDescription();

    public String getTitle();

    public void setTitle(String title);

    public String getCompany();

    public void setCompany(String company);
}
