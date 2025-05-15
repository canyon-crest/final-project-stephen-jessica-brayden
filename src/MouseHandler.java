import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseMotionListener{

	public int x, y;
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		x = e.getX();
		y = e.getY();
		
	}

}
