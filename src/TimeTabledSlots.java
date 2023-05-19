import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 *represents the timetabled slots (meetings and lectures)
 *
 * @author Piotr Dabiński
 * @version 1 (02.04.2020)
 */
public class TimeTabledSlots implements Comparable<TimeTabledSlots> {
     Venue venue;
     LocalDateTime startTime;
     LocalDateTime endTime;
     int meetingId;
     boolean requiresDataProjector;

    /**
     * empty constructor
     */
    public TimeTabledSlots() {

    }

    /**
     *
     * @param venue venue for  the timetabled slots
     * @param startTime start time of the events on timetable
     * @param endTime end time of the timetabled events
     * @param timetableId unique id for the timetabled events
     * @param requiresDataProjector boolean variable of Data projector requirement
     */
    public TimeTabledSlots(Venue venue, LocalDateTime startTime, LocalDateTime endTime, int timetableId, boolean requiresDataProjector) {
        this.venue = venue;
        this.startTime = startTime;
        this.endTime = endTime;
        this.meetingId = timetableId;
        this.requiresDataProjector = requiresDataProjector;
    }

    /**
     *
     * @return venue
     */
    public Venue getVenue() {
        return venue;
    }

    /**
     *
     * @param venue setter of the venue for the time tabled slot
     */
    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    /**
     *
     * @return meetingId
     */
    public int getMeetingId() {
        return meetingId;
    }

    /**
     *
     * @param meetingId setter of the meeting id
     */
    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    /**
     *
     * @return returns if data projector is required
     */

    public boolean isDataProjectorRequired(){
        return requiresDataProjector;
    }

    /**
     *
     * @param dataProjectorRequired setter of the dataProjector on the timetabledslot
     * requires to change the venue if it does not have access to the data projector
     */
    public void setDataProjectorRequired(boolean dataProjectorRequired) {
        if (venue != null && (dataProjectorRequired && !venue.hasDataProjector())){
            System.err.println("Event currently has a venue " +
                    venue.getName() + " that does not have a data projector. Change the venue first");
        } else {
            this.requiresDataProjector = dataProjectorRequired;
        }
    }

    /**
     *
     * @param startTime of the timetabled slot
     * @param endTime of the time tabled slot
     * @throws IllegalArgumentException
     */
    public void setStartAndEndTime(LocalDateTime startTime, LocalDateTime endTime) throws IllegalArgumentException {
        if (startTime.compareTo(endTime) >= 0){
            throw new IllegalArgumentException("start time: " + startTime + " must be before end time: " + endTime);
        }
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * reader of the date time from the file
     * @param infile
     * @return
     */
    public LocalDateTime readDateTime(Scanner infile){
        String dateTime = infile.next();
        return LocalDateTime.parse(dateTime);
    }

    /**
     * writes down the date time of the timetabled slot into the file
     * @param outfile
     * @param dateTime
     */
    public void writeDateTime(PrintWriter outfile, LocalDateTime dateTime){
        outfile.println(dateTime);
    }

    /**
     * save method of the timetabled slot
     * @param outfile
     */
    public void save(PrintWriter outfile) {
        if (outfile == null) {
            throw new IllegalArgumentException("outfile must not be null");
        }
        outfile.println(meetingId);
        writeDateTime(outfile, startTime);
        writeDateTime(outfile, endTime);
        outfile.println(requiresDataProjector);

    }

    /**
     * load method of the time tabled slot
     * @param infile
     * @throws IllegalArgumentException
     */
    public void load(Scanner infile) throws IllegalArgumentException {
        if (infile == null) {
            throw new IllegalArgumentException("infile must not be null");
        }
        meetingId = infile.nextInt();
        startTime = readDateTime(infile);
        endTime = readDateTime(infile);
        requiresDataProjector = infile.nextBoolean();
    }

    /**
     * overwritten equals method
     * @param other other object
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Meeting meeting = (Meeting) other;
        return meetingId == meeting.meetingId;
    }

    /**
     * overwritten compare to method
     * @param other other timetabled slot
     * @return
     */
    @Override
    public int compareTo(TimeTabledSlots other){
        if(this.startTime.equals(other.startTime)){
            return this.getVenue().compareTo(other.getVenue());
        }else{
            return this.startTime.compareTo(other.startTime);}}

    /**
     * to string method for the timetabled slot
     * @return
     */
    @Override
    public String toString() {
        return "TimeTabledSlots{" +
                "venue=" + venue +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", meetingId=" + meetingId +
                ", requiresDataProjector=" + requiresDataProjector +
                '}';
    }
}