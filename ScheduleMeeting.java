/**
    ITS350 Assignment 1

    -Arin Ibrahim (ai23019);
 */
 package ITS350;
 import java.util.Scanner;

 public class ScheduleMeeting {
     public static void main(String[] args) {
         //We have the 2 meeting rooms
         MeetingRoom room1 = new MeetingRoom("Meeting Room 1");
         MeetingRoom room2 = new MeetingRoom("Meeting Room 2");
         
         Scanner scanner = new Scanner(System.in);
         int choice = 0;
         
         while (choice != 6) {
             // the menu display for the 6 choices
             System.out.println("\n===== Schedule Menu =====");
             System.out.println("1. Schedule a new meeting");
             System.out.println("2. Un-schedule a meeting");
             System.out.println("3. Find first and last meeting times");
             System.out.println("4. Find number of scheduled meetings");
             System.out.println("5. Display all scheduled meetings");
             System.out.println("6. Exit");
             System.out.print("Enter your choice: ");
             
             choice = scanner.nextInt();
             scanner.nextLine();
             
             
             if (choice == 1) {
                 // Schedule a new meeting
                 System.out.print("Enter meeting room (1 or 2): ");
                 int roomChoice = scanner.nextInt();
                 scanner.nextLine();
                 
                // incase of an invalid input for room number
                 if (roomChoice != 1 && roomChoice != 2) {
                     System.out.println("Invalid room number. Please enter 1 or 2.");
                     continue;
                 }
                 // the format is in 24 hour format the input must be hour:minute ex/ 15:30
                 System.out.print("Enter meeting start time (HH:MM format): ");
                 String timeStr = scanner.nextLine();
                 
                 MeetingRoom selectedRoom = (roomChoice == 1) ? room1 : room2;
                 int time = convertTimeToMinutes(timeStr);
                 
                 if (time < convertTimeToMinutes("7:00") || time > convertTimeToMinutes("18:00")) {
                     System.out.println("Meeting rooms are only available between 7:00 and 18:00.");
                 } else {
                     boolean success = selectedRoom.scheduleMeeting(time);
                     if (success) {
                         System.out.println("Meeting scheduled successfully at " + timeStr);
                     } else {
                         System.out.println("You cannot schedule a meeting at that time;");
                     }
                 }
             } 
             else if (choice == 2) {
                 // Un-schedule a meeting
                 System.out.print("Enter meeting room (1 or 2): ");
                 int roomChoice = scanner.nextInt();
                 scanner.nextLine();
                 
                 
                 if (roomChoice != 1 && roomChoice != 2) {
                     System.out.println("Invalid room number. Please enter 1 or 2.");
                     continue;
                 }
                 
                 System.out.print("Enter a meeting *Start time* to remove a scheduled meeting (HH:MM format): ");
                 String timeStr = scanner.nextLine();
                 
                 MeetingRoom selectedRoom = (roomChoice == 1) ? room1 : room2;
                 int time = convertTimeToMinutes(timeStr);
                 
                 boolean success = selectedRoom.unscheduleMeeting(time);
                 if (success) {
                     System.out.println("Meeting at " + timeStr + " has been removed");
                 } else {
                     System.out.println("No such scheduled meeting.");
                 }
             } 
             else if (choice == 3) {
                 // Find first and last meeting times
                 System.out.println("\n--- First and Last Meeting Times ---");
                 
                 // For Room 1
                 int[] firstLast1 = room1.findFirstAndLastMeeting();
                 if (firstLast1[0] != -1) {
                     System.out.println("The first meeting in meeting room1 starts at: " + 
                             convertMinutesToTime(firstLast1[0]));
                     System.out.println("The last meeting in meeting room1 starts at: " + 
                             convertMinutesToTime(firstLast1[1]));
                 } else {
                     System.out.println("No meetings scheduled in meeting room1");
                 }
                 
                 // For Room 2
                 int[] firstLast2 = room2.findFirstAndLastMeeting();
                 if (firstLast2[0] != -1) {
                     System.out.println("The first meeting in meeting room2 starts at: " + 
                             convertMinutesToTime(firstLast2[0]));
                     System.out.println("The last meeting in meeting room2 starts at: " + 
                             convertMinutesToTime(firstLast2[1]));
                 } else {
                     System.out.println("No meetings scheduled in meeting room2");
                 }
             } 
             else if (choice == 4) {
                 // Find number of meetings
                 System.out.println("The number of scheduled meetings in meeting room1 is: " + 
                         room1.getNumberOfMeetings());
                 System.out.println("The number of scheduled meetings in meeting room2 is: " + 
                         room2.getNumberOfMeetings());
             } 
             else if (choice == 5) {
                 // Display all meetings
                 System.out.println("\nMeeting Room 1 Schedule:");
                 room1.displayAllMeetings();
                 
                 System.out.println("\nMeeting Room 2 Schedule:");
                 room2.displayAllMeetings();
             }
             else if (choice == 6) {
                 System.out.println("Exiting program.");
             }
             else {
                 System.out.println("Invalid choice. Please enter a number between 1 and 6.");
             }
         }
         
         scanner.close();
     }
     
     
     //  Time Complexity: O(1)
      
     public static int convertTimeToMinutes(String timeStr) {
         String[] parts = timeStr.split(":");
         
         // Add validation for time format
         if (parts.length != 2) {
             return -1;
         }
         
         int hours = Integer.parseInt(parts[0]);
         int minutes = Integer.parseInt(parts[1]);
         return hours * 60 + minutes;
     }
     
     
    //  Time Complexity: O(1)
    
     public static String convertMinutesToTime(int minutes) {
         int hours = minutes / 60;
         int mins = minutes % 60;
         return String.format("%02d:%02d", hours, mins);
     }
 }
 

 class MeetingRoom {
     private String name;
     private MeetingNode head;
     private int size;
     private MeetingNode first;
     private MeetingNode last;
     
     public MeetingRoom(String name) {
         this.name = name;
         this.head = null;
         this.size = 0;
         this.first = null;
         this.last = null;
     }
     
  
    // Schedule a new meeting
    // Time Complexity: O(n)
  
     public boolean scheduleMeeting(int startTime) {
         // If list is empty
         if (head == null) {
             head = new MeetingNode(startTime);
             first = head;
             last = head;
             size++;
             return true;
         }
         
         //insert at the begginging 
         if (startTime < head.startTime) {
             if (head.startTime - startTime < 70) {
                 return false;
             }
             
             MeetingNode newNode = new MeetingNode(startTime);
             newNode.next = head;
             head = newNode;
             first = head;
             size++;
             return true;
         }
         
         MeetingNode current = head;
         MeetingNode prev = null;
         
         while (current != null) {
             // Check if time is already scheduled
             if (current.startTime == startTime) {
                 return false;
             }
             
             // Found the right position
             if (current.startTime > startTime) {
                 // Check gaps with prev and current
                 if (startTime - prev.startTime < 70 || current.startTime - startTime < 70) {
                     return false;
                 }
                 
                 MeetingNode newNode = new MeetingNode(startTime);
                 prev.next = newNode;
                 newNode.next = current;
                 size++;
                 return true;
             }
             
             prev = current;
             current = current.next;
         }
         
         if (startTime - prev.startTime < 70) {
             return false;
         }
         
         MeetingNode newNode = new MeetingNode(startTime);
         prev.next = newNode;
         last = newNode;
         size++;
         return true;
     }
     

    // Unschedule a meeting
    // Time Complexity: O(n)

     public boolean unscheduleMeeting(int startTime) {
         if (head == null) {
             return false;
         }
         
         // Remove head
         if (head.startTime == startTime) {
             head = head.next;
             first = head;
             if (head == null) last = null;
             size--;
             return true;
         }
         
         MeetingNode current = head;
         MeetingNode prev = null;
         
         while (current != null) {
             if (current.startTime == startTime) {
                 prev.next = current.next;
                 if (current == last) last = prev;
                 size--;
                 return true;
             }
             prev = current;
             current = current.next;
         }
         
         return false;
     }
     

    // Find the first and last meeting times
    // Time Complexity: O(1) for Meeting Room 1, O(n) for Room 2

     public int[] findFirstAndLastMeeting() {
         int[] result = {-1, -1};
         
         if (head == null) {
             return result;
         }
         
         // For Meeting Room 1 O(1)
         if (name.equals("Meeting Room 1") && first != null && last != null) {
             result[0] = first.startTime;
             result[1] = last.startTime;
             return result;
         }
         
         // For Room 2 O(n)
         result[0] = head.startTime;
         
         MeetingNode current = head;
         while (current.next != null) {
             current = current.next;
         }
         result[1] = current.startTime;
         return result;
     }
     

    // Get number of scheduled meetings
    // Time Complexity: O(1)

     public int getNumberOfMeetings() {
         return size;
     }
     
  
    // Display all scheduled meetings
    // Time Complexity: O(n)
 
     public void displayAllMeetings() {
         if (head == null) {
             System.out.println("No meetings scheduled");
             return;
         }
         
         MeetingNode current = head;
         while (current != null) {
             System.out.print(ScheduleMeeting.convertMinutesToTime(current.startTime) + " ");
             current = current.next;
         }
         System.out.println();
     }
 }
 
 
  // Node class for the linked list

 class MeetingNode {
     int startTime;
     MeetingNode next;
     
     public MeetingNode(int startTime) {
         this.startTime = startTime;
         this.next = null;
     }
 }