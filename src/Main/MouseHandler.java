package Main;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

public class MouseHandler implements MouseListener, MouseMotionListener {

	public int x, y;
	public boolean click;
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		x = e.getX();
		y = e.getY();
		if (e.getButton() == MouseEvent.BUTTON1) {
			click = false;
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			click = false;
		}
		if (e.getButton() == MouseEvent.BUTTON2) {
			click = false;
		}
		if (e.getButton() == MouseEvent.NOBUTTON) {
			click = false;
		}
	

		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		x = e.getX();
		y = e.getY();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getButton() == MouseEvent.BUTTON1) {
			click = false;
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			click = false;
		}
		if (e.getButton() == MouseEvent.BUTTON2) {
			click = false;
		}
		if (e.getButton() == MouseEvent.NOBUTTON) {
			click = false;
		}
		if (SwingUtilities.isRightMouseButton(e)) {
			click = false;
		}
		if (SwingUtilities.isLeftMouseButton(e)) {
			click = false;
		}
		if (SwingUtilities.isMiddleMouseButton(e)) {
			click = false;
		}

		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getButton() == MouseEvent.BUTTON1) {
			click = true;
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			click = false;
		}
		if (e.getButton() == MouseEvent.BUTTON2) {
			click = false;
		}
		if (e.getButton() == MouseEvent.NOBUTTON) {
			click = false;
		}
		if (SwingUtilities.isRightMouseButton(e)) {
			click = false;
		}
		if (SwingUtilities.isLeftMouseButton(e)) {
			click = true;
		}
		if (SwingUtilities.isMiddleMouseButton(e)) {
			click = false;
		}

		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getButton() == MouseEvent.BUTTON1) {
			click = false;
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			click = false;
		}
		if (e.getButton() == MouseEvent.BUTTON2) {
			click = false;
		}
		if (e.getButton() == MouseEvent.NOBUTTON) {
			click = false;
		}
		if (SwingUtilities.isRightMouseButton(e)) {
			click = false;
		}
		if (SwingUtilities.isLeftMouseButton(e)) {
			click = false;
		}
		if (SwingUtilities.isMiddleMouseButton(e)) {
			click = false;
		}
		if (e.getButton() == MouseEvent.BUTTON1) {
			click = false;
		}

		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		x = e.getX();
		y = e.getY();
		if (e.getButton() == MouseEvent.BUTTON1) {
			click = false;
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			click = false;
		}
		if (e.getButton() == MouseEvent.BUTTON2) {
			click = false;
		}
		if (e.getButton() == MouseEvent.NOBUTTON) {
			click = false;
		}
		if (SwingUtilities.isRightMouseButton(e)) {
			click = false;
		}
		if (SwingUtilities.isLeftMouseButton(e)) {
			click = false;
		}
		if (SwingUtilities.isMiddleMouseButton(e)) {
			click = false;
		}
		if (e.getButton() == MouseEvent.BUTTON1) {
			click = false;
		}
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		x = e.getX();
		y = e.getY();
		if (e.getButton() == MouseEvent.BUTTON1) {
			click = false;
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			click = false;
		}
		if (e.getButton() == MouseEvent.BUTTON2) {
			click = false;
		}
		if (e.getButton() == MouseEvent.NOBUTTON) {
			click = false;
		}
		if (SwingUtilities.isRightMouseButton(e)) {
			click = false;
		}
		if (SwingUtilities.isLeftMouseButton(e)) {
			click = false;
		}
		if (SwingUtilities.isMiddleMouseButton(e)) {
			click = false;
		}
		if (e.getButton() == MouseEvent.BUTTON1) {
			click = false;
		}
		
	}
	

}
