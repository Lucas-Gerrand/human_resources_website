package hrrss.model;


public interface ISubAnswer {
	
	public Long getId();

    public void setId(Long id);

    public IQuastion getQuestion();

    public void setQuestion(IQuastion question);
    
    public String getSubAnswer();
    
    public void setSubAnswer(String subAnswer);
}
