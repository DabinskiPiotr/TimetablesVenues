import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Scanner;

/**
 * Represents a meeting
 * @author Piotr Dabiński
 * @version 1 (02.04.2020)
 */
public class Meeting extends TimeTabledSlots {
    String meetingName;
    String organiser;
    MeetingType meetingType;

    /**
     * empty constructor
     */
    public Meeting() {
    }

    /**
     *
     * @param venue
     * @param startTime
     * @param endTime
     * @param timetableId
     * @param requiresDataProjector
     * @param meetingName name of the meeting
     * @param organiser meeting organiser
     * @param meetingType this is a one of 4 types of the meeting
     */
    public Meeting(Venue venue, LocalDateTime startTime, LocalDateTime endTime, int timetableId, boolean requiresDataProjector, String meetingName, String organiser, MeetingType meetingType) {
        super(venue, startTime, endTime, timetableId, requiresDataProjector);
        this.meetingName = meetingName;
        this.organiser = organiser;
        this.meetingType = meetingType;
    }

    /**
     *
     * @return meeting name
     */
    public String getMeetingName() {
        return meetingName;
    }

    /**
     *
     * @param meetingName setter
     */
    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    /**
     *
     * @return organiser of the meeting
     */
    public String getOrganiser() {
        return organiser;
    }

    /**
     *
     * @param organiser setter of the organiser of the meeting
     */
    public void setOrganiser(String organiser) {
        this.organiser = organiser;
    }

    /**
     *
     * @return meeting type
     */
    public MeetingType getMeetingType() {
        return meetingType;
    }

    /**
     *
     * @param meetingType setter of the meeting
     */
    public void setMeetingType(MeetingType meetingType) {
        this.meetingType = meetingType;
    }

    /**
     *
     * @param venue setter for the meeting
     */
    public void setVenue(Venue venue) {
        // Only allow this if the venue spec matches the
        // the meeting requirement
        if (requiresDataProjector && !venue.hasDataProjector()) {
            System.err.println("Meeting requires a data projector. " +
                    "Venue " + venue.getName() + " does not have one");
        } else {
            this.venue = venue;
        }
    }

    /**
     *
     * @return venue of the meeting
     */
    public Venue getVenue() {
        return venue;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(venue, startTime, endTime);
    }

    /**
     * Loads meeting data from the given text file
     *
     * @param infile An open scanner on the text file
     * @throws IllegalArgumentException thrown if infile is null
     */
    public void load(Scanner infile) throws IllegalArgumentException {
        if (infile == null) {
            throw new IllegalArgumentException("infile must not be null");
        }
        super.load(infile);
        meetingName = infile.next();
        organiser = infile.next();
        String meetingTypeStr = infile.next();
        meetingType = MeetingType.valueOf(meetingTypeStr);
    }

    /**
     * Saves meeting data to the given text file
     *
     * @param outfile An open scanner on the text file
     * @throws IllegalArgumentException thrown if outfile is null
     */
    public void save(PrintWriter outfile) {
        if (outfile == null) {
            throw new IllegalArgumentException("outfile must not be null");
        }
       super.save(outfile);
        outfile.println(meetingName);
        outfile.println(organiser);
        outfile.println(meetingType);
    }

    /**
     * To string method for meeting class
     * @return
     */
    @Override
    public String toString() {
        return "Meeting{" +
                "meetingName='" + meetingName + '\'' +
                ", organiser='" + organiser + '\'' +
                ", meetingType=" + meetingType +
                ", venue=" + venue +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", meetingId=" + meetingId +
                ", requiresDataProjector=" + requiresDataProjector +
                '}';
    }

}