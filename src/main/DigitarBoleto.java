package main;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import javafx.scene.input.KeyCode;

public class DigitarBoleto {

	public void DigitarInput(String txt,int delayStart,int delayPress) {
		Robot robot;
		try {

			robot = new Robot();
			robot.delay(delayStart);
			robot.setAutoDelay(delayPress);
			
			char[] press = txt.toCharArray();

			for (int i = 0; i < press.length; i++) {
				int key = KeyEvent.getExtendedKeyCodeForChar(press[i]);
				robot.keyPress(key);
				robot.keyRelease(key);
			}
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
