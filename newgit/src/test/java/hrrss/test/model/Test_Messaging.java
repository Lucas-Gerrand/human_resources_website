package hrrss.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import hrrss.model.IAddress;
import hrrss.model.IApplicant;
import hrrss.model.IEmployer;
import hrrss.model.IJobDescription;
import hrrss.model.IMessaging;
import hrrss.model.IPerson;
import hrrss.model.IReply;
import hrrss.model.impl.ModelFactory;
import hrrss.model.impl.Person;
import hrrss.service.impl.JobDescriptionService;
import hrrss.service.impl.MessagingService;
import hrrss.service.impl.PersonService;
import hrrss.service.impl.ReplyService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.PropertyValueException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class Test_Messaging{
    ModelFactory modelFactory = new ModelFactory();

    @Autowired
    PersonService personService;

    @Autowired
    MessagingService messagingService;
    
    @Autowired
    ReplyService replyService;
    
    @Test
    public void testMessaging() {

	IPerson person1 = (IPerson) personService
		.loadPersonByUsername("cv_test_user1");
	
	Long person1id = person1.getId();
	
	IPerson person2 = (IPerson) personService
			.loadPersonByUsername("testUser");
		
	Long person2id = person2.getId();
	
	
	IMessaging message1 = modelFactory.createMessage();
	IMessaging message2 = modelFactory.createMessage();
	/*
	 More tests...
	IMessaging message2 = modelFactory.createMessage();
	IMessaging message3 = modelFactory.createMessage();
	IMessaging message4 = modelFactory.createMessage();
	*/
	message1.setMessageTitle("title1");
	message1.setMessageBody("messageBody1");
	message1.setMessageSubject("subjectmessage1");
	message1.setReceived(false);
	message1.setSenderID(person1id);
	message1.setReceiverID(person2id);
	message1.setSent(true);
	message1.setPerson(person1);
	
	message2.setMessageTitle("title2");
	message2.setMessageBody("messageBody2");
	message2.setMessageSubject("subjectmessage2");
	message2.setReceived(false);
	message2.setSenderID(person1id);
	message2.setReceiverID(person2id);
	message2.setSent(true);
	message2.setPerson(person1);
	
	
	
	/*
	List<IMessaging>messageList = new ArrayList<IMessaging>();
	messageList.add(message1);
	person1.setMessages(messageList);
	*/
	try {
	    messagingService.save(message1);
	    messagingService.save(message2);
	    //fail("PropertyValueException expected - Employer null");
	} catch (PropertyValueException e) {
	    // expected
	}

	//message1.setPerson(person1);
	
	List<IMessaging> listMsg = new ArrayList<IMessaging>();
	listMsg.add(message1);
	
	@SuppressWarnings("unchecked")
	List<IMessaging> listJDloaded = (List<IMessaging>) messagingService
		.loadAllMessagesByPersonId(person1id);
	assertNotNull(listJDloaded);
	assertEquals(listJDloaded.size(), 4);
	
	List<IMessaging>inboxList = (List<IMessaging>)messagingService
			.loadAllInboxByPersonId(person2id);
	assertNotNull(inboxList);
	assertEquals(inboxList.size(), 2);
	
	/*
	Long checkNew = messagingService.countNewMessagesByPersonId(person2id);
	assertNotNull(inboxList);
	Long temp = null;
	temp = (long) 2; //Long.valueOf(2);
	assertEquals(checkNew, temp);
	*/
	
	/*
	listJDloaded = (List<IJobDescription>) jobDescriptionService
		.loadAllJobDescriptionByPerson("xxx");
	assertNotNull(listJDloaded);
	assertEquals(listJDloaded.size(), 0);
    
	*/
	IReply reply = modelFactory.createReply();
	
	reply.setReplyPersonId(person1id);
	IMessaging message123 = (IMessaging) reply;
	message123.setMessageBody("Message sodashihiihere");
	message123.setMessageTitle("tittttlkkookoke2");
	message123.setMessageBody("messaaaaeBody2");
	message123.setMessageSubject("subjectsdage2");
	message123.setReceived(false);
	message123.setSenderID(person1id);
	message123.setReceiverID(person2id);
	message123.setSent(true);
	message123.setPerson(person1);
	message123.setMessageId(Long.valueOf(1));
	//messagingService.update(message123);
	replyService.update((IReply) message123);
	replyService.delete(reply);
	
IReply reply1 = modelFactory.createReply();
	
	reply1.setReplyPersonId(person1id);
	IMessaging message1234 = (IMessaging) reply1;
	message1234.setMessageBody("Sushi house ahsda");
	message1234.setMessageTitle("Karaek2");
	message1234.setMessageBody("messaaadsdololol");
	message1234.setMessageSubject("dadadad");
	message1234.setReceived(false);
	message1234.setSenderID(person2id);
	message1234.setReceiverID(person2id);
	message1234.setSent(true);
	message1234.setPerson(person1);
	message1234.setMessageId(Long.valueOf(2));
	
	replyService.update((IReply) message1234);
	
	Long messageID = message1234.getMessageId();
	System.out.println("message id: " + messageID);
	replyService.delete(reply1);
	//List<IReply>replyList = replyService.loadAllRepliesByPersonId(messageID);
	//System.out.println("reply id: " + replyList.get(0).getReplyId());
	//assertEquals(replyList.size(), 1);
	
	
    }
    @Before
    public void setUp() {

	try {
		IPerson sender1 = modelFactory.createPerson();
		sender1.setEmail("cv_test1@email.at");
		sender1.setUsername("cv_test_user1");
		sender1.setActivation("done");
		sender1
		    .setPassword("7d5825412ad70930a249f706922146ba694b3448");
		sender1.setFirstName("Max1");
		sender1.setLastName("Mustermann1");
		
		IPerson tester1 = modelFactory.createPerson();
		tester1.setEmail("test@test.at");
		tester1.setUsername("testUser");
		tester1.setActivation("done");
		tester1
		    .setPassword("7d5825412ad922146ba694b3448");
		tester1.setFirstName("TestMan");
		tester1.setLastName("TestHey");
		
		IPerson sender2 = modelFactory.createPerson();
	    sender2.setEmail("cv_test2@email.at");
	    sender2.setUsername("cv_test_user2");
	    sender2.setActivation("done");
	    sender2
		    .setPassword("7d5825412ad7093012121a249f706922146ba694b3448");
	    sender2.setFirstName("Max2");
	    sender2.setLastName("Mustermann2");
	    
	    personService.save(sender1);
	    personService.save(sender2);
	    personService.save(tester1);
	    IMessaging msg1 = modelFactory.createMessage();
	    IMessaging msg2 = modelFactory.createMessage();
	    IMessaging msg3 = modelFactory.createMessage();
	    IMessaging msg4 = modelFactory.createMessage();
	    
	    msg1.setMessageBody("msg1 body");
	    msg1.setMessageSubject("helloooo subject");
	    msg1.setMessageTitle("title");
	    msg1.setReceived(true);
	    msg1.setSent(true);
	    msg1.setReceiverID(sender2.getId());
	    msg1.setReceiverID(sender1.getId());
	    msg1.setPerson(sender1);
	    
	    msg2.setMessageBody("msg2 body");
	    msg2.setMessageSubject("2 helloooo subject");
	    msg2.setMessageTitle("title 2");
	    msg2.setReceived(false);
	    msg2.setSent(true);
	    msg2.setReceiverID(sender2.getId());
	    msg2.setReceiverID(sender1.getId());
	    msg2.setPerson(sender2);
	    
	    msg3.setMessageBody("msg3 body");
	    msg3.setMessageSubject("helloooo  3 subject");
	    msg3.setMessageTitle("title3");
	    msg3.setReceived(true);
	    msg3.setSent(true);
	    msg3.setReceiverID(sender1.getId());
	    msg3.setReceiverID(sender2.getId());
	    msg3.setPerson(sender1);
	    
	    msg4.setMessageBody("msg4 body");
	    msg4.setMessageSubject("hellooo4 o subject");
	    msg4.setMessageTitle("titl4 e");
	    msg4.setReceived(true);
	    msg4.setSent(true);
	    msg4.setReceiverID(sender2.getId());
	    msg4.setReceiverID(sender1.getId());
	    msg4.setPerson(sender2);
	    
//	    List<IMessaging>messagingList1 = new ArrayList<IMessaging>();
//	    messagingList1.add(msg1);
//	    List<IMessaging>messagingList2 = new ArrayList<IMessaging>();
//	    messagingList2.add(msg2);
//	    
//	    messagingList1.add(msg3);
//	    messagingList2.add(msg4);
//	    
//	    sender1.setMessages(messagingList1);
//	    sender2.setMessages(messagingList2);
	    
	    
	    
	    messagingService.save(msg1);
	    messagingService.save(msg2);
	    messagingService.save(msg3);
	    messagingService.save(msg4);
	    
	} catch (Exception e) {
	    System.out.println(e);
	}
    }
}
