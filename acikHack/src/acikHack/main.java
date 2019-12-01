package acikHack;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class main extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;

	/**
	 * Launch the application.
	 */
	
	acik acik=new acik();
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main frame = new main();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 830, 619);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textArea.setBounds(12, 401, 788, 158);
		contentPane.add(textArea);
		
		/*
		JFileChooser j = new JFileChooser(); 
		j.setBounds(12, 66, 788, 45); 
		// Open the save dialog 
		j.showSaveDialog(null);
		*/
		
		JLabel lblErguvan = new JLabel("ERGUVAN");
		lblErguvan.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblErguvan.setBounds(436, 13, 119, 26);
		contentPane.add(lblErguvan);
		
		JLabel lblNewLabel = new JLabel("Yorumlar\u0131n hi\u00E7 i\u015Flem g\u00F6rmeden kendi puanlama");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(12, 146, 290, 26);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField.setBounds(314, 144, 226, 45);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel(" sistemi ile de\u011Ferlendirilmesi");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(12, 169, 244, 20);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblYorumlarnSnflandrldktanSonra = new JLabel("Yorumlar\u0131n s\u00FCzge\u00E7 i\u015Flemi g\u00F6rd\u00FCkten sonra kendi");
		lblYorumlarnSnflandrldktanSonra.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblYorumlarnSnflandrldktanSonra.setBounds(12, 214, 290, 26);
		contentPane.add(lblYorumlarnSnflandrldktanSonra);
		
		JLabel lblPuanlamaSistemiIle = new JLabel("puanlama sistemi ile de\u011Ferlendirilmesi");
		lblPuanlamaSistemiIle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPuanlamaSistemiIle.setBounds(12, 237, 244, 20);
		contentPane.add(lblPuanlamaSistemiIle);
		
		JLabel lblYorumlarnSnfl = new JLabel("Yorumlar\u0131n s\u00FCzge\u00E7 i\u015Flemi g\u00F6rd\u00FCkten sonra ");
		lblYorumlarnSnfl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblYorumlarnSnfl.setBounds(12, 275, 290, 26);
		contentPane.add(lblYorumlarnSnfl);
		
		JLabel lblErguvanIleDeerlendirilmesi = new JLabel(" Erguvan ile de\u011Ferlendirilmesi");
		lblErguvanIleDeerlendirilmesi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblErguvanIleDeerlendirilmesi.setBounds(12, 298, 244, 20);
		contentPane.add(lblErguvanIleDeerlendirilmesi);
		
		JLabel lblrnvenYorumlarn = new JLabel("\u00DCr\u00FCn\u00FC \u00D6ven Yorumlar\u0131n Y\u00FCzdesi");
		lblrnvenYorumlarn.setBounds(336, 115, 189, 16);
		contentPane.add(lblrnvenYorumlarn);
		
		JLabel lblrnKtleyenYorumlarn = new JLabel("\u00DCr\u00FCn\u00FC K\u00F6t\u00FCleyen Yorumlar\u0131n Y\u00FCzdesi");
		lblrnKtleyenYorumlarn.setBounds(581, 115, 206, 16);
		contentPane.add(lblrnKtleyenYorumlarn);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_1.setColumns(10);
		textField_1.setBounds(574, 144, 226, 45);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_2.setColumns(10);
		textField_2.setBounds(314, 214, 226, 45);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_3.setColumns(10);
		textField_3.setBounds(574, 214, 226, 45);
		contentPane.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_4.setColumns(10);
		textField_4.setBounds(314, 287, 226, 45);
		contentPane.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_5.setColumns(10);
		textField_5.setBounds(574, 287, 226, 45);
		contentPane.add(textField_5);
		
		
		
		JLabel lblkarmlar = new JLabel("\u00C7\u0131kar\u0131mlar:");
		lblkarmlar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblkarmlar.setBounds(12, 355, 132, 33);
		contentPane.add(lblkarmlar);
		
		JButton btnAnalizEt = new JButton("Analiz Et");
		btnAnalizEt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					acik.exxecute();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				textField.setText(Float.toString(acik.ilkyorumIyiler));
				textField_1.setText(Float.toString(acik.ilkyorumKotuler));
				
				textField_2.setText(Float.toString(acik.iyiyorum));
				textField_3.setText(Float.toString(acik.kotuyorum));
				
				textField_4.setText(Float.toString(acik.totiyiSonuc));
				textField_5.setText(Float.toString(acik.totkotuSonuc));


				textArea.setText(acik.textArea);
				
				
				
			}
		});
		btnAnalizEt.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAnalizEt.setBounds(200, 52, 600, 45);
		contentPane.add(btnAnalizEt);	
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\Users\\Muaz DERVENT\\eclipse-workspace\\acikHack\\src\\acikHack\\logo1.png"));
		label.setBounds(12, 13, 146, 120);
		contentPane.add(label);
	}
}
