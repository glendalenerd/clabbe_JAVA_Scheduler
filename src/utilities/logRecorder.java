package src.utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;

/**
 * class for log recording
 */
public class logRecorder {
    private static String fileName = "loginRecord.txt";
    public logRecorder() {}

    /**
     *
     * @param user username
     * @param pass password
     * @param isValid boolean value - true for valid, false for invalid
     * @throws IOException
     */
    public static void logRecord(String user, String pass, boolean isValid) throws IOException {
        FileWriter fileWrite = new FileWriter(fileName, true);
        BufferedWriter bufferedWrite = new BufferedWriter(fileWrite);
        PrintWriter printWrite = new PrintWriter(bufferedWrite);
        String lineToPrint = " logging in at: " + Instant.now().toString() + " using password: " +pass;
        if (user == "") {
            user = "unspecified user";
        }
        printWrite.println("'"+user+"'" + (isValid ? " success with" : " failed with")+lineToPrint);
        bufferedWrite.close();
        printWrite.close();
        fileWrite.close();
    }

}
