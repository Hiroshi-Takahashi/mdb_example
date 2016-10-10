package app;

public class Main {

    public static void main( String[] args ) {
    	
    	// メッセージ送信
    	MessageSender sender = new MessageSender();
    	
    	for ( int i=0; i < 100; i++ ) {
        	sender.send("test"+i);
    	}
    }
}
