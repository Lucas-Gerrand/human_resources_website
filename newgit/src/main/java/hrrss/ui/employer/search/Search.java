package hrrss.ui.employer.search;

import hrrss.model.ICV;
import hrrss.model.ICVEducation;
import hrrss.model.ICVExperience;
import hrrss.model.ICVSkill;
import hrrss.model.IJobDescription;
import hrrss.model.IPerson;
import hrrss.model.impl.Applicant;
import hrrss.model.impl.Person;
import hrrss.service.impl.CVService;
import hrrss.service.impl.JobDescriptionService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;
import hrrss.ui.util.CopyOfDocumentSimilarity;
import hrrss.ui.util.RoundUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wickedcharts.highcharts.options.Axis;
import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.DataLabels;
import com.googlecode.wickedcharts.highcharts.options.Global;
import com.googlecode.wickedcharts.highcharts.options.HorizontalAlignment;
import com.googlecode.wickedcharts.highcharts.options.Legend;
import com.googlecode.wickedcharts.highcharts.options.LegendLayout;
import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.highcharts.options.PlotOptions;
import com.googlecode.wickedcharts.highcharts.options.PlotOptionsChoice;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.VerticalAlignment;
import com.googlecode.wickedcharts.highcharts.options.color.HexColor;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;
import com.googlecode.wickedcharts.wicket6.highcharts.Chart;

public class Search extends BasePage {
	@SpringBean
	PersonService ps;

	@SpringBean
	JobDescriptionService js;

	@SpringBean
	CVService cvs;

	final Logger logger = LoggerFactory.getLogger(Search.class);
	private static final long serialVersionUID = 1L;

