import java.awt.EventQueue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Source {

	private JFrame frame;
	private JTextArea outputTextArea;
	private String loadedTokens[];
	private int loadedTokensCount;
	private int doneElementsCount;
	private String loadPath = "input.txt";
	private String savePath = "save.txt";

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Source window = new Source();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public String getFirstByAlphabet(String str1, String str2)
	{
		int minLength = 0;
		if(str1.length() <= str2.length())
			minLength = str1.length();
		else
			minLength = str2.length();
		
		for(int i = 0; i < minLength; i++)
		{
			if(str1.charAt(i) < str2.charAt(i))
			{
				return str1;
			}else if (str1.charAt(i) > str2.charAt(i))
			{
				return str2;
			}
		}
		return str1;
	}
	
	public boolean isStr1UpperTnenStr2ByAlphabet(String str1, String str2)
	{
		int minLength = 0;
		if(str1.length() <= str2.length())
			minLength = str1.length();
		else
			minLength = str2.length();
		
		for(int i = 0; i < minLength; i++)
		{
			if(str1.charAt(i) < str2.charAt(i))
			{
				return true;
			}else if (str1.charAt(i) > str2.charAt(i))
			{
				return false;
			}
		}
		return true;
	}
	
	public void swap(String[] strings, int id1, int id2)
	{
		String tempStr = strings[id1];
		strings[id1] = strings[id2];
		strings[id2] = tempStr;
	}
	
	public int nextSortStep(String[] strings, int doneElementsCount)
	{
		if(doneElementsCount >= 0 && doneElementsCount < strings.length)
		{
			int uppestID = doneElementsCount;
			String uppestStr = strings[doneElementsCount];
			for(int i = doneElementsCount; i < strings.length; i++)
			{
				if(isStr1UpperTnenStr2ByAlphabet(strings[i], uppestStr))
				{
					uppestStr = strings[i];
					uppestID = i;
				}
			}
			swap(strings, uppestID, doneElementsCount);
			doneElementsCount++;
		}	
		return doneElementsCount;
	}
	
	public int sortArray(String[] strings, int doneElementsCount)
	{
		while(doneElementsCount < strings.length)
			doneElementsCount = nextSortStep(strings, doneElementsCount);
		return doneElementsCount;
	}

	public Source() {
		initialize();
		/*
		try {
			
			Scanner sc = new Scanner(new File("input.txt"));

			String inputStr = new String();
			
			while(sc.hasNextLine())
				inputStr = inputStr + sc.nextLine();
			
			StringTokenizer st = new StringTokenizer(inputStr, " \t\n\r,.");
			String[] tokens = new String[st.countTokens()];
			
			for(int i = 0; i < st.countTokens(); i++)
				tokens[i] = st.nextToken();

			
			outputTextArea.setText(tokens[1]);
			

			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		*/
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 761, 523);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		outputTextArea = new JTextArea();
		outputTextArea.setBounds(0, 0, 587, 486);
		frame.getContentPane().add(outputTextArea);
		
		JButton loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Scanner sc;
				try {
					JFileChooser fileChooser = new JFileChooser(loadPath);
					int returnValue = fileChooser.showOpenDialog(null);
					
					if(returnValue == JFileChooser.APPROVE_OPTION)
					{
						File selectedFile = fileChooser.getSelectedFile();
						loadPath = selectedFile.getAbsolutePath();
					}
					
					doneElementsCount = 0;
					sc = new Scanner(new File(loadPath));
					
					String inputStr = new String();
					
					while(sc.hasNextLine())
						inputStr = inputStr + sc.nextLine();
					
					StringTokenizer st = new StringTokenizer(inputStr, " \t\n\r,.");
					loadedTokens = new String[st.countTokens()];
					loadedTokensCount = st.countTokens();
					
					int j = 0;
					while(st.hasMoreTokens())
					{
						loadedTokens[j] = st.nextToken();	
						j++;
					}
					
					String outputString = new String();						
					outputTextArea.setText(inputStr);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		loadButton.setBounds(606, 24, 131, 34);
		frame.getContentPane().add(loadButton);
		
		JButton nextSortStepButton = new JButton("Next sort step");
		nextSortStepButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				doneElementsCount = nextSortStep(loadedTokens, doneElementsCount);
				
				String outputString = new String();					
				for(int i = 0; i < loadedTokensCount; i++)
					outputString = outputString + loadedTokens[i] + "\n";
				
				outputTextArea.setText(outputString);
			}
		});
		nextSortStepButton.setBounds(606, 221, 131, 34);
		frame.getContentPane().add(nextSortStepButton);
		
		JLabel arraySortLabel = new JLabel("Array sorting");
		arraySortLabel.setHorizontalAlignment(SwingConstants.CENTER);
		arraySortLabel.setBounds(597, 152, 140, 13);
		frame.getContentPane().add(arraySortLabel);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChooser = new JFileChooser(loadPath);
				int returnValue = fileChooser.showSaveDialog(null);
				
				if(returnValue == JFileChooser.APPROVE_OPTION)
				{
					File selectedFile = fileChooser.getSelectedFile();
					savePath = selectedFile.getAbsolutePath();
				}
				
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter(savePath));
					
					String outputString = new String();					
					for(int i = 0; i < loadedTokensCount; i++)
						outputString = outputString + loadedTokens[i] + "\n";
					
					writer.write(outputString);
					writer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		saveButton.setBounds(606, 68, 131, 34);
		frame.getContentPane().add(saveButton);
		
		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				doneElementsCount = 0;
				try {
					Scanner sc = new Scanner(new File(loadPath));
					
					String inputStr = new String();
					
					while(sc.hasNextLine())
						inputStr = inputStr + sc.nextLine();
					
					StringTokenizer st = new StringTokenizer(inputStr, " \t\n\r,.");
					loadedTokens = new String[st.countTokens()];
					loadedTokensCount = st.countTokens();
					
					int j = 0;
					while(st.hasMoreTokens())
					{
						loadedTokens[j] = st.nextToken();	
						j++;
					}				
					
					String outputString = new String();		
					for(int i = 0; i < loadedTokensCount; i++)
						outputString = outputString + loadedTokens[i] + "\n";
					outputTextArea.setText(outputString);
					
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		updateButton.setBounds(606, 175, 131, 34);
		frame.getContentPane().add(updateButton);
		
		JButton sortArrayButton = new JButton("Sort array");
		sortArrayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				doneElementsCount = sortArray(loadedTokens, doneElementsCount);
				String outputString = new String();		
				for(int i = 0; i < loadedTokensCount; i++)
					outputString = outputString + loadedTokens[i] + "\n";
				outputTextArea.setText(outputString);
			}
		});
		sortArrayButton.setBounds(606, 265, 131, 34);
		frame.getContentPane().add(sortArrayButton);
	}
}
