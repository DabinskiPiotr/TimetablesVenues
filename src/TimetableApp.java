import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Collections;

/**
 * The timetable system
 *
 * @author Piotr Dabiński
 * @version 1 (02.04.2020)
 */
public class TimetableApp {

    private String filename;
    private Scanner scan;
    private Timetable timetable;

    private TimetableApp() {
        scan = new Scanner(System.in);
        System.out.print("Please enter the filename of timetable information: ");
        filename = scan.next();

        timetable = new Timetable();
    }

    /*
     * initialise() method runs from the main and reads from a file
     */
    private void initialise() {
        System.out.println("Using file " + filename);

        try {
            timetable.load(filename);
        } catch (FileNotFoundException e) {
            System.err.println("The file: " + " does not exist. Assuming first use and an empty file." +
                    " If this is not the first use then have you accidentally deleted the file?");
        } catch (IOException e) {
            System.err.println("An unexpected error occurred when trying to open the file " + filename);
            System.err.println(e.getMessage());
        }
    }

    /*
     * runMenu() method runs from the main and allows entry of data etc
     */
    private void runMenu() {
        String response;
        do {
            printMenu();
            System.out.println("What would you like to do:");
            scan = new Scanner(System.in);
            response = scan.nextLine().toUpperCase();
            switch (response) {
                case "1":
                    addMeeting();
                    break;
                case "2":
                    addLecture();
                    break;
                case "3":
                    searchForMeeting();
                    break;
                case "4":
                    removeMeeting();
                    break;
                case "5":
                    addVenue();
                    break;
                case "6":
                    printAll();
                    break;
                case "Q":
                    break;
                default:
                    System.out.println("Try again");
            }
        } while (!(response.equals("Q")));
    }

    private void printMenu() {
        System.out.println("1 -  add a new Meeting slot");
        System.out.println("2 -  add a new Lecture slot");
        System.out.println("3 -  search for a booked timetable meeting");
        System.out.println("4 -  remove a timetable meeting");
        System.out.println("5 -  add a venue");
        System.out.println("6 -  display everything");
        System.out.println("q -  Quit");
    }
    private void addLecture() {
        System.out.println("Enter module code: ");
        String moduleCode = scan.nextLine();

        System.out.println("Enter Lecturer's name: ");
        String lecturerName = scan.nextLine();

        System.out.println("Enter Lecturer's phone number: ");
        String number = scan.nextLine();

        System.out.println("Does it need the adjustable seating? (Y/N)");
        String yn = scan.next();

        boolean x = false;
        if(yn.equals('y') || yn.equals('Y')) {
            x = true;
        }


        Lecture lecture = new Lecture();
        lecture.setModuleCode(moduleCode);
        lecture.setSeatingAdjustment(x);
        populateAndAddToTimetable(lecture);

    }

    private void addVenue() {
        Venue venue;
        String venueName;

        while (true) {
            System.out.println("Enter the venue name");
            venueName = scan.nextLine();
            venue = timetable.searchForVenue(venueName);
            if (venue != null) {
                System.out.println("This venue already exists. Give it a different name");
            } else {
                break;
            }
        }

        System.out.println("Does it have a data projector?(Y/N)");
        String answer = scan.nextLine().toUpperCase();
        boolean hasDataProjector = answer.equals("Y");

        venue = new Venue(venueName);
        venue.setHasDataProjector(hasDataProjector);
        timetable.add(venue);
    }

    private void addMeeting() {
        System.out.println("Enter meeting name: ");
        String meetingName = scan.nextLine();

        System.out.println("Enter organiser name: ");
        String organiser = scan.nextLine();

        MeetingType meetingType = getMeetingType();

        Meeting meeting = new Meeting();

        meeting.setMeetingName(meetingName);
        meeting.setOrganiser(organiser);
        meeting.setMeetingType(meetingType);
        populateAndAddToTimetable(meeting);

    }

    private void searchForMeeting() {
        System.out.println("Enter unique meeting ID?");
        int meetingId = scan.nextInt(); // STUDENTS MUST HANDLE ILLEGAL INPUT
        scan.nextLine();

        Meeting meeting = timetable.searchForMeeting(meetingId);
        if (meeting != null) {
            System.out.println(meeting);
        } else {
            System.out.println("Could not find booked timetable meeting: " + meetingId);
        }
    }

    private void removeMeeting() {
       System.out.println("Which booked meeting do you want to remove? Enter its ID: ");
       int meetingId = scan.nextInt(); // STUDENTS MUST HANDLE BAD INPUT HERE
       scan.nextLine();
      boolean removed = timetable.removeMeeting(meetingId);
        if (removed) {
            System.out.println("Removed meeting " + meetingId);
        } else {
            System.out.println("Unable to find meeting " + meetingId);
        }
   }



