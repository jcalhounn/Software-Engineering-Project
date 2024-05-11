
import java.awt.*;
import java.io.File;
import java.util.concurrent.TimeUnit;

import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;


import javax.swing.*;


//Client creating ComputeRequest

public class UserClient extends JFrame {

    private enum State {
        START_MENU, FILE_MENU, COMPLETE_MENU, ERROR_MENU
    }
    private final ComputeAPIGrpc.ComputeAPIBlockingStub blockingStub; // Boilerplate TODO: update to appropriate blocking stub


    private UserClient.State currentState;
    private final JButton startButton, exitButton, delimButton, againButton, backButton, completeButton;
    private final JLabel inputFilePrompt, outputFilePrompt, delimPrompt, welcomeMessage, completedMessage, errorMessage;

    private final JTextField inputFileField, delimField, outputFileField;

    private String inputFile;
    private String outputFile;
    private char delimiter;


    //Explaination 2
    //We need to send our code across the network and this process is buffered by a protocol file
    //this means that the data types that our project expects to see is defined in the proto file
    //To send our actual request through the compute method, the proto is structured in a way that
    //states what data type will be expected as a parameter and as the return type
    //The blockingStub.compute(request) is an rpc service defined in the proto, so it needs proto parameters
    //these parameters are set through the initialization of inputConfig,and outputConfig, and explicitly for delimiter
    //this proto readable request is then sent across the channel to the EngineServer
    //Continued on EngineServer

    public void request() {

        //TODO: user fills out input output configs and delimiter attached through GUI


        UserProto.InputConfig inputConfig = UserProto.InputConfig.newBuilder().setFileName(inputFile).build();
        UserProto.OutputConfig outputConfig = UserProto.OutputConfig.newBuilder().setFileName(outputFile).build();
        UserProto.ComputeRequest request = UserProto.ComputeRequest.newBuilder().setInput(inputConfig).setOutput(outputConfig).setDelimiter(delimiter + "").build();

        UserProto.ComputeResult response;
        try {
            response = blockingStub.compute(request);
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
            return;
        }
        if (response==null) {
            System.err.println("Failure Response"); //+ response.getErrorMessage());
        } else {
            System.out.println("Compute Success!"); //+ response.getOrderNumber());
        }
    }


    //EXPLANATION 1
    //UserClient wants to make a compute request across a "network"
    //To do this, we host a server on "EngineServer"
    //To connect to this server we use the target address "localhost:50051" that matches up to the port declared in EngineSerer
    //The channel is then built between the client and server.
    //we then call the request method on our UserClient object
    //Continued above.




    public UserClient(Channel channel) {
        super("Computation");
        blockingStub = ComputeAPIGrpc.newBlockingStub(channel);  // Boilerplate TODO: update to appropriate blocking stub

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize components
        startButton = new JButton("Start");
        exitButton = new JButton("Exit");
        againButton = new JButton("New Computation");
        delimButton = new JButton("Add Delimiter");
        backButton = new JButton("Main Menu");
        completeButton = new JButton("Start Computation");

        inputFilePrompt  = new JLabel("Input File Name (\".github/files/\")  : ");
        outputFilePrompt = new JLabel("Output File Name (\".github/files/\") : ");
        delimPrompt = new JLabel(   "Delimiter                                                        : ");
        welcomeMessage = new JLabel("Welcome to Our Computation Machine");
        completedMessage = new JLabel("Completed Computation Location: ");
        errorMessage = new JLabel("ERROR: Invalid Input");

        inputFileField = new JTextField(15);
        delimField = new JTextField(15);
        outputFileField = new JTextField(15);

        // Set initial state
        currentState = State.START_MENU;

        // Add components to frame
        addStartMenu();

        // Set initial visibility
        updateVisibility();

        // Button Actions
        addButtonActions();

        setVisible(true);
    }

    private void addButtonActions() {
        // Button actions
        startButton.addActionListener(e -> {
            currentState = State.FILE_MENU;
            updateVisibility();
            addFileStart();
        });

        exitButton.addActionListener(e -> System.exit(0));

        againButton.addActionListener(e -> {
            currentState = State.FILE_MENU;
            updateVisibility();
            addFileStart();
        });

        backButton.addActionListener(e -> {
            currentState = State.START_MENU;
            updateVisibility();
            addStartMenu();
        });

        //add complete page action listener
        completeActionListener();
    }

