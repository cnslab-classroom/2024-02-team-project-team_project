package refrigerator;

public class ExpiryTimer implements Runnable {
    private Refrigerator refrigerator;
    private boolean running;

    public ExpiryTimer(Refrigerator refrigerator) {
        this.refrigerator = refrigerator;
        this.running = true;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while (running) {
                // 1초가 지날 때까지 대기
            long currentTime = System.currentTimeMillis();
            if (currentTime - startTime >= 1000) { // 1000밀리초 == 1초
                System.out.println("1초가 지났습니다!"); // 30분 대기
                int i=0;
                refrigerator.minusExpiry();
                System.out.println("1 second passed, expiry dates updated.");
                refrigerator.print();
                while(refrigerator.get(0).getExpiry()==0){
                    System.out.println(refrigerator.get(0)+" is elemenated");
                    refrigerator.pop();
                }                    
                while(refrigerator.get(i).getExpiry()<3){
                        System.out.println(refrigerator.get(i).getName()+": 남은 유통기한 ("+refrigerator.get(i).getExpiry()+") ");
                    i++;
                }
                currentTime=startTime;               
            }
        }
    }

    public void stop() {
        running = false;
    }
}