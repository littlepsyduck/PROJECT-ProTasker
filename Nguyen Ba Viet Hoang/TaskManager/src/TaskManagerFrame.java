import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;

public class TaskManagerFrame extends JFrame {
    private final ArrayList<Task> tasks;
    private final JTable taskTable;
    private final DefaultTableModel tableModel;
    private final String username;

    public TaskManagerFrame(String username) {
        this.username = username;

        setTitle("Task Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        tasks = new ArrayList<>();

        tableModel = new DefaultTableModel(new Object[]{"Title", "Description", "Priority", "Due Date"}, 0);
        taskTable = new JTable(tableModel);

        JPanel buttonPanel = getPanel();

        JScrollPane scrollPane = new JScrollPane(taskTable);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadTasksFromFile();
    }

    private JPanel getPanel() {
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        addButton.addActionListener(_ -> addTask());

        editButton.addActionListener(_ -> editTask());

        deleteButton.addActionListener(_ -> deleteTask());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        return buttonPanel;
    }

    private void loadTasksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(STR."\{username}.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] taskData = line.split(",");
                String title = taskData[0];
                String description = taskData[1];
                String priority = taskData[2];
                String dueDate = taskData[3];
                tasks.add(new Task(title, description, priority, dueDate));
            }
        } catch (IOException _) {
        }

        tasks.sort(new Comparator<>() {
            @Override
            public int compare(Task task1, Task task2) {

                int priority1 = getPriorityValue(task1.priority);
                int priority2 = getPriorityValue(task2.priority);
                return Integer.compare(priority1, priority2);
            }

            private int getPriorityValue(String priority) {
                return switch (priority) {
                    case "high" -> 0;
                    case "medium" -> 1;
                    case "low" -> 2;
                    default -> 3;
                };
            }
        });


        for (Task task : tasks) {
            tableModel.addRow(new Object[]{task.title, task.description, task.priority, task.dueDate});
        }
    }

    private void addTask() {
        String title = JOptionPane.showInputDialog(this, "Enter the task title:");
        String description = JOptionPane.showInputDialog(this, "Enter the task description:");

        String[] priorityOptions = {"high", "medium", "low"};
        String priority = (String) JOptionPane.showInputDialog(
                this,
                "Select the task priority:",
                "Priority",
                JOptionPane.PLAIN_MESSAGE,
                null,
                priorityOptions,
                "medium"
        );

        String dueDate;
        while (true) {
            dueDate = JOptionPane.showInputDialog(this, "Enter the task due date (yyyy-mm-dd):");
            if (isValidDateFormat(dueDate)) {
                break;
            } else {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please enter the date in yyyy-mm-dd format.");
            }
        }
        Task task = new Task(title, description, priority, dueDate);
        tasks.add(task);
        tableModel.addRow(new Object[]{task.title, task.description, task.priority, task.dueDate});

        saveTasksToFile();
    }

    private boolean isValidDateFormat(String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void editTask() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow != -1) {
            Task task = tasks.get(selectedRow);

            String title = JOptionPane.showInputDialog(this, "Enter the new task title:", task.title);
            String description = JOptionPane.showInputDialog(this, "Enter the new task description:", task.description);

            String[] priorityOptions = {"high", "medium", "low"};
            String priority = (String) JOptionPane.showInputDialog(
                    this,
                    "Select the new task priority:",
                    "Priority",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    priorityOptions,
                    task.priority
            );

            String dueDate;
            while (true) {
                dueDate = JOptionPane.showInputDialog(this, "Enter the new task due date (yyyy-mm-dd):");
                if (isValidDateFormat(dueDate)) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid date format. Please enter the date in yyyy-mm-dd format.");
                }
            }
            task.title = title;
            task.description = description;
            task.priority = priority;
            task.dueDate = dueDate;

            tableModel.setValueAt(task.title, selectedRow, 0);
            tableModel.setValueAt(task.description, selectedRow, 1);
            tableModel.setValueAt(task.priority, selectedRow, 2);
            tableModel.setValueAt(task.dueDate, selectedRow, 3);

            saveTasksToFile();
        }
    }

    private void deleteTask() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow != -1) {
            tasks.remove(selectedRow);
            tableModel.removeRow(selectedRow);

            saveTasksToFile();
        }
    }

    private void saveTasksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STR."\{username}.txt"))) {
            for (Task task : tasks) {
                writer.write(STR."\{task.title},\{task.description},\{task.priority},\{task.dueDate}");
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error occurred while saving tasks to file.");
        }
    }
}

