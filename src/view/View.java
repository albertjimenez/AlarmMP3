package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.ControllerAlarm;
import media.MP3;
import model.Alarm;

public class View {

	private ControllerAlarm myController;
	private JFrame window;
	private static boolean soundRepeat = true;
	// TODO implement that in the future
	private JFileChooser fileChooser;
	private Alarm myAlarmController;

	public View() {
		myController = new ControllerAlarm();
		window = new JFrame("Alarma / Cronometro");
		fileChooser = new JFileChooser();
		myAlarmController = myController.getMyAlarm();

	}

	public void createGUI() {
		// New
		JPanel bigPanel = new JPanel();
		JPanel panel = new JPanel();
		JPanel panelBottom = new JPanel();
		JLabel hourLabel = new JLabel("Horas: ");
		JTextField fieldHour = new JTextField(2);
		JLabel minuteLabel = new JLabel("Minutos: ");
		JTextField fieldMinute = new JTextField(2);
		JLabel secondLabel = new JLabel("Segundos: ");
		JTextField fieldSecond = new JTextField(2);
		JButton button = new JButton("Iniciar / Parar", new ImageIcon(getClass().getResource("/media/alarm.png")));
		JLabel label = new JLabel(myAlarmController.toString());
		JButton buttonMP3 = new JButton("Cargar MP3", new ImageIcon(getClass().getResource("/media/loadmusic.png")));
		// Adds
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
		AccionBoton accion = new AccionBoton(label, fieldHour, fieldMinute, fieldSecond, false);
		fieldSecond.addActionListener(accion);
		button.addActionListener(accion);
		buttonMP3.addActionListener(new AccionBoton(label, fieldHour, fieldMinute, fieldSecond, true));
		// Adds
		bigPanel.add(panel);
		bigPanel.add(panelBottom);
		// Finally
		window.getContentPane().add(bigPanel);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);

	}

	class AccionBoton implements ActionListener {

		private JLabel jlabel;
		private JTextField hour, minute, second;
		private boolean isAnotherMP3;

		public AccionBoton(JLabel jLabel, JTextField hour, JTextField minute, JTextField second, boolean isAnotherMp3) {
			this.jlabel = jLabel;
			this.hour = hour;
			this.minute = minute;
			this.second = second;
			this.isAnotherMP3 = isAnotherMp3;
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

			time.schedule(new TimerTask() {

				@Override
				public void run() {
					if (alarm.isNew()) {
						Thread t = new Thread(new Runnable() {

							@Override
							public void run() {
								if (soundRepeat) {
									myMp3.play();
									soundRepeat = false;
									jlabel.setText("ALARMA");

								}
							}
						});
						t.setDaemon(true);
						t.start();

					}

					else {
						jlabel.setText(alarm.toString());
						alarm.decrement();

					}

				}
			}, 0, 1000);

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
