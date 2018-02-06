package hrrss.model;

import java.util.Date;

public interface ICVEducation {

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

    public String getLocation();

    public void setLocation(String location);

    public String getFacility();

    public void setFacility(String facility);
}