    private void completeActionListener() {
        completeButton.addActionListener(e -> {
            String inputFileName = inputFileField.getText().trim();
            String outputFileName = outputFileField.getText().trim();
            String delim = delimField.getText().trim();

            // Check for Errors
            if (!isValidInputFile(inputFileName)) {
                JOptionPane.showMessageDialog(UserClient.this, "Invalid INPUT file name or file does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if(!isValidDelimiter(delim)) {
                JOptionPane.showMessageDialog(UserClient.this, "Invalid delimiter length or is an integer.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if(!isValidOutputFile(outputFileName)) {
                JOptionPane.showMessageDialog(UserClient.this, "Invalid OUTPUT file name or file does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                currentState = State.COMPLETE_MENU;
                outputFile = outputFileName;
                inputFile = inputFileName;
                delimiter = delim.charAt(0);
                updateVisibility();
                addCompleteScreen();
                this.request();
            }
        });
    }

    private void addStartMenu() {
        JPanel messagePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        messagePanel.add(welcomeMessage);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(startButton);
        buttonsPanel.add(exitButton);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        messagePanel.add(buttonsPanel, gbc);

        add(messagePanel, BorderLayout.CENTER);

    }

    private void addFileStart() {

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Add label and text field for input file name
        inputPanel.add(inputFilePrompt, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(inputFileField, gbc);

        // Add label and text field for delimiter
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(outputFilePrompt, gbc);
        gbc.gridx = 1;
        outputFileField.setPreferredSize(new Dimension(100,25));
        inputPanel.add(outputFileField, gbc);


        // Add label and text field for delimiter
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(delimPrompt, gbc);
        gbc.gridx = 1;
        delimField.setPreferredSize(new Dimension(100,25));
        inputPanel.add(delimField, gbc);

        // Add buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(exitButton);
        buttonPanel.add(backButton);
        buttonPanel.add(completeButton);

        // Add components to main panel
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        inputPanel.add(buttonPanel, gbc);

        add(inputPanel, BorderLayout.CENTER);

    }

    private void addErrorScreen() {
        JPanel messagePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        messagePanel.add(errorMessage);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(againButton);
        buttonsPanel.add(exitButton);

        gbc.gridy = 2;
        gbc.gridwidth = 2;
        messagePanel.add(buttonsPanel, gbc);

        add(messagePanel, BorderLayout.CENTER);
    }

    private void addCompleteScreen() {

        JPanel messagePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        completedMessage.setText("Completed Computation Location: .github/files/" + outputFile);
        messagePanel.add(completedMessage);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(againButton);
        buttonsPanel.add(exitButton);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        messagePanel.add(buttonsPanel, gbc);

        add(messagePanel, BorderLayout.CENTER);

    }

    private void updateVisibility() {

        //clear text fields
        inputFileField.setText("");
        delimField.setText("");

        // Remove all existing components from the frame
        removeComponents();

        // Set visibility to false
        hideComponents();

        // Show components based on the current state
        switch (currentState) {
            case START_MENU:
                addStartMenu();
                startButton.setVisible(true);
                exitButton.setVisible(true);
                break;

            case FILE_MENU:
                addFileStart();
                inputFileField.setVisible(true);
                delimField.setVisible(true);
                completeButton.setVisible(true);
                exitButton.setVisible(true);
                backButton.setVisible(true);
                inputFilePrompt.setVisible(true);
                delimPrompt.setVisible(true);
                break;

            case COMPLETE_MENU:
                addCompleteScreen();
                againButton.setVisible(true);
                exitButton.setVisible(true);
                backButton.setVisible(true);
                break;

            default:
        }

    }

    private void removeComponents() {
        getContentPane().removeAll();
        revalidate();
        repaint();
    }

    private void hideComponents() {

        // Hide all components
        startButton.setVisible(false);
        exitButton.setVisible(false);
        againButton.setVisible(false);
        delimButton.setVisible(false);
        backButton.setVisible(false);
        completeButton.setVisible(false);
        inputFileField.setVisible(false);
        delimField.setVisible(false);
        inputFilePrompt.setVisible(false);
        delimPrompt.setVisible(false);
    }

    private boolean isValidInputFile(String fileName) {
        File file = new File(".github/files/" + fileName);
        return file.exists() && !file.isDirectory();
    }

    private boolean isValidOutputFile(String fileName) {
        File file = new File(".github/files/" + fileName);
        return file.exists() && !file.isDirectory();
    }

    private boolean isValidDelimiter(String delimiter) {
        boolean valid = delimiter.length() == 1 && !Character.isDigit(delimiter.charAt(0));
        System.out.println(valid);
        return valid;
    }



    public static void main(String[] args) throws Exception {


        String target = "localhost:50054";  // Boilerplate TODO: make sure the server/port match the server/port you want to connect to

        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
                .build();
        try {


            UserClient client = new UserClient(channel);
            client.setVisible(true);

            //client.request();

        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }

    }
}