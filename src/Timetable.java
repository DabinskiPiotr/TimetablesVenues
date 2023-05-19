import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Represents a timetable. Keeps track of timetable meetings and possible venues
 *
 * @author Piotr Dabiński
 * @version 1 (02.04.2020)
 */

public class Timetable {

    private ArrayList<Venue> venues;
    private ArrayList<TimeTabledSlots> timeTabledSlot;
    private ArrayList<Meeting> meetings;
    private ArrayList<Lecture> lectures;


    /**
     * Initialises the timetable meetings and venues
     */
    public Timetable() {

        timeTabledSlot = new ArrayList<>();
        venues = new ArrayList<>();
        meetings = new ArrayList<>();
    }

    /**
     * Add a timetabled lecture
     * @param lecture A non-null, unique Lecture object.
     * @return rue if lecture added else false if the meeting already exists
     * @throws IllegalArgumentException is thrown if meeting is null
     */
    public boolean add(Lecture lecture) throws IllegalArgumentException {
        boolean success = false;
        if (lecture == null) {
            throw new IllegalArgumentException("The lecture must not be null");
        }
        if (!lectures.contains(lecture)) {
            lectures.add(lecture);
            success = true;
        }
        return success;
    }

    /**
     * Add a timetabled meeting.
     *
     * @param meeting A non-null, unique meeting object.
     * @return true if meeting added else false if the meeting already exists
     * @throws IllegalArgumentException is thrown if meeting is null
     */
    public boolean add(Meeting meeting) throws IllegalArgumentException {
        boolean success = false;
        if (meeting == null) {
            throw new IllegalArgumentException("The meeting must not be null");
        }
        if (!meetings.contains(meeting)) {
            meetings.add(meeting);
            success = true;
        }
        return success;
    }

    /**
     * Add a venue to the timetable system.
     *
     * @param venue A non-null, unique venue object.
     * @return true if venue added else false if the venue already exists
     * @throws IllegalArgumentException is thrown if venue is null
     */
    public boolean add(Venue venue) throws IllegalArgumentException {
        boolean success = false;
        if (venue == null) {
            throw new IllegalArgumentException("The venue must not be null");
        }
        if (!venues.contains(venue)) {
            venues.add(venue);
            success = true;
        }
        return success;
    }

    /**
     *
     * @param timeTabledSlots A non-null, unique timeTabledSlots object.
     * @return true if venue added else false if the venue already exists
     * @throws IllegalArgumentException is thrown if venue is null
     */
    public boolean add(TimeTabledSlots timeTabledSlots) throws IllegalArgumentException {
        boolean success = false;
        if (timeTabledSlot == null) {
            throw new IllegalArgumentException("The timeTabledSlot must not be null");
        }
        if (!timeTabledSlot.contains(timeTabledSlots)) {
            timeTabledSlot.add(timeTabledSlots);
            success = true;
        }
        return success;
    }