	public Search() {
		int show = 0;

		if (getSession().getAttribute("isLogin").equals("false")) {
			setResponsePage(Authentication.class);
			return;
		}
		if (getSession().getAttribute("personTyp").equals("a")) {
			setResponsePage(PersonTyp.class);
			return;
		}

		add(new Label("title", "Find applicants"));

		String url = RequestCycle.get().getRequest().getUrl().toString();
		String[] idFromUrl = url.split("/");

		if (idFromUrl.length == 3 || idFromUrl.length == 4) {
			if (idFromUrl.length == 3) {
				show = 5;
			}
			if (idFromUrl.length == 4) {
				try {
					show = Integer.parseInt(idFromUrl[3]);
				} catch (Exception e) {
					show = 5;
				}
			}

		} else {
			Long id = Long.valueOf(getSession().getAttribute("id").toString());
			throw new RedirectToUrlException("/employer/profile/" + id);

		}

		Integer findId;

		try {
			String out = "";

			findId = Integer.parseInt(idFromUrl[2]);

			Long id = Long.valueOf(getSession().getAttribute("id").toString());

			List<IJobDescription> list = new ArrayList<IJobDescription>();

			list = js.loadAllJobDescriptionByEmployerId(id);

			IJobDescription jsSearch = list.get(findId);
			ArrayList<String> a = new ArrayList<String>();
			String jobTitle = "[Title: ";
			String jobContent = "[Content: ";
			String jobQualifications = "[Qualifications: ";
			String total = "";
			total += jobTitle + jsSearch.getJobTitle() + "]" + jobContent
					+ jsSearch.getMainPurpose() + "]" + jobQualifications
					+ jsSearch.getQualification() + "]";

			// a.add(jsSearch.getAll());
			a.add(total);

			HashMap<Long, String> cvmap = getAllCVByAppID(findId, id);

			for (Map.Entry e : cvmap.entrySet()) {
				if (!e.getValue().toString().startsWith(" ")) {
					a.add(e.getKey().toString() + "-" + e.getValue().toString());
					logger.info(e.getKey().toString() + "-"
							+ e.getValue().toString());

				}
			}

			Map<Double, String> treeMap = new TreeMap<Double, String>(
					Collections.reverseOrder());
			treeMap.putAll(new CopyOfDocumentSimilarity().mainComponent(a,
					false));

			int topNumber = show;
			int count = 0;
			logger.info("Search for best matching documents");
			String shortDesc, shortDesc2, shortDesc3;
			// Map<String, Double>peopleScores=new HashMap<String, Double>();
			List<Number> scores = new ArrayList<Number>();
			List<String> people = new ArrayList<String>();

			Form form = new Form("questionForm");
			int realAppCount = countFoundApplicants(treeMap);

			// System.out.println("REAL APP COUNT "+realAppCount);

			for (Map.Entry entry : treeMap.entrySet()) {
				if (count < topNumber) {

					double i = (Double) (entry.getKey());
					if (i <= 0.95 && i > 0.05) {
						String[] one = entry.getValue().toString().split("-");
						logger.info("processing the applicant id: " + one[0]);
						final Applicant p = (Applicant) ps.find(Long
								.valueOf(one[0]));
						if (p.getStatus().equals("no")) {
							continue;
						}

						List<ICV> cvList = cvs.loadCVById(Long.valueOf(one[0]));
						List<ICVEducation> education = null;
						List<ICVExperience> experience = null;
						List<ICVSkill> skill = null;

						String str = "-";
						String skillstr = "-";
						String experiencestr = "-";
						try {
							education = cvList.get(0).getEducations();
							str += education.get(0).getLocation() + " "
									+ education.get(0).getDescription();

							experience = cvList.get(0).getExperiences();
							experiencestr += "Company: "
									+ experience.get(0).getCompany() + " "
									+ experience.get(0).getDescription();

							skill = cvList.get(0).getSkills();
							skillstr += skill.get(0).getSkillType() + " "
									+ skill.get(0).getDescription();

						} catch (Exception e) {
							str = "-";
							skillstr = "-";
							experiencestr = "-";
							education = null;
							experience = null;
							skill = null;
						}

						if (education != null && experience != null
								&& skill != null) {

							shortDesc = shortenString(str);
							shortDesc2 = shortenString(skillstr);
							shortDesc3 = shortenString(experiencestr);
							out += "<tr><td colspan=\"2\"><b>"
									+ (count + 1)
									+ ". Result &#8211; <a href=\"/applicant/profile/"
									+ one[0] + "\">" + p.getFirstName() + " "
									+ p.getLastName() + "</a></td>";
							out += "<td>" + "<a href=\"/message/messages/" + id
									+ "/" + one[0] + "\">Send Message:"
									+ "</a></td>";
							out += "<td>" + "<a href=\"/message/questionnaire/"
									+ id + "/" + one[0] + "\">Questionnaire"
									+ "</a></td>";
							out += "<td>" + "<a href=\"/message/hire/" + id
									+ "/" + one[0] + "/" + findId + "\">Hire!"
									+ "</a></td>";
							out += "<td>" + "<a href=\"/message/saveapplicant/"
									+ id + "/" + one[0] + "/" + findId
									+ "\">Add to basket" + "</a></td></tr>";
							// message/saveapplicant
							out += "<tr><td>" + shortDesc + "</td></tr>";
							out += "<tr><td>" + shortDesc2 + "</td></tr>";
							out += "<tr><td>" + shortDesc3 + "</td></tr>";
							out += "<tr><td >Matching to "
									+ (RoundUtil.round2(i * 100))
									+ "%</td></tr>";
							out += "<tr><td height=\"20\"></td></tr>";

							logger.info("RESULT LIST " + treeMap.size());
							logger.info("Key: " + i + " Value= "
									+ entry.getValue());
							logger.info("Key: " + entry.getKey());
							// peopleScores.put(entry.getValue().toString(),
							// RoundUtil.round3(i)*100);
							scores.add(RoundUtil.round2(i * 100));
							// people.add(p.getFirstName() + " " +
							// p.getLastName());
							people.add("<a href=\"/applicant/profile/" + one[0]
									+ "\">" + p.getFirstName() + " "
									+ p.getLastName() + "</a>");

							count++;
						}

					}

				}
			}
			if (out.equals("")) {
				add(new Label("content", "No results found")
						.setEscapeModelStrings(false));
				add(new Label("chart", ""));
			} else {

				Options options = new Options();
				options.setTitle(new Title(
						"Automatic Matching: Best applicants"));
				options.setChartOptions(new ChartOptions()
						.setType(SeriesType.BAR));
				options.setGlobal(new Global().setUseUTC(Boolean.TRUE));
				options.setSubtitle(new Title("Click Match Hire"));
				options.setxAxis(new Axis().setCategories(people));
				options.setyAxis(new Axis().setTitle(new Title("Percentages")));
				options.setPlotOptions(new PlotOptionsChoice()
						.setBar(new PlotOptions()
								.setDataLabels(new DataLabels()
										.setEnabled(Boolean.TRUE))));
				options.setLegend(new Legend().setLayout(LegendLayout.VERTICAL)
						.setAlign(HorizontalAlignment.RIGHT)
						.setVerticalAlign(VerticalAlignment.TOP).setX(-100)
						.setY(100).setFloating(Boolean.TRUE).setBorderWidth(1)
						.setBackgroundColor(new HexColor("#ffffff"))
						.setShadow(Boolean.TRUE));

				options.addSeries(new SimpleSeries().setName("People").setData(
						scores));
				// .setData(person1, person2, person3, person4, person5));
			
				if(show >= 25) {
        			add(new Chart("chart", options).setVisible(false));
        		} else {
        			add(new Chart("chart", options));
        		}
				if (show < realAppCount) {
					out += "<tr><td colspan=\"6\" align=\"right\"><a href=\"/employer/search/"
							+ findId
							+ "/"
							+ (show + 5)
							+ "/\">Show More</a></td></tr>";
				}
				add(new Label("content", out).setEscapeModelStrings(false));

				// js.delete(jsDel);

				// setResponsePage(EditJob.class);
			}
		} catch (Exception e) {
			e.printStackTrace();

			Long id = Long.valueOf(getSession().getAttribute("id").toString());
			throw new RedirectToUrlException("/employer/profile/" + id);
		}

	}

