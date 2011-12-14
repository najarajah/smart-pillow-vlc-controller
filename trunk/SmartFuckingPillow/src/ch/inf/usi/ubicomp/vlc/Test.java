package ch.inf.usi.ubicomp.vlc;


public class Test{

	public static void main(String[] args) throws InterruptedException {
		
		VLCController vlc = new VLCController();
		
		vlc.playOrPauseCommand();
		Thread.sleep(3000);
		//vlc.pauseCommand();
		//Thread.sleep(3000);
		//vlc.nextCommand();
		//vlc.playCommand();
		//Thread.sleep(3000);
		//vlc.prevCommand();
		//Thread.sleep(3000);
		vlc.increaseVolumeCommand();
		vlc.increaseVolumeCommand();
		vlc.increaseVolumeCommand();
		vlc.increaseVolumeCommand();
		vlc.increaseVolumeCommand();
		Thread.sleep(3000);
		vlc.decreaseVolumeCommand();
		vlc.decreaseVolumeCommand();
		vlc.decreaseVolumeCommand();
		vlc.decreaseVolumeCommand();
		vlc.decreaseVolumeCommand();
		vlc.increaseSeekCommand();
		Thread.sleep(6000);
		vlc.pauseCommand();
		Thread.sleep(6000);
		vlc.pauseCommand();
		/*vlc.increaseSeekCommand();
		Thread.sleep(3000);
		vlc.increaseSeekCommand();
		Thread.sleep(3000);
		vlc.increaseSeekCommand();
		Thread.sleep(3000);
		vlc.playOrPauseCommand();
		vlc.increaseSeekCommand();
		Thread.sleep(3000);
		vlc.increaseSeekCommand();
		Thread.sleep(3000);
		vlc.decreaseSeekCommand();
		Thread.sleep(3000);
		vlc.decreaseSeekCommand();
		Thread.sleep(3000);
		vlc.increaseSeekCommand();
		Thread.sleep(3000);
		vlc.increaseSeekCommand();*/
//		System.out.println(vlc.parseVLC.getPosition());
		
		
		
		
		
	}
	
}
