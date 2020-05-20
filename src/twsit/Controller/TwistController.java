package twsit.Controller;

import twsit.Model.TwistBE;
import twsit.gui.TwistGui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TwistController {
    private TwistGui twistGui;
    private TwistBE twistBE;

    public TwistController(TwistBE twistBE, TwistGui twistGui) {
        this.twistBE = twistBE;
        this.twistGui = twistGui;
        this.twistGui.addTwistListener(new TwistListener());
        this.twistGui.addDetwistListener(new DetwistListener());
        this.twistGui.addDictBrowseListener(new DictBrowseListener());
        this.twistGui.addAddWordListener(new AddWordListener());
        //this.twistGui.addProposalListener(new ProposalListener());
        //this.twistGui.addCheckProposalListener(new CheckProposalListener());
    }

    class TwistListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] split = getTextSplitLine("\\n");
            String text;
            text="";
            //twist text line by line
            for(String splitText : split) {
                text+=twistBE.twistText(splitText);

            }
            twistGui.setText(text);
        }
    }

    class DetwistListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] split = getTextSplitLine("\n");
            String text;
            text="";
            for(String splitText : split) {
                text+=twistBE.detwistText(splitText);
            }
            twistGui.setText(text);

        }
    }

    private String[] getTextSplitLine(String s) {
        String text = twistGui.getText();
        return text.split(s);
    }

 /*   class CheckProposalListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String text = twistGui.getPropTextField().getText();
            if(text.contains(" ")) {
                twistGui.getPropTextField().setText("Bitte nur ein Wort einfügen!");
            } else {
                twistBE.detwistWord(text);
            }

        }
    }*/

    class DictBrowseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String dictPath = twistGui.openDict();
            if(dictPath=="") {
                return;
            }
            twistGui.setGitLocText(dictPath);
            twistGui.setButtonsActive();
            twistGui.setText("Fügen Sie Ihren Text hier ein");


                twistBE.setWordListFile(new File(dictPath));


        }
    }

    class ProposalListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            twistGui.callProposalWindow();
        }
    }

    class AddWordListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            File wordListFile = twistBE.getWordListFile();

            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(wordListFile,true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String word = twistGui.getAddWordFieldText();
            if(word.trim().equals("")) {
                JOptionPane.showMessageDialog(null," Leeres Wort kann nicht hinzugefügt werden.","Wort nicht hinzugefügt",JOptionPane.ERROR_MESSAGE);
                return;
            }
            twistGui.setAddWordFieldText("");
            JOptionPane.showMessageDialog(null,word+" zur Liste hinzugefügt","Wort hinzugefügt",JOptionPane.INFORMATION_MESSAGE);
            try {
                bufferedWriter.write("\n"+word.trim());
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                try {
                    bufferedWriter.close();
                    fileWriter.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                twistBE.setWordListFile(twistBE.getWordListFile());
            }

        }
    }
}