	private int countFoundApplicants(Map<Double, String> treeMap) {
		// TODO Auto-generated method stub
		int count = 0;
		for (Map.Entry entry : treeMap.entrySet()) {
			double i = (Double) (entry.getKey());

			if (i <= 0.95 && i > 0.05) {
				String[] one = entry.getValue().toString().split("-");
				logger.info("processing the applicant id: " + one[0]);
				final Applicant p = (Applicant) ps.find(Long.valueOf(one[0]));
				if (p.getStatus().equals("no")) {
					continue;
				}

				List<ICV> cvList = cvs.loadCVById(Long.valueOf(one[0]));
				List<ICVEducation> education = null;
				List<ICVExperience> experience = null;
				List<ICVSkill> skill = null;

				String str = "-";
				String skillstr = "-";
				String experiencestr = "-";
				try {
					education = cvList.get(0).getEducations();
					str += education.get(0).getLocation() + " "
							+ education.get(0).getDescription();

					experience = cvList.get(0).getExperiences();
					experiencestr += "Company: "
							+ experience.get(0).getCompany() + " "
							+ experience.get(0).getDescription();

					skill = cvList.get(0).getSkills();
					skillstr += skill.get(0).getSkillType() + " "
							+ skill.get(0).getDescription();

				} catch (Exception e) {
					str = "-";
					skillstr = "-";
					experiencestr = "-";
					education = null;
					experience = null;
					skill = null;
				}

				if (education != null && experience != null && skill != null) {

					count++;
				}

			}
		}

		return count;

	}

	public HashMap<Long, String> getAllCVByAppID(Integer findId, long id) {

		List<ICV> allCVList = cvs.findAllCV();

		String all = "";
		HashMap<Long, String> allCVbyAppId = new HashMap<Long, String>();
		
		
		
		for (ICV cv : allCVList) {
			
			String eduForUser = "";
			String expTitleForUser = "";
			String expForUser = "";
			String skillsForUser = "";
			
			Person cvPerson = (Person) cv.getApplicant();
			if(cvPerson.getAddress()!= null ){
				if(cvPerson.getAddress().getCity()!= null && cvPerson.getAddress().getCity()!= ""){
					expForUser += cvPerson.getAddress().getCity();
				}		
			}
			
			
			

			List<ICVEducation> educationList = cv.getEducations();
			for (ICVEducation edu : educationList) {
				eduForUser += edu.getDescription() + " ";
				expForUser += edu.getLocation();
			}

			List<ICVExperience> experiencesList = cv.getExperiences();
			for (ICVExperience exp : experiencesList) {
				expTitleForUser += exp.getTitle() + " ";
				expForUser += exp.getDescription() + " ";
			}

			List<ICVSkill> skillsList = cv.getSkills();
			for (ICVSkill skill : skillsList) {
				skillsForUser += skill.getDescription() + " ";

			}

			expForUser += cv.getNationality();

			Applicant appli = (Applicant) cv.getApplicant();
			String personWrap = "[Person: ";
			String titleWrap = "[Title: ";
			String contentWrap = "[Content: ";
			String QualificationsWrap = "[Qualifications: ";
			all += personWrap + "] " + titleWrap + expTitleForUser + " a]"
					+ contentWrap + expForUser + " " + skillsForUser + " "
					+ eduForUser + " b]" + QualificationsWrap + eduForUser
					+ " c]";

			if (allCVbyAppId.containsKey(appli.getId())) {
				String old = allCVbyAppId.get(appli.getId()).toString();
				allCVbyAppId.put(appli.getId(), old + " " + all);
			} else {
				allCVbyAppId.put(appli.getId(), all);

			}

			all = "";

		}
		return allCVbyAppId;

	}

	public String shortenString(String str) {
		String shortDesc;
		if (str.length() > 150) {
			shortDesc = str.substring(0, 150);
			shortDesc += "...";

		} else {
			shortDesc = str;
		}
		return shortDesc;
	}
}