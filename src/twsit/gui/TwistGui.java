package twsit.gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class TwistGui extends JFrame {

    GridLayout gridLayoutNorthern = new GridLayout(2,3);
    private JPanel northernLayout = new JPanel(gridLayoutNorthern);

    GridLayout gridLayoutCenter = new GridLayout(2,2);
    private JPanel centerLayout = new JPanel(gridLayoutCenter);

    FlowLayout flowLayoutSouth = new FlowLayout(FlowLayout.TRAILING,10,10);
    private JPanel southLayout = new JPanel(flowLayoutSouth);

    private JButton checkButton = new JButton("check");;
    //private JTextField propTextField;
    private JTextField dictLoc;
    private JTextField addWordField;
    private JButton dictbrowse;
    private JButton addWordButton;
    private JLabel dictText = new JLabel("Pfad zur Wörterliste: ");
    private JLabel addWordLabel = new JLabel("Wort zu Liste hinzufügen: ");
    private JTextArea textArea = new JTextArea("Wählen Sie eine Wörterbuchdatei aus...");
    //private JButton proposalButton = new JButton("Wortvorschlag");
    private JButton twistButton = new JButton("Text twisten");
    private JButton detwistButton = new JButton("Text enttwisten");


    public String getAddWordFieldText() {
        return addWordField.getText();
    }

    public TwistGui() {
        this.setTitle("Twist");
        this.setSize(600,600);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);



        dictLoc = new JTextField();
        dictLoc.setEditable(false);

        addWordField = new JTextField();


        dictbrowse = new JButton("...");
        addWordButton = new JButton("Hinzufügen");



        northernLayout.add(dictText);
        northernLayout.add(dictLoc);
        northernLayout.add(dictbrowse);

        northernLayout.add(addWordLabel);
        northernLayout.add(addWordField);
        northernLayout.add(addWordButton);

        centerLayout.add(textArea);

        //southLayout.add(proposalButton);
        southLayout.add(twistButton);

        southLayout.add(detwistButton);


        this.add(northernLayout,BorderLayout.NORTH);
        this.add(centerLayout,BorderLayout.CENTER);
        this.add(southLayout,BorderLayout.SOUTH);

        //proposalButton.setEnabled(false);
        twistButton.setEnabled(false);
        detwistButton.setEnabled(false);
        addWordButton.setEnabled(false);
        addWordField.setEnabled(false);
        textArea.setEnabled(false);
        this.setVisible(true);
    }

    public String openDict() {
        String absolutePath="";
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("Wörterbuch auswählen...");
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser.setFileFilter(new FileNameExtensionFilter(".txt","txt"));
        int i = jFileChooser.showOpenDialog(this);

        if(i==jFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            absolutePath = selectedFile.getAbsolutePath();
            return absolutePath;
        }
        return absolutePath;
    }


    public String getText() {
        return textArea.getText();
    }

    public void setText(String text) {
        textArea.setText(text);
    }

    public void addTwistListener(ActionListener myTwistListener) {
        twistButton.addActionListener(myTwistListener);
    }

    public void addDetwistListener(ActionListener myDetwistListener) {
        detwistButton.addActionListener(myDetwistListener);
    }

    public void addDictBrowseListener(ActionListener myDictBrowseListener) {
        dictbrowse.addActionListener(myDictBrowseListener);
    }

    public void addAddWordListener(ActionListener myAddWordListener) {
        addWordButton.addActionListener(myAddWordListener);
    }

    /*public void addProposalListener(ActionListener myAddProposalListener) {
        proposalButton.addActionListener(myAddProposalListener);
    }*/

    public void setGitLocText(String s) {
        dictLoc.setText(s);
    }

    public void addCheckProposalListener(ActionListener myCheckProposalListener) {
        checkButton.addActionListener(myCheckProposalListener);
    }

    public void setButtonsActive() {
        //proposalButton.setEnabled(true);
        twistButton.setEnabled(true);
        detwistButton.setEnabled(true);
        addWordButton.setEnabled(true);
        addWordField.setEnabled(true);
        textArea.setEnabled(true);
    }

    public void setAddWordFieldText(String s) {
        addWordField.setText(s);
    }

    public void callProposalWindow() {

        JPanel jPanel = new JPanel();
        //propTextField = new JTextField();
        //propTextField.setText("Wort hier einfügen...");


        String[] data = {"one", "two", "three", "four"};
        JList<String> myList = new JList<String>(data);

        //jPanel.add(propTextField);
        jPanel.add(checkButton);
        jPanel.add(myList);
        JFrame jFrame = new JFrame();
        jFrame.add(jPanel);
        jFrame.setSize(500,500);
        jFrame.setTitle("Wortvorschläge");
        jPanel.setVisible(true);
        //propTextField.setVisible(true);
        checkButton.setVisible(true);
        DefaultListModel<String> stringDefaultListModel = new DefaultListModel<>();
        stringDefaultListModel.addElement("Test");
        stringDefaultListModel.addElement("Test2");
        myList.setVisible(true);

        jFrame.setVisible(true);
    }

/*    public JTextField getPropTextField() {
        return propTextField;
    }*/
}
