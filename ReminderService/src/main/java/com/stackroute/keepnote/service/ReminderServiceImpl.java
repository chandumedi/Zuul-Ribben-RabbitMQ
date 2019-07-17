package com.stackroute.keepnote.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.ReminderNotCreatedException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;
import com.stackroute.keepnote.repository.ReminderRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class ReminderServiceImpl implements ReminderService {

	/*
	 * Autowiring should be implemented for the ReminderRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	@Autowired
	private ReminderRepository reminderRepo;

	public ReminderServiceImpl(ReminderRepository reminderRepo) {
		this.reminderRepo = reminderRepo;
	}

	/*
	 * This method should be used to save a new reminder.Call the corresponding
	 * method of Respository interface.
	 */
	public Reminder createReminder(Reminder reminder) throws ReminderNotCreatedException {
		try {
			reminder.setReminderCreationDate(new Date());
			/*Reminder existingReminder = getReminderById(reminder.getReminderId());
			if (existingReminder != null) {
				throw new ReminderNotCreatedException("ReminderNotCreatedException");
			}*/
			Reminder insertStatus=reminderRepo.insert(reminder);
			if(insertStatus==null) throw new ReminderNotCreatedException("ReminderNotCreatedException");
		} catch (Exception e) {
			
		}
		return reminder;
	}

	/*
	 * This method should be used to delete an existing reminder.Call the
	 * corresponding method of Respository interface.
	 */
	public boolean deleteReminder(String reminderId) throws ReminderNotFoundException {
		Reminder reminder = getReminderById(reminderId);
		if (reminder == null) {
			throw new ReminderNotFoundException("throws ReminderNotFoundException");
		}
		reminderRepo.delete(reminder);
		return true;
	}

	/*
	 * This method should be used to update a existing reminder.Call the
	 * corresponding method of Respository interface.
	 */
	public Reminder updateReminder(Reminder reminder, String reminderId) throws ReminderNotFoundException {
		Reminder existingReminder=getReminderById(reminderId);
		if (existingReminder == null) {
			throw new ReminderNotFoundException("throws ReminderNotFoundException");
		}
		reminder.setReminderCreatedBy(reminder.getReminderCreatedBy());
		reminder.setReminderCreationDate(reminder.getReminderCreationDate());
		reminderRepo.delete(existingReminder);
		reminderRepo.insert(reminder);
		return reminder;
	}

	/*
	 * This method should be used to get a reminder by reminderId.Call the
	 * corresponding method of Respository interface.
	 */
	public Reminder getReminderById(String reminderId) throws ReminderNotFoundException {
		try {
			Optional<Reminder> reminder=reminderRepo.findById(reminderId);
			if(!reminder.isPresent()) { return null; }
			return reminder.get();
		} catch (Exception e) {
			throw new ReminderNotFoundException("ReminderNotFoundException");
		}

	}

	/*
	 * This method should be used to get all reminders. Call the corresponding
	 * method of Respository interface.
	 */

	public List<Reminder> getAllReminders() {

		return reminderRepo.findAll();
	}

}
