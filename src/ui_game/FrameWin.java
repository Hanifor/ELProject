package ui_game;

import javax.swing.JFrame;

import control.PlayerControl;
import ui.FrameTotal;
import ui.FrameTotal;
/**
 * 通关界面的窗口
 * @author 恩哥哥
 * 20.5.4.24.
 */
public class FrameWin extends FrameTotal{
	PanelWin panelWin;
	
	public FrameWin(PlayerControl playerControl){
		panelWin = new PanelWin(playerControl);

		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//定义通关窗口的大小与位置
		int w = PanelWin.WIDTH;
		int h = PanelWin.HEIGHT;
		int x = (WINDOWW-w)/2+WINDOWX;
		int y = (WINDOWH-h)/2+WINDOWY;
		this.setSize(w, h);
		this.setLocation(x, y);
		
		this.setContentPane(panelWin);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