    private void printAll() {
        Venue[] venues = timetable.obtainAllVenues();
        Arrays.sort(venues);
        System.out.println("Venues in timetable system are: \n");
        for (int i = 0; i < venues.length; i++) {
            System.out.println(venues[i]);
        }
        TimeTabledSlots[] timeTabledSlot = timetable.obtainAllTimeTabledSlots();
        Arrays.sort(timeTabledSlot);
        System.out.println("Events in timetable are: ");
        for (int i = 0; i < timeTabledSlot.length; i++) {
            System.out.println(timeTabledSlot[i]);
        }
    }
    private void populateAndAddToTimetable(TimeTabledSlots timeTabledSlot) {
        System.out.println("Enter the unique timetable meeting identifier: (unique number)");
        int meetingId = scan.nextInt();
        scan.nextLine();
        inputStartEndTime(timeTabledSlot);

        System.out.println("Is a data projector required?(Y/N)");
        String answer = scan.nextLine().toUpperCase();
        boolean projectorRequired = true;
        if (answer.equals("N")) {
            projectorRequired = false;
        }

        Venue venue = null;
        while (true) {
            System.out.println("Enter venue name");
            String venueName = scan.nextLine();
            venue = timetable.searchForVenue(venueName);
            if (venue != null) {
                if (projectorRequired && !venue.hasDataProjector()) {
                    System.out.println("Selected venue does not have a data projector. Choose a different venue");
                } else {
                    timeTabledSlot.setMeetingId(meetingId);
                    timeTabledSlot.setDataProjectorRequired(projectorRequired);

                    timeTabledSlot.setVenue(venue);

                    timetable.add(timeTabledSlot);
                    break; // out of the loop
                }
            } else {
                System.out.println("Venue " + venue + " does not exist. Try a different venue? (Y/N)");
                answer = scan.nextLine().toUpperCase();
                if (!answer.equals("Y")) break; // if not Y then break out of the loop
            }
        }
    }

    private void inputStartEndTime(TimeTabledSlots timeTabledSlot) {
        while (true) {
            System.out.println("Enter start time for timetable meeting");
            LocalDateTime startTime = getDateTime();
            System.out.println("Enter end time for timetable meeting");
            LocalDateTime endTime = getDateTime();
            if (startTime.compareTo(endTime) < 0) {
                timeTabledSlot.setStartAndEndTime(startTime, endTime);
                break;
            } else {
                System.out.println("Start time: " + startTime + " must be before end time: " + endTime);
            }
        }
    }

    private LocalDateTime getDateTime() {
        LocalDateTime result = null;
        while(true) {
            try {
                System.out.println("On one line (numbers): year month day hour minutes");

                // Note that an InputMismatchException is thrown if an
                // illegal value is entered. For simplicity, we will pretend that won't happen.
                // STUDENTS MUST HANDLE THIS SITUATION.
                int year = scan.nextInt();
                int month = scan.nextInt();
                int day = scan.nextInt();
                int hour = scan.nextInt();
                int minutes = scan.nextInt();
                scan.nextLine(); // Clear the end of line character

                result = LocalDateTime.of(year, month, day, hour, minutes);
                break; // break out of the loop
            } catch (DateTimeException dte) {
                System.out.println("Try again. " + dte.getMessage());
            }
        }

        System.out.println("The date/time you entered was: " + result);
        return result;
    }

    private MeetingType getMeetingType() {
        MeetingType meetingType = MeetingType.OTHER; // Make the default
        System.out.println("Meeting type, enter the number (1 - staff meeting \n 2 - learning and teaching meeting \n 3 - subject panel meeting \n 4 - other kind of meeting");
        String answer = scan.nextLine();
        switch (answer) {
            case "1":
                meetingType = MeetingType.STAFF;
                break;
            case "2":
                meetingType = MeetingType.LTC;
                break;
            case "3":
                meetingType = MeetingType.SUBJECT_PANEL;
                break;
        }

        System.out.println("Meeting type selected: " + meetingType);
        return meetingType;
    }

    private void save() {
        try {
            timetable.save(filename);
        } catch (IOException e) {
            System.err.println("Problem when trying to write to file: " + filename);
        }
    }

    /**
     * the main method
     * @param args
     */
    public static void main(String args[]) {
        System.out.println("**********HELLO***********");

        TimetableApp app = new TimetableApp();
        app.initialise();
        app.runMenu();
        app.printAll();
        app.save();

        System.out.println("***********GOODBYE**********");
    }
}
