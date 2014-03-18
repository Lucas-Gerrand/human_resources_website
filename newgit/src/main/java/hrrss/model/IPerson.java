package hrrss.model;

import java.util.Date;
import java.util.List;

public interface IPerson {

    public Long getId();

    public void setId(Long id);

    public String getLastName();

    public void setLastName(String lastName);

    public String getFirstName();

    public void setFirstName(String firstName);

    public IAddress getAddress();

    public void setAddress(IAddress address);

    public String getUsername();

    public void setUsername(String username);

    public String getPassword();

    public void setPassword(String password);

    public String getEmail();

    public void setEmail(String email);

    public List<IMediaFile> getMedias();

    public void setMedias(List<IMediaFile> medias);

    public List<IMessaging> getMessages();

    public void setMessages(List<IMessaging> messaging);
    
    public String getBirthday();

    public void setBirthday(String birthday);

    public String getGender();

    public void setGender(String gender);
    
    public String getActivation();

    public void setActivation(String gender);
    
    public byte[] getPic();
    
    public void setPic(byte[] pic);
    
}
