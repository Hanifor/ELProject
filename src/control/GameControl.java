package control;

import audio.SoundEffect;
import gamecomponent.PlanetEarth;
import gamedata.GameData;
import gamedata.TotalData;
import ui.FrameTotal;
import ui_game.PanelGame;
import ui_start.PanelSelectMission;
import ui_start.PanelStartGame;
/**
 * 游戏控制器，用于接收PlayerControl传入的信息，并刷新JPanelGame。
 * @author 恩哥哥
 * 2015.4.13.
 */
public class GameControl {
	/**
	 * 游戏界面
	 */
	private FrameTotal frameTotal;
	/**
	 * 开始界面层
	 */
	private PanelStartGame panelStartGame;
	/**
	 * 选关界面层
	 */
	private PanelSelectMission panelSelectMission;
	/**
	 * 游戏界面层
	 */
	private PanelGame panelGame;
	/**
	 * 单局游戏数据
	 */
	private GameData gameData;
	
	public GameControl(){
	}
	
	/**
	 * 向游戏控制器中加入界面
	 * @param frameTotal
	 */
	public void addFrame(FrameTotal frameTotal) {
		this.frameTotal = frameTotal;	
	}
	
	public void setPanelStartGame(PanelStartGame panelStartGame) {
		this.panelStartGame = panelStartGame;
	}
	public void setPanelSelectMission(PanelSelectMission panelSelectMission) {
		this.panelSelectMission = panelSelectMission;
	}
	public void setPanelGame(PanelGame panelGame) {
		this.panelGame = panelGame;
	}
	
	//
	public void stopDrag(){
		this.panelGame.stopDrag();
	}
	
	/**
	 * 发射光线
	 * @param launchX
	 * @param lightY
	 */
	public void launchLight() {
		if(this.panelGame != null){
			this.printPlanetLocation();
			this.gameData.getLightControl().launchLight(PlanetEarth.launchX, PlanetEarth.launchY, this.gameData.getLightDirectionX(), this.gameData.getLightDirectionY());
			this.panelGame.repaint();
			//音效
			SoundEffect.LIGHT.play();		
		}	
	}
	//==========================以下是各个界面间的跳转方法==============================
	/**
	 * 从开始界面跳转至选关界面
	 */
	public void toSelectMission() {
		this.frameTotal.musicStart.stop();
		this.frameTotal.remove(this.panelStartGame);
		this.panelStartGame = null;
		this.frameTotal.initPanelSelectMission();
	}
	
	/**
	 * 从通关界面返回至选关界面
	 */
	public void returnFromWin() {
		this.panelGame.closeFrameWin();
		this.frameTotal.remove(this.panelGame);
		this.panelGame = null;
		this.frameTotal.initPanelSelectMission();
	}
	
	/**
	 * 从游戏界面返回选关界面
	 */
	public void returnFromGame() {
		this.frameTotal.remove(this.panelGame);
		this.panelGame = null;
		this.frameTotal.initPanelSelectMission();
	}
	
	/**
	 * 从选关界面返回至开始界面
	 */
	public void returnToStart() {
		this.frameTotal.remove(this.panelSelectMission);
		this.panelSelectMission = null;
		this.frameTotal.musicSelect.stop();
		this.frameTotal.initPanelStartGame();
	}
	
	/**
	 * 打开帮助界面
	 */
	public void openFrameHelp(){
		this.panelStartGame.openFrameHelp();
	}
	
	/**
	 * 关闭帮助界面
	 */
	public void closeFrameHelp() {
		this.panelStartGame.closeFrameHelp();	
	}

	/**
	 * 进行下一关
	 */
	public void nextLevel() {
		//关闭通关界面
		this.panelGame.closeFrameWin();
		
		//重新建立单局游戏数据
		this.gameData = new GameData(this.gameData.getLevel() + 1);
		//移除原有的游戏界面
		this.frameTotal.remove(this.panelGame);
		this.panelGame = null;
		//下一关游戏界面
		this.frameTotal.initPanelGame(this.gameData);
	}
	
	/**
	 * 退出游戏
	 */
	public void Quit() {
		FrameTotal.TOTALDATA.saveData();
		this.frameTotal.removeAll();
		this.frameTotal.dispose();
		System.exit(0);
	}

	/**
	 * 从选关界面进入进入游戏界面
	 * @param level
	 */
	public void toGameLevel(int level) {
		this.gameData =new GameData(level);
		this.frameTotal.musicSelect.stop();
		this.frameTotal.remove(this.panelSelectMission);
		this.panelSelectMission = null;
		this.frameTotal.initPanelGame(this.gameData);
		
		this.panelGame.addControl(this);
	}
	
	/**
	 * 改变界面分辨率
	 * @param resolution 新分辨率
	 */
	public void changeResolution(int resolution){
		if((FrameTotal.TOTALDATA.getResolution() != resolution)&&(this.panelStartGame != null)){
			FrameTotal.TOTALDATA.setResolution(resolution);
			FrameTotal.TOTALDATA.saveData();
			this.frameTotal.dispose();
			this.frameTotal = new FrameTotal(this);
		}
	}

	public void printPlanetLocation() {
		System.out.println("地球 :x="+this.gameData.getPlanetEarth().getLocationX()+"||y="+this.gameData.getPlanetEarth().getLocationY());
		System.out.println("三体 :x="+this.gameData.getPlanetThreeBody().getLocationX()+"||y="+this.gameData.getPlanetThreeBody().getLocationY());
		for (int i = 0; i < this.gameData.getPlanetReflections().size(); i++) {
			System.out.println("反射"+i+":x="+this.gameData.getPlanetReflections().get(i).getLocationX()+"||y="+this.gameData.getPlanetReflections().get(i).getLocationY());
		}
		for (int i = 0; i < this.gameData.getPlanetRefractions().size(); i++) {
			System.out.println("折射"+i+":x="+this.gameData.getPlanetRefractions().get(i).getLocationX()+"||y="+this.gameData.getPlanetRefractions().get(i).getLocationY());
		}
		for (int i = 0; i < this.gameData.getPlanetBlackHoles().size(); i++) {
			System.out.println("黑洞"+i+":x="+this.gameData.getPlanetBlackHoles().get(i).getLocationX()+"||y="+this.gameData.getPlanetBlackHoles().get(i).getLocationY());
		}
	}
}