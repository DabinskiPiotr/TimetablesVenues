import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Represents a lecture
 * @author Piotr Dabiński
 * @version 1 (02.04.2020)
 */
public class Lecture extends TimeTabledSlots {
    String moduleCode;
    String lecturer;
    int phoneNumber;
    boolean seating;

    /**
     * empty constructor
     */
    public Lecture() {
    }

    /**
     *
     * @param venue
     * @param startTime
     * @param endTime
     * @param timetableId
     * @param requiresDataProjector
     * @param moduleCode code of the lecture module
     * @param lecturer lecturer's  name
     * @param phoneNumber lecturers's phone number
     * @param seating is seating adjustable boolean variable
     */
    public Lecture(Venue venue, LocalDateTime startTime, LocalDateTime endTime, int timetableId, boolean requiresDataProjector, String moduleCode, String lecturer,int phoneNumber, boolean seating) {
        super(venue, startTime, endTime, timetableId, requiresDataProjector);
        this.moduleCode = moduleCode;
        this.lecturer = lecturer;
        this.phoneNumber = phoneNumber;
        this.seating = seating;
    }

    /**
     *
     * @return lecturer's name
     */
    public String getLecturer() {
        return lecturer;
    }

    /**
     *
     * @param lecturer setter of name of the lecturer
     */
    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    /**
     *
     * @return phone number
     */
    public int getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @param phoneNumber setter of the lecturer
     */
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * @return module code
     */
    public String getModuleCode() {
        return moduleCode;
    }

    /**
     *
     * @param moduleCode setter of a module code for lecture
     */
    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    /**
     *
     * @return true if seating is adjustable
     */
    public boolean isSeatingAdjustable() {
        return seating;
    }

    /**
     *
     * @param seating setter of boolean weather seating is adjustable
     */
    public void setSeatingAdjustment(boolean seating) {
        this.seating = seating;
    }

    /**
     * Loads lecture data from the given text file
     *
     * @param infile An open scanner on the text file
     * @throws IllegalArgumentException thrown if infile is null
     */
    public void load(Scanner infile) throws IllegalArgumentException {
        if (infile == null) {
            throw new IllegalArgumentException("infile must not be null");
        }
        super.load(infile);
        moduleCode = infile.next();
        lecturer = infile.next();
        phoneNumber = infile.nextInt();
        seating = infile.nextBoolean();
    }

    /**
     * Saves lecture data to the given text file
     *
     * @param outfile An open scanner on the text file
     * @throws IllegalArgumentException thrown if outfile is null
     */
    public void save(PrintWriter outfile) {
        if (outfile == null) {
            throw new IllegalArgumentException("outfile must not be null");
        }
        super.save(outfile);
        outfile.println(moduleCode);
        outfile.println(lecturer);
        outfile.println(phoneNumber);
        outfile.println(seating);

    }

    /**
     *
     * @return to string method for lecture
     */
    @Override
    public String toString() {
        return "Lecture{" +
                "moduleCode='" + moduleCode + '\'' +
                ", lecturer=" + lecturer +
                ", seating=" + seating +
                ", venue=" + venue +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", meetingId=" + meetingId +
                ", requiresDataProjector=" + requiresDataProjector +
                '}';
    }
}