    /**
     * Removes the meeting from the timetable
     *
     * @param meetingId The ID of timetabled meeting to be removed
     * @return true if removed else false if not found
     */
    public boolean removeMeeting(int meetingId) {
        // Search for the meeting by name
        Meeting which = null;
        for (Meeting meeting : meetings) {
            if (meeting.getMeetingId() == meetingId) {
                which = meeting;
                break;
            }
        }
        if (which != null) {
            meetings.remove(which);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Obtains a copy of the timeTabledSlots
     *
     * @return A copy of the timetable timeTabledSlots
     */
    public TimeTabledSlots[] obtainAllTimeTabledSlots() {
        TimeTabledSlots[] result = new TimeTabledSlots[timeTabledSlot.size()];
        result = timeTabledSlot.toArray(result);
        return result;
    }

    /**
     * Obtains a copy of the timetable system venues
     *
     * @return A copy of the timetable system venues
     */
    public Venue[] obtainAllVenues() {
        Venue[] result = new Venue[venues.size()];
        result = venues.toArray(result);
        return result;
    }

    /**
     * Searches for a given timetabled meeting
     *
     * @param meetingId the meeting to search for
     * @return The found meeting or else null if not found
     */

    public Meeting searchForMeeting(int meetingId) {
        Meeting result = null;

        if (meetings.isEmpty()) {
            return null;
        }

        for (int i = 0; i < meetings.size(); i++) {
            if (meetings.get(i).getMeetingId() == meetingId) {
                result = meetings.get(meetingId);
                //  System.out.println("Meeting Found");
                break;
            }
        }
        // ENTER CODE HERE (POSSIBLY CHANGE SOME, YOU MAY CHANGE THE SIGNATURE TO DEAL
        // WITH ALL KINDS OF TIMETABLED SLOTS: MEETINGS AND LECTURES)
        return result;
    }

    /**
     * Searches for the given venue
     *
     * @param name The name of the venue. Must not be null.
     * @return The venue if found else null
     */

    public Venue searchForVenue(String name) {
        Venue result = null;

        if (venues.isEmpty()) {
            return null;
        }
        for (int i = 0; i < venues.size(); i++) {
            if (venues.get(i).getName().equals(name)) {
                result = venues.get(i);
                // System.out.println("Venue Found");
                break;
            }
        }
        // ENTER CODE HERE (POSSIBLY CHANGE SOME, YOU MUST NOT CHANGE THE SIGNATURE)

        return result;
    }

    /**
     * To string method for venues and Timetabled Slots
     * @return Built String from for each loops of all Venues and Timetabled Slots
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Venues in the timetable system are: \n");
        for (Venue venue : venues) {
            sb.append(venue + "\n");
        }

        sb.append("TimeTabledSlots in system are: \n");
        for (TimeTabledSlots timeTabledSlot : timeTabledSlot) {
            sb.append(timeTabledSlot + "\n");
        }
        String result = sb.toString();
        return result;
    }

    /**
     * Loads the timetable data from the given file
     * Requires previously written file with MEETING signature before meeting data in the file and
     * LECTURE for lecture's data
     *
     * @param filename The text file. Must exist.
     * @throws FileNotFoundException thrown if the file does not exist
     * @throws IOException           thrown if some other kind of IO error occurs
     */
    public void load(String filename) throws FileNotFoundException, IOException {
        // Using try-with-resource (see my slides from workshop 15)
        try (FileReader fr = new FileReader(filename);
             BufferedReader br = new BufferedReader(fr);
             Scanner infile = new Scanner(br)) {

            timeTabledSlot.clear();
            venues.clear();

            infile.useDelimiter("\r?\n|\r"); // End of line characters on Unix or DOS or others OSs

            // Read in the venues first
            Venue venue = null;
            TimeTabledSlots timeTabledSlots = null;
            Meeting meeting = null;
            Lecture lecture = null;
            int numVenues = infile.nextInt();

            for (int i = 0; i < numVenues; i++) {
                String venueName = infile.next();
                boolean hasDataProjector = infile.nextBoolean();

                venue = new Venue(venueName);
                venue.setHasDataProjector(hasDataProjector);
                venues.add(venue);
            }
            meeting = new Meeting();
            lecture = new Lecture();
            String venueName;
            Venue venueLoad;
            while (infile.hasNext()) {
                switch (infile.nextLine()) {
                    case "MEETING":
                        meeting.load(infile);
                        venueName = infile.nextLine();
                        venueLoad = searchForVenue(venueName);
                        timeTabledSlots = meeting;
                        timeTabledSlots.setVenue(venueLoad);
                        timeTabledSlot.add(timeTabledSlots);
                        break;
                    case "LECTURE":
                        lecture.load(infile);
                        venueName = infile.next();
                        venueLoad = searchForVenue(venueName);
                        timeTabledSlots = lecture;
                        timeTabledSlots.setVenue(venueLoad);
                        timeTabledSlot.add(timeTabledSlots);
                }
            }
        }
    }

    /**
     * Saves the timetabled data to a text file with special signature before each of TimeTabledSlot
     * to be able to read the written file later and put meetings and lectures into the correct categories
     *
     * @param outfileName The file. Will create a new file if it does not exist. Will overwrite an
     *                    existing file.
     * @throws IOException Thrown if some IO problem occurs.
     */
    public void save(String outfileName) throws IOException {
        try (FileWriter fw = new FileWriter(outfileName);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter outfile = new PrintWriter(bw);) {

            // Start by saving the list of allowable venues.
            // We save the number of venues first so that later on
            // we know how many to read in
            outfile.println(venues.size());
            Venue venue;
            for (int i = 0; i < venues.size(); i++) {
                venue = venues.get(i);
                outfile.println(venue.getName());
                outfile.println(venue.hasDataProjector());
            }

            TimeTabledSlots timeTabledSlots = null;
            for (int i = 0; i < timeTabledSlot.size(); i++) {
                timeTabledSlots = timeTabledSlot.get(i);
                if (timeTabledSlots.getClass().equals(Meeting.class)) {
                    outfile.println("MEETING");
                } else if (timeTabledSlots.getClass().equals(Lecture.class)) {
                    outfile.println("LECTURE");
                }
                timeTabledSlots.save(outfile);
                venue = timeTabledSlots.getVenue();
                String name = venue.getName();
                outfile.println(name);

            }
        }
    }
}
