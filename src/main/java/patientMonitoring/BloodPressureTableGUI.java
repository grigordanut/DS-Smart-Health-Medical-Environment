package patientMonitoring;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.SwingConstants;

import healthtServiceMonitoring.healthServiceGUI;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BloodPressureTableGUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BloodPressureTableGUI window = new BloodPressureTableGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BloodPressureTableGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 960, 630);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lbl_image = new JLabel("");
		lbl_image.setIcon(new ImageIcon(BloodPressureTableGUI.class.getResource("/bloodNew.png")));
		lbl_image.setBounds(0, 40, 950, 500);
		
		frame.getContentPane().add(lbl_image);
		
		JLabel lblNewLabel = new JLabel("Blood Pressure Stages");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(330, 10, 210, 25);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btn_back = new JButton("Back");
		btn_back.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {				
				frame.dispose();				
				healthServiceGUI healthGui = new healthServiceGUI();
				healthGui.main(null);
			}
		});
		btn_back.setFont(new Font("Tahoma", Font.BOLD, 18));
		btn_back.setBounds(380, 550, 100, 30);
		frame.getContentPane().add(btn_back);
	}
}
