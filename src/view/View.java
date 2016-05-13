package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import controller.ControllerAlarm;
import media.MP3;
import model.Alarm;

public class View {

	private ControllerAlarm myController;
	private JFrame window;
	private static boolean soundRepeat = true;
	private JFileChooser fileChooser;
	private Alarm myAlarmController;
	private ButtonGroup buttonGroup;
	private JRadioButton timerRadioButton, alarmRadioButton;

	public View() {
		myController = new ControllerAlarm();
		window = new JFrame("Alarma / Cronometro");
		fileChooser = new JFileChooser();
		buttonGroup = new ButtonGroup();
		timerRadioButton = new JRadioButton("Temporizador");
		alarmRadioButton = new JRadioButton("Alarma");
		myAlarmController = myController.getMyAlarm();

	}

	public void createGUI() {
		// New

		JPanel bigPanel = new JPanel();
		JPanel panelRadio = new JPanel();
		JPanel panel = new JPanel();
		JPanel panelBottom = new JPanel();
		JLabel hourLabel = new JLabel("Horas: ");
		JTextField fieldHour = new JTextField(2);
		JLabel minuteLabel = new JLabel("Minutos: ");
		JTextField fieldMinute = new JTextField(2);
		JLabel secondLabel = new JLabel("Segundos: ");
		JTextField fieldSecond = new JTextField(2);
		JButton button = new JButton("Iniciar", new ImageIcon(getClass().getResource("/media/alarm.png")));
		JLabel label = new JLabel(myAlarmController.toString());
		JButton buttonMP3 = new JButton("Cargar MP3", new ImageIcon(getClass().getResource("/media/loadmusic.png")));
		// Adds
		timerRadioButton.setSelected(true);
		buttonGroup.add(alarmRadioButton);
		buttonGroup.add(timerRadioButton);
		panelBottom.add(label);
		panel.add(hourLabel);
		panel.add(fieldHour);
		panel.add(minuteLabel);
		panel.add(fieldMinute);
		panel.add(secondLabel);
		panel.add(fieldSecond);
		panel.add(button);
		panel.add(buttonMP3);
		// Actions
		ActionsTimeAlarm accion = new ActionsTimeAlarm(label, fieldHour, fieldMinute, fieldSecond, false,
				timerRadioButton);
		fieldSecond.addActionListener(accion);
		button.addActionListener(accion);
		buttonMP3.addActionListener(
				new ActionsTimeAlarm(label, fieldHour, fieldMinute, fieldSecond, true, timerRadioButton));
		// Adds
		panelRadio.add(timerRadioButton);
		panelRadio.add(alarmRadioButton);
		bigPanel.add(panelRadio);
		bigPanel.add(panel);
		bigPanel.add(panelBottom);

		// Finally
		window.getContentPane().add(bigPanel);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);

	}

	class ActionsTimeAlarm implements ActionListener {

		private JLabel jlabel;
		private JTextField hour, minute, second;
		private boolean isAnotherMP3;
		private JRadioButton isTimerSelected;

		public ActionsTimeAlarm(JLabel jLabel, JTextField hour, JTextField minute, JTextField second,
				boolean isAnotherMp3, JRadioButton isTimerSelected) {
			this.jlabel = jLabel;
			this.hour = hour;
			this.minute = minute;
			this.second = second;
			this.isAnotherMP3 = isAnotherMp3;
			this.isTimerSelected = isTimerSelected;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int h, m, s = 0;

			try {
				h = Integer.parseInt(hour.getText());

			} catch (Exception e2) {
				h = 0;

			}
			try {
				m = Integer.parseInt(minute.getText());

			} catch (Exception e2) {
				m = 0;
			}
			try {
				s = Integer.parseInt(second.getText());
			} catch (Exception e2) {
				s = 0;
			}

			final Alarm alarm = new Alarm(h, m, s);
			myController.setMyAlarm(alarm);
			Timer time = new Timer();
			MP3 myMp3 = loadFileMP3(isAnotherMP3);
			if (isTimerSelected.isSelected()) {
				time.schedule(new TimerTask() {

					@Override
					public void run() {
						if (alarm.isNew()) {
							if (soundRepeat) {
								myMp3.play();
								soundRepeat = false;
								jlabel.setText("TEMPORIZADOR");
								if (JOptionPane.showConfirmDialog(null, "TIEMPO FINALIZADO", "Aviso",
										JOptionPane.DEFAULT_OPTION, JOptionPane.OK_CANCEL_OPTION,
										new ImageIcon(getClass()
												.getResource("/media/vault-.png"))) == JOptionPane.OK_OPTION) {
									myMp3.close();
									soundRepeat = true;
									time.cancel();
								}
							}
						}

						else {
							jlabel.setText(alarm.toString());
							alarm.decrement();

						}

					}
				}, 0, 1000);
			} // Fin del IF
			else {
				time.schedule(new TimerTask() {
					private Alarm currentAlarm = new Alarm(new GregorianCalendar());

					@Override
					public void run() {

						if (currentAlarm.equals(alarm)) {
							if (soundRepeat) {
								myMp3.play();
								soundRepeat = false;
								jlabel.setText("ALARMA");
								if (JOptionPane.showConfirmDialog(null, "TIEMPO FINALIZADO", "Aviso",
										JOptionPane.DEFAULT_OPTION, JOptionPane.OK_CANCEL_OPTION,
										new ImageIcon(getClass()
												.getResource("/media/vault-.png"))) == JOptionPane.OK_OPTION) {
									myMp3.close();
									soundRepeat = true;
									time.cancel();
								}

							}

						} else {

							jlabel.setText(currentAlarm.toString());
							currentAlarm.increment();
						}

					}
				}, 0, 1000);
			}

		}

	}

	private MP3 loadFileMP3(boolean isAnotherMp3) {
		if (!isAnotherMp3)
			return new MP3(View.class.getResourceAsStream("/media/fiestaBienLoca.mp3"));

		fileChooser.setDialogTitle("Cargar MP3");
		fileChooser.showOpenDialog(null);
		fileChooser.setVisible(true);
		File f = fileChooser.getSelectedFile();
		MP3 mp3 = null;
		try {
			mp3 = new MP3(new FileInputStream(f.getAbsolutePath()));
		} catch (FileNotFoundException | NullPointerException e2) {
			mp3 = new MP3(View.class.getResourceAsStream("/media/fiestaBienLoca.mp3"));
		}
		return mp3;
	}

}
