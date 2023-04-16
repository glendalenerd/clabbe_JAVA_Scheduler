package src.utilities;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import src.database.JDBC;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.Optional;

public class utilityFunctions {

    /**
     * Takes string text and displays that text in an alert message as a warning
     * @param text
     */
    public static void warningAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(text);
        alert.showAndWait();
    }
    public static LocalDateTime getLocalBusTime(LocalDateTime universal) {
        ZonedDateTime universalZoned = universal.atZone(getZoneId());
        return universalZoned.toLocalDateTime().atZone(ZoneId.of("UTC")).
                withZoneSameInstant(ZoneId.of("US/Mountain")).toLocalDateTime();
    }

    public static LocalDateTime getUTCTime(LocalDateTime timeAndDate){
        ZonedDateTime timeDateZone = timeAndDate.atZone(getZoneId());
        ZonedDateTime universalZone = timeDateZone.withZoneSameInstant(ZoneId.of("UTC"));
        return universalZone.toLocalDateTime();
    }
    public static ZoneId getZoneId() {
        return ZoneId.systemDefault();
    }

    public static LocalDateTime getUniversalTime(LocalDateTime localClock) {
        ZonedDateTime localClockZoned = localClock.atZone(getZoneId());
        ZonedDateTime UniversalZoned = localClockZoned.withZoneSameInstant(ZoneId.of("UTC"));
        return UniversalZoned.toLocalDateTime();
    }

    public static boolean confirmationAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText(text);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public static void localeSet() {
        Locale locale = Locale.getDefault();
        Locale.setDefault(locale);
    }

    public static ZoneId fetchZoneId () {
        return ZoneId.systemDefault();
    }

    public static void menuOpen(ActionEvent event, String freshMenu) throws IOException {
        Parent parent = FXMLLoader.load(utilityFunctions.class.getResource(freshMenu));
        Scene newScene = new Scene(parent);
        Stage newWindow = (Stage) ((Node)event.getSource()).getScene().getWindow();
        newWindow.setScene(newScene);
        newWindow.show();
    }
    public static final Connection database = JDBC.getConnection();

    public static void DBExec(String dbQuery) throws SQLException {
        PreparedStatement ps = database.prepareStatement(dbQuery);
        ps.execute();
    }
    public static ResultSet DBQuery(String dbQuery) throws SQLException {
        PreparedStatement ps = database.prepareStatement(dbQuery);
        ResultSet rs;
        return rs = ps.executeQuery();
    }

    public static boolean businessHourCheck(LocalDateTime completeStart, LocalDateTime completeEnd) {
        LocalDateTime universalStart = utilityFunctions.getUniversalTime(completeStart);
        LocalDateTime universalEnd = utilityFunctions.getUniversalTime(completeEnd);
        LocalDateTime businessTimeStart = utilityFunctions.getLocalBusTime(universalStart);
        LocalDateTime businessTimeEnd = utilityFunctions.getLocalBusTime(universalEnd);
        LocalTime officeOpenTime = LocalTime.of(8, 0,0);
        LocalTime officeCloseTime = LocalTime.of(22,0,0);
        LocalDate tGHoliday = tGCalculate(completeStart.getYear());
        LocalDate tGDayAfter = tGHoliday.plusDays(1);
        //System.out.println(tGDayAfter.getDayOfWeek());
        LocalDate fourthHoliday = julyFourthDate(completeStart.getYear());
        LocalDate newYearHoliday = newYearsDay(completeStart.getYear());
        DayOfWeek firstWeekday = newYearHoliday.getDayOfWeek();
        DayOfWeek fourthWeekday = fourthHoliday.getDayOfWeek();
        LocalDate julyFourthObserved = fourthHoliday;
        LocalDate newYearsObserved = newYearHoliday;
        boolean julyFourthIsOnWeekend = false;
        boolean newYearsIsOnWeekend = false;
        if (fourthWeekday == DayOfWeek.SATURDAY || fourthWeekday == DayOfWeek.SUNDAY) {
            julyFourthIsOnWeekend = true;
        }
        if (firstWeekday == DayOfWeek.SATURDAY || firstWeekday == DayOfWeek.SUNDAY) {
            newYearsIsOnWeekend = true;
        }
        //System.out.println("day of week: "+fourthWeekday);
        if (businessTimeStart.toLocalTime().isBefore(officeOpenTime) || businessTimeStart.toLocalTime().isAfter(officeCloseTime)) {
            utilityFunctions.warningAlert("This Appointment Falls Outside of Regular Hours of 8AM to 10PM MST");
            return false;
        }
        if (completeStart.toLocalDate().isEqual(tGHoliday) || completeStart.toLocalDate().isEqual(tGDayAfter)) {
            utilityFunctions.warningAlert("The business is closed on Thanksgiving day and the day after");
            return false;
        }
        if (completeStart.toLocalDate().isEqual(fourthHoliday) && julyFourthIsOnWeekend == false) {
            utilityFunctions.warningAlert("The business is closed on the 4th of July");
            return false;
        }
        if (completeStart.toLocalDate().isEqual(newYearHoliday) && newYearsIsOnWeekend == false) {
            utilityFunctions.warningAlert("The business is closed on New Years Day");
            return false;
        }
        if (julyFourthIsOnWeekend) {
            if (completeStart.toLocalDate().isEqual(LocalDate.of(completeStart.getYear(), 7, 3))) {
                utilityFunctions.warningAlert("This business is closed on Friday in observance of the 4th of July holiday");
                return false;
            }
        }
        if (newYearsIsOnWeekend) {
            if (newYearHoliday.getDayOfWeek() == DayOfWeek.SATURDAY){
                if (completeStart.toLocalDate().isEqual(LocalDate.of(completeStart.getYear(), 1, 3))) {
                    utilityFunctions.warningAlert("This business is closed on Monday in observance of New Years day");
                    return false;
                }
            }
            if (newYearHoliday.getDayOfWeek() == DayOfWeek.SUNDAY){
                if (completeStart.toLocalDate().isEqual(LocalDate.of(completeStart.getYear(), 1, 2))) {
                    utilityFunctions.warningAlert("This business is closed on Monday in observance of New Years day");
                    return false;
                }
            }
        }
        return true;
    }

    public static LocalDate tGCalculate(int year) {
        LocalDate tGDate = Year.of(year).atMonth(Month.NOVEMBER).atDay(1).
                with(TemporalAdjusters.dayOfWeekInMonth(4, DayOfWeek.THURSDAY));
        return tGDate;
    }
    public static LocalDate julyFourthDate(int year) {
        LocalDate julyFourthHoliday = Year.of(year).atMonth(Month.JULY).atDay(4);
        return julyFourthHoliday;
    }
    public static LocalDate newYearsDay(int year) {
        LocalDate newYearsHoliday = Year.of(year).atMonth(Month.JANUARY).atDay(1);
        return newYearsHoliday;
    }
}
