package adil.spring.security.learnPart;

import adil.spring.security.ReminderUser;
//import sun.util.calendar.BaseCalendar;

import java.sql.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;


public class CreateRepository {
private static final String DB_USERNAME="postgres";
private static final String DB_PASSWORD="password";
private static final String DB_URL="jdbc:postgresql://localhost:5432/jwt_service";

public void createNameTask(String login){
    try {
    Connection connection= DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
        Statement statement = connection.createStatement();
        String SQL_CREATE = "create table IF not EXISTS "+login+"(" +
                "id serial PRIMARY KEY, " +
                "name VARCHAR ( 20 )  NOT NULL, " +
                "description VARCHAR ( 255 ) NOT NULL );";
//        String sql_create_contr="CREATE TABLE IF NOT EXISTS controller (" +
//                "email VARCHAR(20) UNIQUE NOT NULL, " +
//                "state TIMESTAMP WITH TIME ZONE);";

        statement.executeUpdate(SQL_CREATE);
//        statement.executeUpdate(sql_create_contr);

        System.out.println("Success");

    }
    catch (Exception e){
        e.printStackTrace();
    }
}
    public static void call() {
     String desiredTime = "21:43"; // Replace with the desired time in HH:mm:ss format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime desiredDateTime = LocalTime.parse(desiredTime.formatted(formatter));
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                LocalTime currentTime = LocalTime.now();
                // Format the current time to show only the minute and hour
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime now = LocalTime.parse((currentTime.format(formatter)));
                if (now.isAfter(desiredDateTime)) {
                    System.out.println("Displaying text at: " + now.toString());
                    // Display the desired text here
                }
            }
        };
        // Create a Timer to schedule the TimerTask
        Timer timer = new Timer();
        // Schedule the TimerTask to run every second (1000 milliseconds)
        timer.schedule(timerTask, 0, 1000);
    }

public void insert_row( String nameTable, String nameTask, String description){

try {
    Connection connection= DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
    Statement statement = connection.createStatement();
//    String query= "insert into "+nameTable+"(name,description"+") values ( "+nameTask+" , "+description+" );";
    String query = "INSERT INTO " + nameTable + " (name, description) VALUES ('" + nameTask + "', '" + description + "');";
    statement.executeUpdate(query);
    System.out.println("success");

} catch (SQLException e) {
e.printStackTrace();
}
}
    public static void man() {


        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        List<ReminderUser> clients = new ArrayList<>();
clients.add(new ReminderUser("Task",23,30,0));
clients.add(new ReminderUser("Task1",23,31,0));
clients.add(new ReminderUser("Task2",23,29,0));

        // Add more clients as needed

        // Define the time at which the reminder should trigger
        int hour = 23;
        int minute = 4;
        int second = 0;

        // Loop through the clients and schedule reminders for each one
        for (ReminderUser client : clients) {
            // Get the current time
            long currentTime = System.currentTimeMillis();

            // Calculate the time delay until the reminder triggers
            long delayMillis = calculateDelayMillis(client.getHour(), client.getMinute(), client.getSecond(), currentTime);

            executor.schedule(() -> {
            // Code to execute when the reminder triggers
            System.out.println("Reminder triggered for client: " + client.getNameTask() + " at: " + new java.util.Date());
            sendReminderMessage(client.getNameTask()); // Send reminder message to the client
        }, delayMillis, TimeUnit.MILLISECONDS);
    }
        executor.shutdown();


}
    public static long calculateDelayMillis(int hour, int minute, int second, long currentTime) {
        // Get the current time in milliseconds
        java.util.Date date = new java.util.Date(currentTime);

        // Set the desired time for the reminder
        date.setHours(hour);
        date.setMinutes(minute);
        date.setSeconds(second);

        // Calculate the time delay until the reminder triggers
        long delayMillis = date.getTime() - currentTime;

        // If the calculated delay is negative, it means the desired time has already passed, so add 24 hours to the delay
        if (delayMillis < 0) {
            delayMillis += 24 * 60 * 60 * 1000; // 24 hours in milliseconds
        }

        return delayMillis;
    }
    public static void sendReminderMessage(String clientName) {
        // Code to send reminder message to the client
        System.out.println("Sending reminder message to client: " + clientName);
        // Add your logic here to send reminder messages to clients, such as email, SMS, etc.
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException  {
//        createNameTask("adil");
//        CreateRepository c=new CreateRepository();
//        c.insert_row("tasks","Adil","safadsfsdjflsdfsadf");

        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

        Callable<Integer> task2 = () -> 10;

        task1();

        //run this task after 5 seconds, nonblock for task3, returns a future
        ScheduledFuture<Integer> schedule = ses.schedule(task2, 5, TimeUnit.SECONDS);

        task3();

        // block and get the result
        System.out.println(schedule.get());

        System.out.println("shutdown!");

        ses.shutdown();

    }

    public static void task1() {
        System.out.println("Running task1...");
    }

    public static void task3() {
        System.out.println("Running task3...");
    }

}

