package edu.ucalgary.oop;

/*
@author Naina Gupta <a
href="naina.gupta@ucalgary.ca"</a>
@version 1.12
@since 1.0
*/

//Importing GUI Libraries


import javax.swing.*;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Creating GUI Class
public class GUI {
  private static ArrayList<Schedule> schedule2;
  private static int[] volunteer_needed = new int[24];
  private static boolean good = false;
  private static boolean Volunteer = false;
  private static String[] vol;
  private static DbmsConnect myJDBC;
  private static int count = 0;
  private static ArrayList<Animal> animalList;
  private static ArrayList<MedicalTask> medical_task;
  private static ArrayList<RegularTask> regular_task;
/**
 * This method is used to create the GUI
 * This function connects to the dbms and gets the data from the database
 * if the fnction was caleed before it does not call the dbms again and 
 * updates the new medical data to dbms and
 * recursively calls itself to create the GUI again and creates the schedule
 * @param none
 * 
 */
  public void gui_initiator() {

    if (count == 0) {

      animalList = new ArrayList<Animal>();
      medical_task = new ArrayList<MedicalTask>();
      regular_task = new ArrayList<RegularTask>();

      myJDBC = new DbmsConnect();

      myJDBC.createConnection();

      myJDBC.select_animal_name(animalList, regular_task);
      myJDBC.select_medical_from_database(medical_task);

      for (int i = 0; i < medical_task.size(); i++) {
        if (medical_task.get(i).getTask().equals("Kit feeding")) {
          int id = medical_task.get(i).getAnimalId();
          for (int j = 0; j < regular_task.size(); j++) {
            if (regular_task.get(j).getAnimalId() == id) {
              regular_task.get(j).setTaskDone(true);
            }
          }
        }
      }
      count++;
    }

    EventQueue.invokeLater(() -> {
      scheduler schedule = new scheduler();
      Check_volunteer_needed volunteer;

      try {
        schedule2 = schedule.getSchedule(0, animalList, medical_task, regular_task);
        volunteer = new Check_volunteer_needed(schedule2);
        volunteer_needed = volunteer.getVolunteer_needed();

      } catch (Schedule_cannot_be_made e) {

        int userInput1AsInt = 0;
        int userInput2AsInt = 0;

        String temp = e.getMessage();

        String[] temp2 = temp.split(" ");
        int[] temp3 = new int[temp2.length];
        for (int i = 0; i < temp2.length; i++) {
          temp3[i] = Integer.parseInt(temp2[i]);
        }

        JTextField inputField1 = new JTextField(20);
        JTextField inputField2 = new JTextField(20);
        JTextArea textArea = new JTextArea(10, 20);

        int array = 10;
        for (int i = 0; i < temp3.length; i++) {
          textArea.append("MEDICAL TASK TreatmentID: " + temp3[i] + "  " + medical_task.get(temp3[i]).getTask() + "\n");
        }

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JLabel label = new JLabel("PLEASE TYPE THE TREATMENT ID AND TIME YOU WANT TO CHANGE TO");
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(label);
        inputPanel.add(Box.createVerticalStrut(15));
        inputPanel.add(scrollPane); // Add the scrollable text area
        inputPanel.add(Box.createVerticalStrut(15));
        inputPanel.add(new JLabel("Treatment ID:"));
        inputPanel.add(inputField1);
        inputPanel.add(new JLabel("Start Time:"));
        inputPanel.add(inputField2);

        int option = JOptionPane.showConfirmDialog(null, inputPanel, "Schedule Conflict", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
          String userInput1 = inputField1.getText();
          String userInput2 = inputField2.getText();
          userInput1AsInt = Integer.parseInt(userInput1);
          userInput2AsInt = Integer.parseInt(userInput2);
  
        }
        // process user input
        Volunteer = false;
        good = false;

        int Treatment_id = userInput1AsInt;
        int new_time = userInput2AsInt;

        myJDBC.update_medical_time(medical_task, 1, 0);
        medical_task.get(Treatment_id).alterStartTime(new_time);

        myJDBC.closeConnection();
        gui_initiator();

        e.printStackTrace();

      }

      for (int i = 0; i < volunteer_needed.length; i++) {
        if (volunteer_needed[i] == 1) {
          Volunteer = true;
        }
      }

      JFrame frame = new JFrame("GUI"); // Making new GUI Frame
      frame.setSize(400, 400); // Adjusting Size of GUI Frame
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // MAking sure the GUI exits when exit button is pushed

      JPanel contentPane = new JPanel(new BorderLayout());
      frame.setContentPane(contentPane);

      JLabel label3 = new JLabel("Welcome to EWR!");
      label3.setFont(new Font("Arial", Font.BOLD, 24)); // set a larger font
      label3.setHorizontalAlignment(JLabel.CENTER); // center the text horizontally
      contentPane.add(label3, BorderLayout.NORTH);

      // Adding button and statement to GUI

      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      JLabel label2 = new JLabel("Click to see the best schedule");
      buttonPanel.add(label2);
      JButton myButton = new JButton("Click here!");
      myListener buttonListener = new myListener(frame);
      myButton.addActionListener(buttonListener);
      buttonPanel.add(myButton);

      contentPane.add(buttonPanel, BorderLayout.CENTER);

      frame.setVisible(true);
    });

  }
/**
 * Main method
 * 
 * @param args
 */
  public static void main(String[] args) {

    GUI gui = new GUI();
    gui.gui_initiator();

  }
/**
 * Listener for the button
 * 
 */
  static class myListener implements ActionListener {
    JFrame frame;

    public myListener(JFrame frame) {
      this.frame = frame;
    }

    public void actionPerformed(ActionEvent event) {
      for (int i = 0; i < volunteer_needed.length; i++) {
        if (volunteer_needed[i] == 0) {
          good = true;
        }

        // string array where you store a string volunter needed at hour 7 , hour 7

        else if (volunteer_needed[i] == 1) {
          int option = JOptionPane.showOptionDialog(frame, "Volunteers needed at hour " + i + ". Please confirm.",
              "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Confirm" },
              "Confirm");
          if (option == JOptionPane.YES_OPTION) {

          }

          Volunteer = true;
        }

      }

      String backupMessage = ""; // initialize empty string

      if (good) {

        JLabel scheduleBuiltLabel = new JLabel(
            "The schedule is sucessfully built. Please go to schedule.txt file to see the final schedule!");

        // Creating a new JPanel to hold the label
        JPanel scheduleBuiltPanel = new JPanel();
        scheduleBuiltPanel.add(scheduleBuiltLabel);

        // Adding the panel to the content pane
        frame.getContentPane().add(scheduleBuiltPanel, BorderLayout.SOUTH);
        frame.pack();

      } else if (Volunteer) {
        // Create a new JPanel with the backup volunteer message and button components
        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        String volString = String.join(", ", vol);
        JLabel messageLabel = new JLabel(volString);
        // JLabel messageLabel = new JLabel("BACKUP VOLUNTEER NEEDED. PLEASE CONFIRM.");
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null,
                "Thank you for the confirmation. The schedule has been successfully created, please go to schedule.txt to see the schedule.");
          }
        });

        messagePanel.add(messageLabel);
        messagePanel.add(confirmButton);

        // Add the new panel to contentPane
        frame.getContentPane().add(messagePanel, BorderLayout.SOUTH);
        frame.pack();

        // Increase the size of the frame
        Dimension currentSize = frame.getSize();
        Dimension newSize = new Dimension(currentSize.width, currentSize.height + 50);
        frame.setSize(newSize);
        backupMessage = messageLabel.getText();

      }

    }
  }
}
