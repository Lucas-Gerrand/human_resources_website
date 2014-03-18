package hrrss.ui.team;

import hrrss.ui.BasePage;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.ContextImage;

public class Team extends BasePage {
	private static final long serialVersionUID = 1L;

	public Team() {
		add(new Label("title", "Team"));
		add(new Label("content", "Click Match Hire team"));
		
		add(new ContextImage("roman", "/img/roman.jpg"));
		add(new ContextImage("irina", "/img/irina.jpg"));
		add(new ContextImage("lucas", "/img/lucas.jpg"));
		
	}
}
