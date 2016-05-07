package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
	static boolean secure = true;
	// TODO implement that in the future
	// private JFileChooser fileChooser;
	private Alarm myAlarmController;

	public View() {
		myController = new ControllerAlarm();
		window = new JFrame("Alarma");
		// fileChooser = new JFileChooser();
		myAlarmController = myController.getMyAlarm();

	}

	public void createGUI() {
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
		panelBottom.add(label);
		panel.add(hourLabel);
		panel.add(fieldHour);
		panel.add(minuteLabel);
		panel.add(fieldMinute);
		panel.add(secondLabel);
		panel.add(fieldSecond);
		panel.add(button);

		AccionBoton accion = new AccionBoton(label, fieldHour, fieldMinute, fieldSecond);
		fieldSecond.addActionListener(accion);
		button.addActionListener(accion);
		bigPanel.add(panel);
		bigPanel.add(panelBottom);
		window.getContentPane().add(bigPanel);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);

	}

	class AccionBoton implements ActionListener {

		private JLabel jlabel;
		private JTextField hour, minute, second;

		public AccionBoton(JLabel jLabel, JTextField hour, JTextField minute, JTextField second) {
			this.jlabel = jLabel;
			this.hour = hour;
			this.minute = minute;
			this.second = second;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int h, m, s = 0;

			try {
				h = Integer.parseInt(hour.getText());
				m = Integer.parseInt(minute.getText());
				s = Integer.parseInt(second.getText());
				// h = 0;
				// m = 0;
				// s = 5;

			} catch (Exception e2) {
				h = 0;
				m = 0;
				s = 0;
				// alarm = new Alarm();
			}

			final Alarm alarm = new Alarm(h, m, s);
			myController.setMyAlarm(alarm);
			Timer time = new Timer();
			MP3 myMp3 = new MP3(View.class.getResourceAsStream("/media/fiestaBienLoca.mp3"));

			time.schedule(new TimerTask() {

				@Override
				public void run() {
					if (alarm.isNew()) {
						Thread t = new Thread(new Runnable() {

							@Override
							public void run() {
								if (secure) {
									myMp3.play();
									secure = false;
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
}
